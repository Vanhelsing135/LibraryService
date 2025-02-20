package com.example.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService {
    private final BookTrackerService bookTrackerService;

    @KafkaListener(topics = "book-created", groupId = "book-tracker-group")
    public void handleBookCreated(String bookId) {
        bookTrackerService.createBookRecord(Integer.parseInt(bookId));
    }

    @KafkaListener(topics = "book-deleted", groupId = "book-tracker-group")
    public void handleBookDeleted(String bookId) {
        bookTrackerService.deleteBookRecord(Integer.parseInt(bookId));
    }
}
