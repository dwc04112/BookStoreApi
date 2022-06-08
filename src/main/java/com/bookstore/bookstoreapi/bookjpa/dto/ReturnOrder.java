package com.bookstore.bookstoreapi.bookjpa.dto;

import lombok.Data;

@Data
public class ReturnOrder {
    private long bid;
    private String bookThumb;
    private String bookTitle;
    private String bookAuthor;
    private String bookPublisher;
    private int bookSalePrice;

    private int bookCount;
}
