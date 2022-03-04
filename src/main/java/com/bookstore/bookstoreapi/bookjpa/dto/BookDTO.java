package com.bookstore.bookstoreapi.bookjpa.dto;


import lombok.Data;

import java.time.LocalDate;

@Data
public class BookDTO {
    private Long bid;
    private long mid;
    private String bookTitle;
    private String bookSubTitle;
    private String bookAuthor;
    private String bookTranslator;
    private String bookContent;
    private String bookIndex;
    private String bookPreview;
    private int bookPage;
    private String bookIsbn;
    private int bookPrice;
    private int bookSalePrice;
    private String bookSize;
    private String bookThumb;
    private String bookPublisher;
    private LocalDate bookPublishedDate;
    private String bookTag;
    private String bookDetailTag;
    private String bookKeyword;
    private String isDel;

}
