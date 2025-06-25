package com.love.repository;

import com.love.entity.Book;
import com.love.enums.BookAvailabilityStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    
    /**
     * Find a book by its ISBN
     * @param isbn the ISBN to search for
     * @return Optional containing the book if found
     */
    Optional<Book> findByIsbn(String isbn);
    
    /**
     * Find all books by availability status
     * @param status the availability status to filter by
     * @return List of books with the specified status
     */
    List<Book> findByAvailabilityStatus(BookAvailabilityStatus status);
    
    /**
     * Check if a book exists by ISBN
     * @param isbn the ISBN to check
     * @return true if book exists, false otherwise
     */
    boolean existsByIsbn(String isbn);
} 