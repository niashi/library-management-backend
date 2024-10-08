package dev.niashi.library_management_backend.repository;

import dev.niashi.library_management_backend.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
