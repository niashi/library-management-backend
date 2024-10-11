package dev.niashi.library_management_backend.controller;

import dev.niashi.library_management_backend.model.Book;
import dev.niashi.library_management_backend.model.DTO.CreateLoanDTO;
import dev.niashi.library_management_backend.model.Loan;
import dev.niashi.library_management_backend.model.User;
import dev.niashi.library_management_backend.repository.BookRepository;
import dev.niashi.library_management_backend.repository.UserRepository;
import dev.niashi.library_management_backend.service.LoanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
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

    @Operation(description = "Finds a loan by its Id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Loan found."),
            @ApiResponse(responseCode = "404", description = "Loan not found.")
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<Loan> getLoanById(
            @Parameter(description = "Id to be searched.", required = true)
            @PathVariable Long id
    ) {
        try {
            Loan loan = service.getLoanById(id);
            return ResponseEntity.ok().body(loan);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(description = "Finds all loans.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of loans retrieved successfully.")
    })
    @GetMapping
    public ResponseEntity<List<Loan>> getAllLoans() {
        List<Loan> loans = service.getAllLoans();

        return ResponseEntity.ok().body(loans);
    }

    @Operation(description = "Creates a new loan.", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Loan created successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid input."),
            @ApiResponse(responseCode = "404", description = "User or book not found.")
    })
    @PostMapping
    public ResponseEntity<Loan> createLoan(@Valid @RequestBody CreateLoanDTO createLoanDTO) {
        try {
            Loan createdLoan = service.createLoan(
                    createLoanDTO.getUserId(),
                    createLoanDTO.getBookId(),
                    createLoanDTO.getReturnDate()
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(createdLoan);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @Operation(description = "Updates a loan by its Id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Loan updated successfully."),
            @ApiResponse(responseCode = "404", description = "Loan not found."),
            @ApiResponse(responseCode = "400", description = "Invalid input.")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<Loan> updateLoanById(
            @PathVariable Long id,
            @RequestBody Map<String, Object> newInfo
    ) {
        try {
            Loan updatedLoan = service.updateLoanById(id, newInfo);
            return ResponseEntity.status(HttpStatus.OK).body(updatedLoan);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

    @Operation(description = "Deletes a loan by its Id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Loan deleted successfully."),
            @ApiResponse(responseCode = "404", description = "Loan not found.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoanById(@PathVariable Long id) {
        try {
            service.deleteLoanById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

}
