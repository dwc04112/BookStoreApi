package com.bookstore.bookstoreapi.bookjpa.model;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    Book findTopByOrderByBidDesc();

}
