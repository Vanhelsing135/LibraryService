package com.example.demo.service;

import com.example.demo.entity.BookStatus;
import com.example.demo.entity.BookTracker;
import com.example.demo.repository.BookTrackerRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookTrackerService {
    private final BookTrackerRepository bookTrackerRepository;

    @Transactional
    public void createBookRecord(Integer bookId) {
        log.info("Creating record with bookId: {}", bookId);
        if (bookTrackerRepository.existsByBookId(bookId)) {
            log.warn("Attempt to create a record for already existing book with id: {}", bookId);
            throw new IllegalArgumentException("Book has already registered");
        }

        BookTracker bookTracker = new BookTracker();
        bookTracker.setBookId(bookId);
        bookTracker.setStatus(BookStatus.Available);
        bookTrackerRepository.save(bookTracker);
        log.info("Record created successfully");
    }

    public List<BookTracker> getAvailableBooks() {
        log.info("Getting all available books");
        return bookTrackerRepository.findByStatus(BookStatus.Available);
    }

    @Transactional
    public void updateBookStatus(Integer bookId, BookStatus newStatus, LocalDateTime returnBy) {
        log.info("Updatin status for book with id {}", bookId);
        BookTracker bookTracker = bookTrackerRepository.findByBookId(bookId)
                .orElseThrow(() -> {
                    log.error("Book with id {} doesn't exist", bookId);
                    return new EntityNotFoundException("book with id: " + bookId + " doesn't exist");
                });
        bookTracker.setStatus(newStatus);

        if (newStatus == BookStatus.Available) {
            bookTracker.setTakenAt(null);
            bookTracker.setReturnBy(null);
        } else {
            bookTracker.setTakenAt(LocalDateTime.now());
            bookTracker.setReturnBy(returnBy);
        }

        bookTrackerRepository.save(bookTracker);
        log.info("Book status updated successfully");
    }

    @Transactional
    public void deleteBookRecord(Integer bookId) {
        log.info("Deleting record with bookId {}", bookId);
        if (!bookTrackerRepository.existsByBookId(bookId)) {
            log.warn("Attempt to delete record with bookId {} which doesn't exist", bookId);
            throw new IllegalArgumentException("record with bookId " + bookId + " doesn't exist");
        }

        bookTrackerRepository.deleteByBookId(bookId);
        log.info("Deleted successfully");
    }
}
