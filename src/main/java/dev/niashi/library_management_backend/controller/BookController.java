package dev.niashi.library_management_backend.controller;

import dev.niashi.library_management_backend.model.Book;
import dev.niashi.library_management_backend.model.Loan;
import dev.niashi.library_management_backend.service.BookService;
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

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService service;

    @Operation(description = "Finds a book by its Id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book found."),
            @ApiResponse(responseCode = "404", description = "Book not found.")
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<Book> getBookById(
            @Parameter(description = "Id to be searched.", required = true)
            @PathVariable Long id
    ) {
        try {
            Book book = service.getBookById(id);
            return ResponseEntity.ok().body(book);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(description = "Finds all books.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of books retrieved successfully.")
    })
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = service.getAllBooks();

        return ResponseEntity.ok().body(books);
    }

    @Operation(description = "Adds a new book.", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Book added successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid input.")
    })
    @PostMapping
    public ResponseEntity<Book> addBook(@Valid @RequestBody Book book) {
        Book addedBook = service.addBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedBook);
    }

    @Operation(description = "Updates a book by its Id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book updated successfully."),
            @ApiResponse(responseCode = "404", description = "Book not found."),
            @ApiResponse(responseCode = "400", description = "Invalid input.")
    })
    @PatchMapping(value = "/{id}")
    public ResponseEntity<Book> updateBookById(
            @PathVariable Long id,
            @RequestBody Map<String, Object> newInfo
    ) {
        try {
            Book book = service.updateBookById(id, newInfo);
            return ResponseEntity.status(HttpStatus.OK).body(book);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Operation(description = "Deletes a book by its Id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Book deleted successfully."),
            @ApiResponse(responseCode = "404", description = "Book not found.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookId(@PathVariable Long id) {
        try {
            service.deleteBookById(id);
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
