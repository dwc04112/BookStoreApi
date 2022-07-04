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


    List<BookDataDTO> findBookBy();

    // book & order 에서 사용
    Optional<Book> findBookByBidAndIsDel(long bid, String isDel);
    // admin
    Optional<Book> findBookByBid(long bid);


    Book findTopByOrderByBidDesc();



    //책 제목으로 검색
    @Query(value = "select b.bid, b.bookThumb ,b.bookTitle ,b.bookKeyword, b.bookAuthor, b.bookPublisher From Book b where replace (b.bookTitle,' ','') REGEXP :searchTitle and b.isDel=:isDel", nativeQuery = true)
    List<BookMainInterface> searchByRegExp(@Param("searchTitle") String searchTitle, @Param("isDel") String isDel);

    //책 키워드로 검색
    @Query(value = "select b.bid, b.bookThumb ,b.bookTitle ,b.bookKeyword, b.bookAuthor, b.bookPublisher From Book b where (b.bookKeyword REGEXP :searchKeyword) and b.isDel=:isDel ", nativeQuery = true)
    List<BookMainInterface> searchByRegExpKeyword(@Param("searchKeyword") String searchKeyword, @Param("isDel") String isDel);

    //책 제목과 키워드로 검색 (책 제목으로 검색한 결과를 우선순위로)
    @Query(value = "select b.bid, b.bookThumb ,b.bookTitle ,b.bookKeyword, b.bookAuthor, b.bookPublisher From Book b where replace (b.bookTitle,' ','') REGEXP :searchData or (b.bookKeyword REGEXP :searchData) and b.isDel=:isDel order by replace (b.bookTitle,' ','') REGEXP :searchData DESC ", nativeQuery = true)
    List<BookMainInterface> searchByTitleAndKeyword(@Param("searchData") String searchData, @Param("isDel") String isDel);


    @Query("select b From Book b where b.bid= ?1 and b.isDel='N'")
    Book getWishBook(long bid);

    @Transactional
    @Modifying
    @Query("UPDATE Book SET isDel = ?1 WHERE mid=?2")
    void updateIsDel(String isDel, Long mid);

    List<BookMainDTO> findBookByIsDel(String isDel);
    //2-2 책 상세화면 (info 인터페이스 사용)
    BookMainDTO getBookByBidAndIsDel(Long bid, String isDel);
    //2-3 order.vue 에서 호출 > getBookByBidAndIsDel
    // List<BookMainDTO> getBookByBidInAndIsDel(List<Long> bidArr, String isDel);

    //bookThumb ,bookTitle ,bookKeyword, bookAuthor, bookPublisher,bookContent ,bookPreview
    @Query(value = "select bid, bookThumb ,bookTitle ,bookKeyword, bookAuthor, bookPublisher,bookContent ,bookPreview FROM Book where bid between 1 and 5", nativeQuery = true)
    List<BookMainDetailInterface> getMainBook();

    List<BookMainDTO> findBookByBookTagStartingWithAndIsDel(String Tag, String isDel);
}
