package com.bookstore.bookstoreapi.bookjpa.model;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findBookByIsDel(String isDel);

    Book findBookByBidAndIsDel(long bid, String isDel);

    Book findTopByOrderByBidDesc();

}
