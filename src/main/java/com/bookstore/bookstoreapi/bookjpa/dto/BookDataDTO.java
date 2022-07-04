package com.bookstore.bookstoreapi.bookjpa.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BookDataDTO {
    private long bid;
    private String bookTitle;
    private String bookAuthor;
    private String bookPublisher;
    private String bookIsbn;
    private int bookSalePrice;
    private LocalDate bookPublishedDate;
    private String isDel;

    public BookDataDTO(long bid,
                       String bookTitle,
                       String bookKeyword,
                       String bookAuthor,
                       String bookPublisher,
                       String bookIsbn,
                       int bookSalePrice,
                       LocalDate bookPublishedDate,
                       String isDel){
        this.bid = bid;
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.bookPublisher = bookPublisher;
        this.bookIsbn = bookIsbn;
        this.bookSalePrice = bookSalePrice;
        this.bookPublishedDate = bookPublishedDate;
        this.isDel = isDel;
    }
}
