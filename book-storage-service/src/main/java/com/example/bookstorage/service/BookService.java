package com.example.bookstorage.service;

import com.example.bookstorage.dto.BookDTO;
import com.example.bookstorage.dto.BookRequestDTO;
import com.example.bookstorage.entity.Book;
import com.example.bookstorage.repository.BookRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final KafkaProducerService kafkaProducerService;

    public BookDTO createBook(BookRequestDTO requestDTO) {
        Book book = new Book();
        book.setISBN(requestDTO.getISBN());
        book.setAuthor(requestDTO.getAuthor());
        book.setTitle(requestDTO.getTitle());
        book.setGenre(requestDTO.getGenre());
        book.setDescription(requestDTO.getDescription());
        Book newBook = bookRepository.save(book);
        kafkaProducerService.sendBookCreatedMessage(String.valueOf(book.getId()));
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

    public void deleteBook(Integer id) {
        if (!bookRepository.existsById(id)) {
            throw new IllegalArgumentException("book with id: " + id + "doesn't exist");
        }

        bookRepository.deleteById(id);
        kafkaProducerService.sendBookDeletedMessage(String.valueOf(id));
    }

    public BookDTO updateBook(Integer id, BookDTO request) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Книга с ID " + id + " не найдена"));

        book.setISBN(request.getISBN());
        book.setTitle(request.getTitle());
        book.setGenre(request.getGenre());
        book.setDescription(request.getDescription());
        book.setAuthor(request.getAuthor());

        return convertToDTO(bookRepository.save(book));
    }

    public BookDTO getBookById(Integer id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("book with id: " + id + "doesn't exist"));

        return convertToDTO(book);
    }

    public BookDTO getBookByISBN(String ISBN) {
        Book book = bookRepository.getByISBN(ISBN)
                .orElseThrow(() -> new IllegalArgumentException("book with ISBN: " + ISBN + "doesn't exist"));

        return convertToDTO(book);
    }

    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(this::convertToDTO)
                .toList();
    }
}
