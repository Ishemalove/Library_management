package com.love.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class BorrowingRequest {
    
    @NotBlank(message = "ISBN is required")
    private String isbn;
    
    @NotBlank(message = "Borrower name is required")
    private String borrowerName;
    
    @NotNull(message = "Borrow date is required")
    private LocalDateTime borrowDate;
    
    // Default constructor
    public BorrowingRequest() {
    }
    
    // Constructor with parameters
    public BorrowingRequest(String isbn, String borrowerName, LocalDateTime borrowDate) {
        this.isbn = isbn;
        this.borrowerName = borrowerName;
        this.borrowDate = borrowDate;
    }
    
    // Getters and Setters
    public String getIsbn() {
        return isbn;
    }
    
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    
    public String getBorrowerName() {
        return borrowerName;
    }
    
    public void setBorrowerName(String borrowerName) {
        this.borrowerName = borrowerName;
    }
    
    public LocalDateTime getBorrowDate() {
        return borrowDate;
    }
    
    public void setBorrowDate(LocalDateTime borrowDate) {
        this.borrowDate = borrowDate;
    }
} 