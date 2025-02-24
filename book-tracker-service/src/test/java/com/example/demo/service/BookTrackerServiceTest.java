package com.example.demo.service;

import com.example.demo.entity.BookStatus;
import com.example.demo.entity.BookTracker;
import com.example.demo.repository.BookTrackerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookTrackerServiceTest {
    @Mock
    private BookTrackerRepository bookTrackerRepository;

    @InjectMocks
    private BookTrackerService bookTrackerService;

    private BookTracker bookTracker;

    @BeforeEach
    void createBookTracker() {
        bookTracker = new BookTracker();
        bookTracker.setBookId(1);
        bookTracker.setStatus(BookStatus.Available);
    }

    @Test
    void createBookRecord() {
        when(bookTrackerRepository.existsByBookId(1)).thenReturn(false);
        when(bookTrackerRepository.save(any(BookTracker.class))).thenReturn(bookTracker);

        bookTrackerService.createBookRecord(1);

        verify(bookTrackerRepository).save(any(BookTracker.class));
    }

    @Test
    void throwExceptionWhileCreating() {
        when(bookTrackerRepository.existsByBookId(1)).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> bookTrackerService.createBookRecord(1));
    }

    @Test
    void getAvailableBooks() {
        when(bookTrackerRepository.findByStatus(BookStatus.Available)).thenReturn(List.of(bookTracker));

        List<BookTracker> result = bookTrackerService.getAvailableBooks();

        assertEquals(1, result.size());
        assertEquals(BookStatus.Available, result.get(0).getStatus());
    }

    @Test
    void updateBookStatusToTaken() {
        when(bookTrackerRepository.findByBookId(1)).thenReturn(Optional.of(bookTracker));

        bookTrackerService.updateBookStatus(1, BookStatus.Taken, LocalDateTime.now().plusDays(14));

        assertEquals(bookTracker.getStatus(), BookStatus.Taken);
        verify(bookTrackerRepository).save(any(BookTracker.class));
    }

    @Test
    void deleteBookRecord() {
        when(bookTrackerRepository.existsByBookId(1)).thenReturn(true);
        doNothing().when(bookTrackerRepository).deleteByBookId(1);

        bookTrackerService.deleteBookRecord(1);

        verify(bookTrackerRepository).deleteByBookId(1);
    }

    @Test
    void throwExceptionWhileDeletingBookRecord() {
        when(bookTrackerRepository.existsByBookId(1)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> bookTrackerService.deleteBookRecord(1));
    }
}
