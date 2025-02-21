package dev.niashi.library_management_backend.service;

import dev.niashi.library_management_backend.model.Book;
import dev.niashi.library_management_backend.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository repository;

    public Book getBookById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Book not found: " + id));
    }

    public List<Book> getAllBooks() {
        return repository.findAll();
    }

    public Book addBook(Book book) {
        return repository.save(book);
    }

    public Book updateBookById(Long id, Map<String, Object> newInfo) {
        Book book = getBookById(id);

        List<String> allowedFields = List.of("title", "author");

        for (String key : newInfo.keySet()) {
            if (!allowedFields.contains(key)) {
                throw new IllegalArgumentException(key + ": not a valid field.");
            }

            switch (key) {
                case "title":
                    book.setTitle((String) newInfo.get(key));
                    break;
                case "author":
                    book.setAuthor((String) newInfo.get(key));
                    break;
            }
        }

        return addBook(book);
    }

    public void deleteBookById(Long id) {
        Book book = getBookById(id);

        repository.deleteById(book.getId());
    }

}
