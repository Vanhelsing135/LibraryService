package com.example.bookstorage.controller;

import com.example.bookstorage.dto.BookDTO;
import com.example.bookstorage.dto.BookRequestDTO;
import com.example.bookstorage.service.BookService;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book-storage/books")
@RequiredArgsConstructor
@Slf4j
public class BookController {
    private final BookService bookService;

    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        log.info("Received request to get all books");
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable("id") Integer id) {
        log.info("Received request to get book by id {}", id);
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @GetMapping("/by-ISBN/{ISBN}")
    public ResponseEntity<BookDTO> getBookByISBN(@PathVariable("ISBN") String ISBN) {
        log.info("Received request to get book by ISBN {}", ISBN);
        return ResponseEntity.ok(bookService.getBookByISBN(ISBN));
    }

    @PostMapping
    public ResponseEntity<BookDTO> createBook(@RequestBody BookRequestDTO requestDTO) {
        log.info("Received request to create book with ISBN {}", requestDTO.getISBN());
        return ResponseEntity.ok(bookService.createBook(requestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable("id") Integer id, @RequestBody BookDTO bookDTO) {
        log.info("Received request to update book with id {}", id);
        return ResponseEntity.ok(bookService.updateBook(id, bookDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable("id") Integer id) {
        log.info("Received request to delete book with id {}", id);
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
