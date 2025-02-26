package com.example.demo.service;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumerService {
    private final BookTrackerService bookTrackerService;

    @KafkaListener(topics = "book-created", groupId = "book-tracker-group")
    public void handleBookCreated(String bookId) {
        log.info("Received 'book-created' message for book with id {}", bookId);
        bookTrackerService.createBookRecord(Integer.parseInt(bookId));
    }

    @KafkaListener(topics = "book-deleted", groupId = "book-tracker-group")
    public void handleBookDeleted(String bookId) {
        log.info("Received 'book-deleted' message for book with id {}", bookId);
        bookTrackerService.deleteBookRecord(Integer.parseInt(bookId));
    }
}
