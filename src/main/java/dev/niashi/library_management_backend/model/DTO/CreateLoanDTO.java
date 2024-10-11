package dev.niashi.library_management_backend.model.DTO;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

// Representa exatamente o que será recebido via JSON no Postman.
public class CreateLoanDTO {

    @NotNull(message = "User_id is required.")
    private Long userId;

    @NotNull(message = "Book_id is required.")
    private Long bookId;

    @NotNull(message = "Return date is required.")
    private LocalDate returnDate;

    public Long getUserId() {
        return userId;
    }

    public Long getBookId() {
        return bookId;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }
}
