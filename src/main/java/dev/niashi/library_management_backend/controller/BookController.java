package dev.niashi.library_management_backend.controller;

import dev.niashi.library_management_backend.model.Book;
import dev.niashi.library_management_backend.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Book book = service.getBookById(id);

        return ResponseEntity.ok().body(book);
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = service.getAllBooks();

        return ResponseEntity.ok().body(books);
    }

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        Book addedBook = service.addBook(book);

        return ResponseEntity.status(HttpStatus.CREATED).body(addedBook);
    }
}
