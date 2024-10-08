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
        // Validations.
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        Loan loan = new Loan(user, book, returnDate);

        user.addLoan(loan);

        return loanRepository.save(loan);
    }

}
