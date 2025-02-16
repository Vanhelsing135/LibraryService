package com.example.bookstorage.repository;

import com.example.bookstorage.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
}
