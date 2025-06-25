package com.love.service;

import com.love.dto.BorrowingRequest;
import com.love.dto.BorrowingResponse;
import com.love.entity.Book;
import com.love.entity.BorrowingTransaction;
import com.love.enums.BookAvailabilityStatus;
import com.love.enums.BorrowingStatus;
import com.love.repository.BookRepository;
import com.love.repository.BorrowingTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class BorrowingService {
    
    private final BorrowingTransactionRepository borrowingTransactionRepository;
    private final BookRepository bookRepository;
    private final BookService bookService;
    
    @Autowired
    public BorrowingService(BorrowingTransactionRepository borrowingTransactionRepository, 
                          BookRepository bookRepository,
                          BookService bookService) {
        this.borrowingTransactionRepository = borrowingTransactionRepository;
        this.bookRepository = bookRepository;
        this.bookService = bookService;
    }
    
    /**
     * Create a new borrowing transaction
     * @param borrowingRequest the borrowing request containing transaction details
     * @return the created borrowing response
     * @throws RuntimeException if book is not available or not found
     */
    public BorrowingResponse createBorrowingTransaction(BorrowingRequest borrowingRequest) {
        // Check if book exists and is available
        String availability = bookService.getBookAvailability(borrowingRequest.getIsbn());
        
        if (!BookAvailabilityStatus.AVAILABLE.toString().equals(availability)) {
            throw new RuntimeException("Book with ISBN " + borrowingRequest.getIsbn() + " is not available for borrowing");
        }
        
        // Get the book entity
        Book book = bookRepository.findByIsbn(borrowingRequest.getIsbn())
                .orElseThrow(() -> new RuntimeException("Book with ISBN " + borrowingRequest.getIsbn() + " not found"));
        
        // Create borrowing transaction
        BorrowingTransaction transaction = new BorrowingTransaction();
        transaction.setBook(book);
        transaction.setBorrowerName(borrowingRequest.getBorrowerName());
        transaction.setBorrowDate(borrowingRequest.getBorrowDate());
        transaction.setStatus(BorrowingStatus.PENDING);
        
        // Save the transaction
        BorrowingTransaction savedTransaction = borrowingTransactionRepository.save(transaction);
        
        // Update book availability to BORROWED
        book.setAvailabilityStatus(BookAvailabilityStatus.BORROWED);
        bookRepository.save(book);
        
        return convertToBorrowingResponse(savedTransaction);
    }
    
    /**
     * Return a book (update transaction status and book availability)
     * @param transactionId the ID of the borrowing transaction
     * @return the updated borrowing response
     * @throws RuntimeException if transaction not found or already returned
     */
    public BorrowingResponse returnBook(Long transactionId) {
        BorrowingTransaction transaction = borrowingTransactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Borrowing transaction with ID " + transactionId + " not found"));
        
        if (BorrowingStatus.RETURNED.equals(transaction.getStatus())) {
            throw new RuntimeException("Book has already been returned");
        }
        
        // Update transaction status
        transaction.setStatus(BorrowingStatus.RETURNED);
        transaction.setReturnDate(LocalDateTime.now());
        
        // Update book availability
        bookService.updateBookAvailability(transaction.getBook().getIsbn(), BookAvailabilityStatus.AVAILABLE);
        
        BorrowingTransaction savedTransaction = borrowingTransactionRepository.save(transaction);
        return convertToBorrowingResponse(savedTransaction);
    }
    
    /**
     * Get all borrowing transactions
     * @return list of all borrowing responses
     */
    @Transactional(readOnly = true)
    public List<BorrowingResponse> getAllBorrowingTransactions() {
        return borrowingTransactionRepository.findAll().stream()
                .map(this::convertToBorrowingResponse)
                .collect(Collectors.toList());
    }
    
    /**
     * Get borrowing transactions by status
     * @param status the status to filter by
     * @return list of borrowing responses with the specified status
     */
    @Transactional(readOnly = true)
    public List<BorrowingResponse> getBorrowingTransactionsByStatus(BorrowingStatus status) {
        return borrowingTransactionRepository.findByStatus(status).stream()
                .map(this::convertToBorrowingResponse)
                .collect(Collectors.toList());
    }
    
    /**
     * Get borrowing transaction by ID
     * @param transactionId the ID of the transaction
     * @return Optional containing the borrowing response if found
     */
    @Transactional(readOnly = true)
    public Optional<BorrowingResponse> getBorrowingTransactionById(Long transactionId) {
        return borrowingTransactionRepository.findById(transactionId)
                .map(this::convertToBorrowingResponse);
    }
    
    /**
     * Convert BorrowingTransaction entity to BorrowingResponse DTO
     * @param transaction the borrowing transaction entity
     * @return the borrowing response DTO
     */
    private BorrowingResponse convertToBorrowingResponse(BorrowingTransaction transaction) {
        return new BorrowingResponse(
                transaction.getId(),
                transaction.getBook().getTitle(),
                transaction.getBook().getIsbn(),
                transaction.getBorrowerName(),
                transaction.getBorrowDate(),
                transaction.getReturnDate(),
                transaction.getStatus()
        );
    }
} 