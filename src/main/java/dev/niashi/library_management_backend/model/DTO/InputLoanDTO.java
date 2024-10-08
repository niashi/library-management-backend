package dev.niashi.library_management_backend.model.DTO;

import java.time.LocalDate;

// Representa exatamente o que será recebido via JSON no Postman.
public class InputLoanDTO {
    private Long userId;
    private Long bookId;
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
