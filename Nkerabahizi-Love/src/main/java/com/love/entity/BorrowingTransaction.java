package com.love.entity;

import com.love.enums.BorrowingStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "borrowing_transactions")
public class BorrowingTransaction {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "Book is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;
    
    @NotBlank(message = "Borrower name is required")
    @Column(nullable = false)
    private String borrowerName;
    
    @NotNull(message = "Borrow date is required")
    @Column(nullable = false)
    private LocalDateTime borrowDate;
    
    @Column
    private LocalDateTime returnDate;
    
    @NotNull(message = "Status is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BorrowingStatus status;
    
    // Default constructor
    public BorrowingTransaction() {
        this.status = BorrowingStatus.PENDING;
    }
    
    // Constructor with parameters
    public BorrowingTransaction(Book book, String borrowerName, LocalDateTime borrowDate) {
        this.book = book;
        this.borrowerName = borrowerName;
        this.borrowDate = borrowDate;
        this.status = BorrowingStatus.PENDING;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Book getBook() {
        return book;
    }
    
    public void setBook(Book book) {
        this.book = book;
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
    
    @Override
    public String toString() {
        return "BorrowingTransaction{" +
                "id=" + id +
                ", book=" + (book != null ? book.getTitle() : "null") +
                ", borrowerName='" + borrowerName + '\'' +
                ", borrowDate=" + borrowDate +
                ", returnDate=" + returnDate +
                ", status=" + status +
                '}';
    }
} 