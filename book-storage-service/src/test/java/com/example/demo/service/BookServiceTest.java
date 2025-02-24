package com.example.demo.service;

import com.example.bookstorage.dto.BookDTO;
import com.example.bookstorage.dto.BookRequestDTO;
import com.example.bookstorage.entity.Book;
import com.example.bookstorage.repository.BookRepository;
import com.example.bookstorage.service.BookService;
import com.example.bookstorage.service.KafkaProducerService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @Mock
    private BookRepository bookRepository;

    @Mock
    private KafkaProducerService kafkaProducerService;

    @InjectMocks
    private BookService bookService;

    private Book book;
    private BookRequestDTO bookRequestDTO;

    @BeforeEach
    void createBookAndDTO() {
        book = new Book(1, "ISBN", "Author", "Title", "Genre", "Description");
        bookRequestDTO = new BookRequestDTO();
        bookRequestDTO.setAuthor("Author");
        bookRequestDTO.setDescription("Description");
        bookRequestDTO.setGenre("Genre");
        bookRequestDTO.setISBN("ISBN");
        bookRequestDTO.setTitle("Title");
    }

    @Test
    void createBook_shouldCreateAndSendMessage() {
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        doNothing().when(kafkaProducerService).sendBookCreatedMessage(anyString());

        BookDTO result = bookService.createBook(bookRequestDTO);

        assertNotNull(result);
        assertEquals(book.getTitle(), result.getTitle());
        verify(bookRepository).save(any(Book.class));
        verify(kafkaProducerService).sendBookCreatedMessage(anyString());
    }

    @Test
    void updateBook_shouldUpdateBook() {
        when(bookRepository.findById(anyInt())).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        BookDTO bookDTO = new BookDTO();
        bookDTO.setGenre("upd Genre");
        bookDTO.setAuthor("upd Author");
        bookDTO.setDescription("upd Description");
        bookDTO.setTitle("upd Title");
        bookDTO.setISBN("upd ISBN");

        BookDTO updBook = bookService.updateBook(1, bookDTO);

        assertNotNull(updBook);
        assertEquals("upd Author", updBook.getAuthor());
        assertEquals("upd Title", updBook.getTitle());
        verify(bookRepository).save(any(Book.class));
    }

    @Test
    void deleteBook_shouldDeleteAndSendMessage() {
        when(bookRepository.existsById(anyInt())).thenReturn(true);
        doNothing().when(bookRepository).deleteById(anyInt());
        doNothing().when(kafkaProducerService).sendBookDeletedMessage(anyString());

        bookService.deleteBook(1);

        verify(bookRepository).deleteById(anyInt());
        verify(kafkaProducerService).sendBookDeletedMessage(anyString());
    }

    @Test
    void getBookById_shouldReturnBook() {
        when(bookRepository.findById(anyInt())).thenReturn(Optional.of(book));

        BookDTO result = bookService.getBookById(1);

        assertNotNull(result);
        assertEquals(book.getTitle(), result.getTitle());
    }

    @Test
    void getBookById_shouldThrowExceptionWhenNotFound() {
        when(bookRepository.findById(anyInt())).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> bookService.getBookById(1));
        assertEquals("book with id: 1 doesn't exist", exception.getMessage());
    }

    @Test
    void getAllBooks_shouldReturnList() {
        when(bookRepository.findAll()).thenReturn(List.of(book));

        List<BookDTO> result = bookService.getAllBooks();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(book.getTitle(), result.get(0).getTitle());
    }

    @Test
    void getBookByISBN_shouldReturnBook() {
        when(bookRepository.getByISBN(anyString())).thenReturn(Optional.of(book));

        BookDTO result = bookService.getBookByISBN("ISBN");

        assertNotNull(result);
        assertEquals(book.getTitle(), result.getTitle());
    }

    @Test
    void getBookByISBN_shouldThrowExceptionWhenNotFound() {
        when(bookRepository.getByISBN(anyString())).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> bookService.getBookByISBN("ISBN"));
        assertEquals("book with ISBN: ISBN doesn't exist", exception.getMessage());
    }}
