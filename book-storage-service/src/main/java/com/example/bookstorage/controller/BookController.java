package com.example.bookstorage.controller;

import com.example.bookstorage.dto.BookDTO;
import com.example.bookstorage.dto.BookRequestDTO;
import com.example.bookstorage.service.BookService;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book-storage/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @GetMapping("/by-ISBN/{ISBN}")
    public ResponseEntity<BookDTO> getBookByISBN(@PathVariable("ISBN") String ISBN) {
        return ResponseEntity.ok(bookService.getBookByISBN(ISBN));
    }

    @PostMapping
    public ResponseEntity<BookDTO> createBook(@RequestBody BookRequestDTO requestDTO) {
        return ResponseEntity.ok(bookService.createBook(requestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable("id") Integer id, @RequestBody BookDTO bookDTO) {
        return ResponseEntity.ok(bookService.updateBook(id, bookDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable("id") Integer id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
