package dev.niashi.library_management_backend.service;

import dev.niashi.library_management_backend.model.Book;
import dev.niashi.library_management_backend.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    // Esta classe se limita a repassar as responsabilidades para o repositório.
    @Autowired
    private BookRepository repository;

    public Book getBookById(Long id) {
        Optional<Book> book = repository.findById(id);

        return book.get();
    }

    public List<Book> getAllBooks() {
        return repository.findAll();
    }

    public Book addBook(Book book) {
        return repository.save(book);
    }
}
