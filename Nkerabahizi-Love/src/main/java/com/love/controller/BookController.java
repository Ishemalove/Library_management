package com.love.controller;

import com.love.dto.BookRequest;
import com.love.dto.BookResponse;
import com.love.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "*")
public class BookController {
    
    private final BookService bookService;
    
    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }
    
    /**
     * Create a new book
     * POST /api/books
     * @param bookRequest the book request containing book details
     * @return ResponseEntity with created book details
     */
    @PostMapping
    public ResponseEntity<BookResponse> createBook(@Valid @RequestBody BookRequest bookRequest) {
        try {
            BookResponse createdBook = bookService.createBook(bookRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Retrieve book details by ISBN
     * GET /api/books/{isbn}
     * @param isbn the ISBN to search for
     * @return ResponseEntity with book details if found
     */
    @GetMapping("/{isbn}")
    public ResponseEntity<BookResponse> getBookByIsbn(@PathVariable String isbn) {
        Optional<BookResponse> book = bookService.findBookByIsbn(isbn);
        return book.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Retrieve book availability by ISBN
     * GET /api/books/{isbn}/availability
     * @param isbn the ISBN to check
     * @return ResponseEntity with availability status
     */
    @GetMapping("/{isbn}/availability")
    public ResponseEntity<String> getBookAvailability(@PathVariable String isbn) {
        try {
            String availability = bookService.getBookAvailability(isbn);
            return ResponseEntity.ok(availability);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Get all books
     * GET /api/books
     * @return ResponseEntity with list of all books
     */
    @GetMapping
    public ResponseEntity<List<BookResponse>> getAllBooks() {
        List<BookResponse> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }
    
    /**
     * Get all available books
     * GET /api/books/available
     * @return ResponseEntity with list of available books
     */
    @GetMapping("/available")
    public ResponseEntity<List<BookResponse>> getAvailableBooks() {
        List<BookResponse> books = bookService.getAvailableBooks();
        return ResponseEntity.ok(books);
    }
} 