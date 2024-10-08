package dev.niashi.library_management_backend.controller;

import dev.niashi.library_management_backend.model.Book;
import dev.niashi.library_management_backend.model.DTO.InputLoanDTO;
import dev.niashi.library_management_backend.model.Loan;
import dev.niashi.library_management_backend.model.User;
import dev.niashi.library_management_backend.repository.BookRepository;
import dev.niashi.library_management_backend.repository.UserRepository;
import dev.niashi.library_management_backend.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/loans")
public class LoanController {

    @Autowired
    private LoanService service;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Loan> getLoanById(@PathVariable Long id) {
        Loan loan = service.getLoanById(id);

        return ResponseEntity.ok().body(loan);
    }

    @GetMapping
    public ResponseEntity<List<Loan>> getAllLoans() {
        List<Loan> loans = service.getAllLoans();

        return ResponseEntity.ok().body(loans);
    }

    @PostMapping
    public ResponseEntity<Loan> createLoan(@RequestBody InputLoanDTO inputLoan) {
        User user = userRepository.findById(inputLoan.getUserId())
                .orElseThrow(() -> new NoSuchElementException("User not found with id " + inputLoan.getUserId()));
        Book book = bookRepository.findById(inputLoan.getBookId())
                .orElseThrow(() -> new NoSuchElementException("Book not found with id " + inputLoan.getBookId()));
        LocalDate returnDate = inputLoan.getReturnDate();

        // Manda as informações para o serviço criar o empréstimo.
        Loan createdLoan = service.createLoan(
                user.getId(),
                book.getId(),
                returnDate
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(createdLoan);
    }

}
