package com.example.demo.controller;

import com.example.demo.entity.BookStatus;
import com.example.demo.entity.BookTracker;
import com.example.demo.service.BookTrackerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/book-tracker")
@RequiredArgsConstructor
@Slf4j
public class BookTrackerController {
    private final BookTrackerService bookTrackerService;

    @GetMapping("/available")
    public ResponseEntity<List<BookTracker>> getAvailableBooks() {
        log.info("Received request to get all available books");
        return ResponseEntity.ok(bookTrackerService.getAvailableBooks());
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<Void> updateBookStatus(@PathVariable("bookId") Integer bookId, @RequestParam BookStatus status,
                                                 @RequestParam(required = false) LocalDateTime returnBy) {
        log.info("Received request to update status for book with id {}", bookId);
        bookTrackerService.updateBookStatus(bookId, status, returnBy);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<Void> deleteBookRecord(@PathVariable("bookId") Integer bookId) {
        log.info("received request to delete record for book with id {}", bookId);
        bookTrackerService.deleteBookRecord(bookId);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<Void> createBookRecord(@RequestParam("bookId") Integer bookId) {
        log.info("Received request to create record for book with id {}", bookId);
        bookTrackerService.createBookRecord(bookId);
        return ResponseEntity.ok().build();
    }
}
