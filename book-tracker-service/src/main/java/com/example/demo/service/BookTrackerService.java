package com.example.demo.service;

import com.example.demo.entity.BookStatus;
import com.example.demo.entity.BookTracker;
import com.example.demo.repository.BookTrackerRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookTrackerService {
    private final BookTrackerRepository bookTrackerRepository;

    public void createBookRecord(Integer bookId) {
        if (bookTrackerRepository.existsByBookId(bookId)) {
            throw new IllegalArgumentException("Book has already registered");
        }

        BookTracker bookTracker = new BookTracker();
        System.out.println(bookId);
        bookTracker.setBookId(bookId);
        bookTracker.setStatus(BookStatus.Available);
        System.out.println("Сохранение BookTracker с bookId: " + bookTracker.getBookId());
        bookTrackerRepository.save(bookTracker);
    }

    public List<BookTracker> getAvailableBooks() {
        return bookTrackerRepository.findByStatus(BookStatus.Available);
    }

    public void updateBookStatus(Integer bookId, BookStatus newStatus, LocalDateTime returnBy) {
        BookTracker bookTracker = bookTrackerRepository.findByBookId(bookId)
                .orElseThrow(() -> new IllegalArgumentException("book with id: " + bookId + " doesn't exist"));

        bookTracker.setStatus(newStatus);

        if (newStatus == BookStatus.Available) {
            bookTracker.setTakenAt(null);
            bookTracker.setReturnBy(null);
        } else {
            bookTracker.setTakenAt(LocalDateTime.now());
            bookTracker.setReturnBy(returnBy);
        }

        bookTrackerRepository.save(bookTracker);
    }

    public void deleteBookRecord(Integer bookId) {
        bookTrackerRepository.deleteByBookId(bookId);
    }
}
