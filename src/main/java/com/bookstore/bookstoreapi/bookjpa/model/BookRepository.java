package com.bookstore.bookstoreapi.bookjpa.model;


import com.bookstore.bookstoreapi.bookjpa.dto.BookMainDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findBookByIsDel(String isDel);

    Optional<Book> findBookByBidAndIsDel(long bid, String isDel);

    Book findTopByOrderByBidDesc();

    @Query("select mid From Book where bid= ?1")
    Long getMemberIdByBid(long bid);

    @Transactional
    @Modifying
    @Query("UPDATE Book SET isDel = ?1 WHERE mid=?2")
    void updateIsDel(String isDel, Long mid);


    @Query("select bThumb,bTitle,bKeyword,bAuthor From Book")
    List<BookMain> getBookMain();

    List<BookMainDTO> findBookBy();
}
