package com.example.bookstorage.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerService {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendBookCreatedMessage(String bookId) {
        log.info("Sending 'book-created' message for book with id {}", bookId);
        kafkaTemplate.send("book-created", bookId);
    }

    public void sendBookDeletedMessage(String bookId) {
        log.info("Sending 'book-deleted' message for book with id {}", bookId);
        kafkaTemplate.send("book-deleted", bookId);
    }
}
