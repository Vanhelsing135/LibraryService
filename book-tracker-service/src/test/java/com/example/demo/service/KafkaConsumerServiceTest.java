package com.example.demo.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KafkaConsumerServiceTest {

    @Mock
    private BookTrackerService bookTrackerService;

    @InjectMocks
    private KafkaConsumerService kafkaConsumerService;

    @Test
    void handleBookCreated_ShouldCallCreateBookRecord() {
        kafkaConsumerService.handleBookCreated("1");
        verify(bookTrackerService).createBookRecord(1);
    }

    @Test
    void handleBookDeleted_ShouldCallDeleteBookRecord() {
        kafkaConsumerService.handleBookDeleted("1");
        verify(bookTrackerService).deleteBookRecord(1);
    }
}
