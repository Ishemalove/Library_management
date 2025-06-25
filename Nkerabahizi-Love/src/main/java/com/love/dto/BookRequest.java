package com.love.dto;

import com.love.enums.BookAvailabilityStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class BookRequest {
    
    @NotBlank(message = "Title is required")
    private String title;
    
    @NotBlank(message = "Author is required")
    private String author;
    
    @NotBlank(message = "ISBN is required")
    private String isbn;
    
    @NotNull(message = "Availability status is required")
    private BookAvailabilityStatus availabilityStatus;
    
    // Default constructor
    public BookRequest() {
    }
    
    // Constructor with parameters
    public BookRequest(String title, String author, String isbn, BookAvailabilityStatus availabilityStatus) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.availabilityStatus = availabilityStatus;
    }
    
    // Getters and Setters
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getAuthor() {
        return author;
    }
    
    public void setAuthor(String author) {
        this.author = author;
    }
    
    public String getIsbn() {
        return isbn;
    }
    
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    
    public BookAvailabilityStatus getAvailabilityStatus() {
        return availabilityStatus;
    }
    
    public void setAvailabilityStatus(BookAvailabilityStatus availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }
} 