package com.love.dto;

import com.love.enums.BorrowingStatus;

import java.time.LocalDateTime;

public class BorrowingResponse {
    
    private Long id;
    private String bookTitle;
    private String bookIsbn;
    private String borrowerName;
    private LocalDateTime borrowDate;
    private LocalDateTime returnDate;
    private BorrowingStatus status;
    
    // Default constructor
    public BorrowingResponse() {
    }
    
    // Constructor with parameters
    public BorrowingResponse(Long id, String bookTitle, String bookIsbn, String borrowerName, 
                           LocalDateTime borrowDate, LocalDateTime returnDate, BorrowingStatus status) {
        this.id = id;
        this.bookTitle = bookTitle;
        this.bookIsbn = bookIsbn;
        this.borrowerName = borrowerName;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.status = status;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getBookTitle() {
        return bookTitle;
    }
    
    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }
    
    public String getBookIsbn() {
        return bookIsbn;
    }
    
    public void setBookIsbn(String bookIsbn) {
        this.bookIsbn = bookIsbn;
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
    
    public LocalDateTime getReturnDate() {
        return returnDate;
    }
    
    public void setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }
    
    public BorrowingStatus getStatus() {
        return status;
    }
    
    public void setStatus(BorrowingStatus status) {
        this.status = status;
    }
} 