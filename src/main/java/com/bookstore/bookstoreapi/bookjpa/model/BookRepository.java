package com.bookstore.bookstoreapi.bookjpa.model;


import com.bookstore.bookstoreapi.bookjpa.dto.BookDTO;
import com.bookstore.bookstoreapi.bookjpa.dto.BookMainDTO;
import com.bookstore.bookstoreapi.bookjpa.dto.BookMainDetailDTO;
import com.bookstore.bookstoreapi.bookjpa.dto.BookMainDetailInterface;
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
