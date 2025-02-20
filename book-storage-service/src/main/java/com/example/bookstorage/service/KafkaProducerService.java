package com.example.bookstorage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendBookCreatedMessage(String bookId) {
        kafkaTemplate.send("book-created", bookId);
    }

    public void sendBookDeletedMessage(String bookId) {
        kafkaTemplate.send("book-deleted", bookId);
    }
}
