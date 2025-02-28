package com.example.bookstorage.service;

import com.example.bookstorage.dto.BookDTO;
import com.example.bookstorage.dto.BookRequestDTO;
import com.example.bookstorage.entity.Book;
import com.example.bookstorage.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {
    private final BookRepository bookRepository;
    private final KafkaProducerService kafkaProducerService;

    @Transactional
    public BookDTO createBook(BookRequestDTO requestDTO) {
        log.info("Adding new book");

        if (bookRepository.getByISBN(requestDTO.getISBN()).isPresent()) {
            log.warn("Attempt to create duplicate book with ISBN {}", requestDTO.getISBN());
            throw new IllegalArgumentException("Book with ISBN :" + requestDTO.getISBN() + " already exists");
        }
        Book book = new Book();
        book.setISBN(requestDTO.getISBN());
        book.setAuthor(requestDTO.getAuthor());
        book.setTitle(requestDTO.getTitle());
        book.setGenre(requestDTO.getGenre());
        book.setDescription(requestDTO.getDescription());
        Book newBook = bookRepository.save(book);
        kafkaProducerService.sendBookCreatedMessage(String.valueOf(book.getId()));
        log.info("Added successfully");
        return convertToDTO(newBook);
    }

    public BookDTO convertToDTO(Book book) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setAuthor(book.getAuthor());
        bookDTO.setDescription(book.getDescription());
        bookDTO.setISBN(book.getISBN());
        bookDTO.setTitle(book.getTitle());
        bookDTO.setGenre(book.getGenre());
        return bookDTO;
    }

    @Transactional
    public void deleteBook(Integer id) {
        log.info("Deleting book with id {}", id);
        if (!bookRepository.existsById(id)) {
            log.warn("book with id {} doesn't exist", id);
            throw new IllegalArgumentException("book with id: " + id + " doesn't exist");
        }

        bookRepository.deleteById(id);
        log.info("Deleted successfully");
        kafkaProducerService.sendBookDeletedMessage(String.valueOf(id));
    }

    @Transactional
    public BookDTO updateBook(Integer id, BookDTO request) {
        log.info("Updating book with id {}", id);
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Book with id {} doesn't exist", id);
                    return new EntityNotFoundException("book with id " + id + " doesn't exist");
                });

        book.setISBN(request.getISBN());
        book.setTitle(request.getTitle());
        book.setGenre(request.getGenre());
        book.setDescription(request.getDescription());
        book.setAuthor(request.getAuthor());

        log.info("Updated successfully");
        return convertToDTO(bookRepository.save(book));
    }

    public BookDTO getBookById(Integer id) {
        log.info("Getting book by id {}", id);
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Book with id {} doesn't exist", id);
                    return new EntityNotFoundException("book with id: " + id + " doesn't exist");
                });

        log.info("Successfully");
        return convertToDTO(book);
    }

    public BookDTO getBookByISBN(String ISBN) {
        log.info("Getting book by ISBN {}", ISBN);
        Book book = bookRepository.getByISBN(ISBN)
                .orElseThrow(() -> {
                    log.warn("Book with ISBN {} doesn't exist", ISBN);
                    return new EntityNotFoundException("book with ISBN: " + ISBN + " doesn't exist");
                });

        log.info("Successfully");
        return convertToDTO(book);
    }

    public List<BookDTO> getAllBooks() {
        log.info("Getting all books");
        return bookRepository.findAll().stream()
                .map(this::convertToDTO)
                .toList();
    }
}
