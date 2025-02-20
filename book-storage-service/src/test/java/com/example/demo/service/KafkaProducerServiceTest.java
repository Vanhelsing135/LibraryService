package com.example.demo.service;

import com.example.bookstorage.service.KafkaProducerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KafkaProducerServiceTest {

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @InjectMocks
    private KafkaProducerService kafkaProducerService;

    @Test
    void sendBookCreatedMessage_ShouldSendMessageToKafka() {
        kafkaProducerService.sendBookCreatedMessage("1");
        verify(kafkaTemplate).send("book-created", "1");
    }

    @Test
    void sendBookDeletedMessage_ShouldSendMessageToKafka() {
        kafkaProducerService.sendBookDeletedMessage("1");
        verify(kafkaTemplate).send("book-deleted", "1");
    }
}
