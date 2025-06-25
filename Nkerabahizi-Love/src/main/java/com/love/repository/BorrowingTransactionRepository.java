package com.love.repository;

import com.love.entity.Book;
import com.love.entity.BorrowingTransaction;
import com.love.enums.BorrowingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BorrowingTransactionRepository extends JpaRepository<BorrowingTransaction, Long> {
    
    /**
     * Find all transactions for a specific book
     * @param book the book to find transactions for
     * @return List of transactions for the book
     */
    List<BorrowingTransaction> findByBook(Book book);
    
    /**
     * Find all transactions by status
     * @param status the status to filter by
     * @return List of transactions with the specified status
     */
    List<BorrowingTransaction> findByStatus(BorrowingStatus status);
    
    /**
     * Find all transactions for a specific book with a specific status
     * @param book the book to find transactions for
     * @param status the status to filter by
     * @return List of transactions for the book with the specified status
     */
    List<BorrowingTransaction> findByBookAndStatus(Book book, BorrowingStatus status);
    
    /**
     * Find the most recent pending transaction for a book
     * @param book the book to find the transaction for
     * @return Optional containing the most recent pending transaction if found
     */
    Optional<BorrowingTransaction> findFirstByBookAndStatusOrderByBorrowDateDesc(Book book, BorrowingStatus status);
} 