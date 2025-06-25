package com.love.config;

import com.love.entity.Book;
import com.love.enums.BookAvailabilityStatus;
import com.love.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {
    
    private final BookRepository bookRepository;
    
    @Autowired
    public DataLoader(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    
    @Override
    public void run(String... args) throws Exception {
        // Load sample books if the database is empty
        if (bookRepository.count() == 0) {
            loadSampleBooks();
        }
    }
    
    private void loadSampleBooks() {
        // Sample books for testing
        Book book1 = new Book("The Great Gatsby", "F. Scott Fitzgerald", "978-0743273565");
        book1.setAvailabilityStatus(BookAvailabilityStatus.AVAILABLE);
        bookRepository.save(book1);
        
        Book book2 = new Book("To Kill a Mockingbird", "Harper Lee", "978-0446310789");
        book2.setAvailabilityStatus(BookAvailabilityStatus.AVAILABLE);
        bookRepository.save(book2);
        
        Book book3 = new Book("1984", "George Orwell", "978-0451524935");
        book3.setAvailabilityStatus(BookAvailabilityStatus.AVAILABLE);
        bookRepository.save(book3);
        
        Book book4 = new Book("Pride and Prejudice", "Jane Austen", "978-0141439518");
        book4.setAvailabilityStatus(BookAvailabilityStatus.AVAILABLE);
        bookRepository.save(book4);
        
        Book book5 = new Book("The Hobbit", "J.R.R. Tolkien", "978-0547928241");
        book5.setAvailabilityStatus(BookAvailabilityStatus.AVAILABLE);
        bookRepository.save(book5);
        
        System.out.println("Sample books loaded successfully!");
    }
} 