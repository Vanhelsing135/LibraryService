package com.example.bookstorage.repository;

import com.example.bookstorage.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Integer> {
    Optional<Book> getByISBN(String ISBN);
}
