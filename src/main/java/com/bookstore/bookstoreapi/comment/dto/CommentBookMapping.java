package com.bookstore.bookstoreapi.comment.dto;


import com.bookstore.bookstoreapi.bookjpa.model.Book;

import java.time.LocalDate;
import java.time.LocalTime;

public interface CommentBookMapping {

    Long getCid();
    int getRatings();
    String getContent();
    int getPopularity();
    LocalDate getCommentDate();
    LocalTime getCommentTime();

    String getBookTitle();
    String getBookThumb();
    String getBookAuthor();
    String getBookPublisher();
    String getBookTag();
}
