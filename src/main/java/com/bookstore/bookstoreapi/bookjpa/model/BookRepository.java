package com.bookstore.bookstoreapi.bookjpa.model;


import com.bookstore.bookstoreapi.bookjpa.dto.*;
import org.apache.ibatis.annotations.Param;
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

    // List<BookMainDTO> findBookByBookTitleContainingAndIsDel(String searchTitle, String IsDel);
    //List<BookMainDTO> findBookByBookTitleRegexAndIsDel(String searchTitle, String IsDel);

    @Query(value = "select b.bid, b.bookThumb ,b.bookTitle ,b.bookKeyword, b.bookAuthor, b.bookPublisher From Book b where (b.bookTitle REGEXP :searchTitle)", nativeQuery = true)
    List<BookMainInterface> searchByRegExp(@Param("searchTitle") String searchTitle);


    @Query("select mid From Book where bid= ?1")
    Long getMemberIdByBid(long bid);

    @Query("select b From Book b where b.bid= ?1 and b.isDel='N'")
    Book getWishBook(long bid);

    @Transactional
    @Modifying
    @Query("UPDATE Book SET isDel = ?1 WHERE mid=?2")
    void updateIsDel(String isDel, Long mid);
    List<BookMainDTO> findBookBy();

    //bookThumb ,bookTitle ,bookKeyword, bookAuthor, bookPublisher,bookContent ,bookPreview
    @Query(value = "select bid, bookThumb ,bookTitle ,bookKeyword, bookAuthor, bookPublisher,bookContent ,bookPreview FROM Book where bid between 1 and 5", nativeQuery = true)
    List<BookMainDetailInterface> getMainBook();

    List<BookMainDTO> findBookByBookKeywordContainingAndIsDel(String keyword, String IsDel);
    List<BookMainDTO> findBookByBookKeywordContainingAndBookKeywordContainingAndIsDel(String keyword1, String keyword2,String IsDel);
    List<BookMainDTO> findBookByBookKeywordContainingAndBookKeywordContainingAndBookKeywordContainingAndIsDel(String keyword1, String keyword2, String keyword3, String IsDel);
    List<BookMainDTO> findBookByBookKeywordContainingAndBookKeywordContainingAndBookKeywordContainingAndBookKeywordContainingAndIsDel(String keyword1, String keyword2, String keyword3, String keyword4, String IsDel);
    List<BookMainDTO> findBookByBookKeywordContainingAndBookKeywordContainingAndBookKeywordContainingAndBookKeywordContainingAndBookKeywordContainingAndIsDel(String keyword1, String keyword2, String keyword3, String keyword4, String keyword5, String IsDel);

    List<BookMainDTO> findBookByBookTagStartingWithAndIsDel(String Tag, String isDel);
}
