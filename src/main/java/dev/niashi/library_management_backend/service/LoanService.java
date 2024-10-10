package dev.niashi.library_management_backend.service;

import dev.niashi.library_management_backend.model.Book;
import dev.niashi.library_management_backend.model.Loan;
import dev.niashi.library_management_backend.model.User;
import dev.niashi.library_management_backend.repository.BookRepository;
import dev.niashi.library_management_backend.repository.LoanRepository;
import dev.niashi.library_management_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;

    public Loan getLoanById(Long id) {
        Optional<Loan> loan = loanRepository.findById(id);

        return loan.get();
    }

    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    public Loan createLoan(Long userId, Long bookId, LocalDate returnDate) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found" + userId));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NoSuchElementException("Book not found" + bookId));

        Loan loan = new Loan(user, book, returnDate);

        user.addLoan(loan);

        return loanRepository.save(loan);
    }

    public Loan updateLoanById(Long id, Map<String, Object> newInfo) {
        Loan loan = getLoanById(id);

        List<String> allowedFields = List.of("returnDate");

        for (String key : newInfo.keySet()) {
            if (!allowedFields.contains(key)) {
                throw new IllegalArgumentException(key + ": not a valid field.");
            }
        }

        loan.setReturnDate(LocalDate.parse((String) newInfo.get("returnDate")));

        return loanRepository.save(loan);
    }

    public void deleteLoanById(Long id) {
        Loan loan = getLoanById(id);

        loanRepository.deleteById(loan.getId());
    }

}
