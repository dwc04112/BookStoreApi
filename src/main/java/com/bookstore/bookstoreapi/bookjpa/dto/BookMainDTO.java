package com.bookstore.bookstoreapi.bookjpa.dto;

import lombok.Data;

@Data
public class BookMainDTO {
    private long bid;
    private String bookThumb;
    private String bookTitle;
    private String bookKeyword;
    private String bookAuthor;
    private String bookPublisher;
    private int bookSalePrice;


    public BookMainDTO(long bid,
                       String bookThumb,
                       String bookTitle,
                       String bookKeyword,
                       String bookAuthor,
                       String bookPublisher,
                       int bookSalePrice){
        this.bid = bid;
        this.bookThumb = bookThumb;
        this.bookTitle = bookTitle;
        this.bookKeyword = bookKeyword;
        this.bookAuthor = bookAuthor;
        this.bookPublisher = bookPublisher;
        this.bookSalePrice = bookSalePrice;
    }
}
