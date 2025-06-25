package com.love.controller;

import com.love.dto.BorrowingRequest;
import com.love.dto.BorrowingResponse;
import com.love.enums.BorrowingStatus;
import com.love.service.BorrowingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/borrowings")
@CrossOrigin(origins = "*")
public class BorrowingController {
    
    private final BorrowingService borrowingService;
    
    @Autowired
    public BorrowingController(BorrowingService borrowingService) {
        this.borrowingService = borrowingService;
    }
    
    /**
     * Create a new borrowing transaction
     * POST /api/borrowings
     * @param borrowingRequest the borrowing request containing transaction details
     * @return ResponseEntity with created transaction details
     */
    @PostMapping
    public ResponseEntity<BorrowingResponse> createBorrowingTransaction(@Valid @RequestBody BorrowingRequest borrowingRequest) {
        try {
            BorrowingResponse createdTransaction = borrowingService.createBorrowingTransaction(borrowingRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTransaction);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Return a book (update transaction status and book availability)
     * PUT /api/borrowings/{transactionId}/return
     * @param transactionId the ID of the borrowing transaction
     * @return ResponseEntity with updated transaction details
     */
    @PutMapping("/{transactionId}/return")
    public ResponseEntity<BorrowingResponse> returnBook(@PathVariable Long transactionId) {
        try {
            BorrowingResponse updatedTransaction = borrowingService.returnBook(transactionId);
            return ResponseEntity.ok(updatedTransaction);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Get all borrowing transactions
     * GET /api/borrowings
     * @return ResponseEntity with list of all transactions
     */
    @GetMapping
    public ResponseEntity<List<BorrowingResponse>> getAllBorrowingTransactions() {
        List<BorrowingResponse> transactions = borrowingService.getAllBorrowingTransactions();
        return ResponseEntity.ok(transactions);
    }
    
    /**
     * Get borrowing transaction by ID
     * GET /api/borrowings/{transactionId}
     * @param transactionId the ID of the transaction
     * @return ResponseEntity with transaction details if found
     */
    @GetMapping("/{transactionId}")
    public ResponseEntity<BorrowingResponse> getBorrowingTransactionById(@PathVariable Long transactionId) {
        Optional<BorrowingResponse> transaction = borrowingService.getBorrowingTransactionById(transactionId);
        return transaction.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Get borrowing transactions by status
     * GET /api/borrowings/status/{status}
     * @param status the status to filter by
     * @return ResponseEntity with list of transactions with the specified status
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<BorrowingResponse>> getBorrowingTransactionsByStatus(@PathVariable BorrowingStatus status) {
        List<BorrowingResponse> transactions = borrowingService.getBorrowingTransactionsByStatus(status);
        return ResponseEntity.ok(transactions);
    }
} 