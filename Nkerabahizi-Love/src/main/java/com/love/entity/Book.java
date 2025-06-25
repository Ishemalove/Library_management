package com.love.entity;

import com.love.enums.BookAvailabilityStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "books")
public class Book {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Title is required")
    @Column(nullable = false)
    private String title;
    
    @NotBlank(message = "Author is required")
    @Column(nullable = false)
    private String author;
    
    @NotBlank(message = "ISBN is required")
    @Column(nullable = false, unique = true)
    private String isbn;
    
    @NotNull(message = "Availability status is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookAvailabilityStatus availabilityStatus;
    
    // Default constructor
    public Book() {
        this.availabilityStatus = BookAvailabilityStatus.AVAILABLE;
    }
    
    // Constructor with parameters
    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.availabilityStatus = BookAvailabilityStatus.AVAILABLE;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
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
    
    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", isbn='" + isbn + '\'' +
                ", availabilityStatus=" + availabilityStatus +
                '}';
    }
} 