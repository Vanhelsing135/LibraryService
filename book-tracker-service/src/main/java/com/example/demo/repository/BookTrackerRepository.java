package com.example.demo.repository;

import com.example.demo.entity.BookStatus;
import com.example.demo.entity.BookTracker;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookTrackerRepository extends JpaRepository<BookTracker, Integer> {
    List<BookTracker> findByStatus(BookStatus status);

    boolean existsByBookId(Integer bookId);

    Optional<BookTracker> findByBookId(Integer bookId);
    @Transactional
    void deleteByBookId(Integer bookId);
}
