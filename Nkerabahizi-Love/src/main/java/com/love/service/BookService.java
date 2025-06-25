package com.love.service;

import com.love.dto.BookRequest;
import com.love.dto.BookResponse;
import com.love.entity.Book;
import com.love.enums.BookAvailabilityStatus;
import com.love.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookService {
    
    private final BookRepository bookRepository;
    
    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    
    /**
     * Create a new book
     * @param bookRequest the book request containing book details
     * @return the created book response
     * @throws RuntimeException if book with ISBN already exists
     */
    public BookResponse createBook(BookRequest bookRequest) {
        // Check if book with ISBN already exists
        if (bookRepository.existsByIsbn(bookRequest.getIsbn())) {
            throw new RuntimeException("Book with ISBN " + bookRequest.getIsbn() + " already exists");
        }
        
        Book book = new Book();
        book.setTitle(bookRequest.getTitle());
        book.setAuthor(bookRequest.getAuthor());
        book.setIsbn(bookRequest.getIsbn());
        book.setAvailabilityStatus(bookRequest.getAvailabilityStatus());
        
        Book savedBook = bookRepository.save(book);
        return convertToBookResponse(savedBook);
    }
    
    /**
     * Find a book by ISBN
     * @param isbn the ISBN to search for
     * @return Optional containing the book response if found
     */
    @Transactional(readOnly = true)
    public Optional<BookResponse> findBookByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn)
                .map(this::convertToBookResponse);
    }
    
    /**
     * Get book availability status by ISBN
     * @param isbn the ISBN to check
     * @return the availability status as string
     * @throws RuntimeException if book not found
     */
    @Transactional(readOnly = true)
    public String getBookAvailability(String isbn) {
        Book book = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new RuntimeException("Book with ISBN " + isbn + " not found"));
        
        return book.getAvailabilityStatus().toString();
    }
    
    /**
     * Update book availability status
     * @param isbn the ISBN of the book to update
     * @param status the new availability status
     * @return the updated book response
     * @throws RuntimeException if book not found
     */
    public BookResponse updateBookAvailability(String isbn, BookAvailabilityStatus status) {
        Book book = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new RuntimeException("Book with ISBN " + isbn + " not found"));
        
        book.setAvailabilityStatus(status);
        Book updatedBook = bookRepository.save(book);
        return convertToBookResponse(updatedBook);
    }
    
    /**
     * Get all books
     * @return list of all book responses
     */
    @Transactional(readOnly = true)
    public List<BookResponse> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(this::convertToBookResponse)
                .collect(Collectors.toList());
    }
    
    /**
     * Get all available books
     * @return list of available book responses
     */
    @Transactional(readOnly = true)
    public List<BookResponse> getAvailableBooks() {
        return bookRepository.findByAvailabilityStatus(BookAvailabilityStatus.AVAILABLE).stream()
                .map(this::convertToBookResponse)
                .collect(Collectors.toList());
    }
    
    /**
     * Convert Book entity to BookResponse DTO
     * @param book the book entity
     * @return the book response DTO
     */
    private BookResponse convertToBookResponse(Book book) {
        return new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn(),
                book.getAvailabilityStatus()
        );
    }
} 