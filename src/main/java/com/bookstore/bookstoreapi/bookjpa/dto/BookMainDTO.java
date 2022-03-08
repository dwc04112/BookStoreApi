package com.bookstore.bookstoreapi.bookjpa.dto;

import lombok.Data;

@Data
public class BookMainDTO {
    private String bookThumb;
    private String bookTitle;
    private String bookKeyword;
    private String bookAuthor;
    private String bookPublisher;


    public BookMainDTO(String bookThumb,
                       String bookTitle,
                       String bookKeyword,
                       String bookAuthor,
                       String bookPublisher){
        this.bookThumb = bookThumb;
        this.bookTitle = bookTitle;
        this.bookKeyword = bookKeyword;
        this.bookAuthor = bookAuthor;
        this.bookPublisher = bookPublisher;
    }
}
