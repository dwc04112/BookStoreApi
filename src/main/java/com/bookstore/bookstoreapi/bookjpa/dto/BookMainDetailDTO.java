package com.bookstore.bookstoreapi.bookjpa.dto;

public class BookMainDetailDTO {
    private String bookThumb;
    private String bookTitle;
    private String bookKeyword;
    private String bookAuthor;
    private String bookPublisher;
    private String bookContent;
    private String bookPreview;


    public BookMainDetailDTO(String bookThumb,
                           String bookTitle,
                           String bookKeyword,
                           String bookAuthor,
                           String bookPublisher,
                             String bookContent,
                             String bookPreview){
        this.bookThumb = bookThumb;
        this.bookTitle = bookTitle;
        this.bookKeyword = bookKeyword;
        this.bookAuthor = bookAuthor;
        this.bookPublisher = bookPublisher;
        this.bookContent = bookContent;
        this.bookPreview = bookPreview;
    }
}
