package com.bookstore.bookstoreapi.bookjpa.model;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findBookByIsDel(String isDel);

    Optional<Book> findBookByBidAndIsDel(long bid, String isDel);

    Book findTopByOrderByBidDesc();

}
