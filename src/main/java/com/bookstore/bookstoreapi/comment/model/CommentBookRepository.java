package com.bookstore.bookstoreapi.comment.model;

import com.bookstore.bookstoreapi.comment.dto.CommentBookMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface CommentBookRepository extends JpaRepository<CommentBook, Long> {

    @Query("select c.cid as cid," +
            "c.ratings as ratings," +
            "c.content as content," +
            "c.popularity as popularity," +
            "c.commentDate as commentDate," +
            "c.commentTime as commentTime," +
            "c.book.bookTitle as bookTitle," +
            "c.book.bookThumb as bookThumb," +
            "c.book.bookAuthor as bookAuthor," +
            "c.book.bookPublisher as bookPublisher," +
            "c.book.bookTag as bookTag FROM CommentBook c where c.mid = ?1 and c.isDel = ?2 order by c.commentDate desc ,c.commentTime desc")
    Page<CommentBookMapping> findAllByBidAndIsDel(Long mid, String isDel, Pageable pageable);

    @Query("select c.cid as cid," +
            "c.ratings as ratings," +
            "c.content as content," +
            "c.popularity as popularity," +
            "c.commentDate as commentDate," +
            "c.commentTime as commentTime," +
            "c.book.bookTitle as bookTitle," +
            "c.book.bookThumb as bookThumb," +
            "c.book.bookAuthor as bookAuthor," +
            "c.book.bookPublisher as bookPublisher," +
            "c.book.bookTag as bookTag FROM CommentBook c " +
            "where c.mid = ?1 and c.isDel = ?2 and " +
            "c.commentDate between ?3 and ?4 and c.commentTime between ?5 and ?6  order by c.commentDate desc ,c.commentTime desc")
    Page<CommentBookMapping> findAllByDate(Long mid,
                                           String isDel,
                                           LocalDate startDate,
                                           LocalDate endDate,
                                           LocalTime startTime,
                                           LocalTime endTime,
                                           Pageable pageable);
}
