package com.bookstore.bookstoreapi.bookjpa.model;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Table(name = "book")
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Book {

    @Id
    @Column(name = "bid")
    private long bid;

    @Column(unique = true)
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
    private String bookKeyword;
    private String isDel;

    public void updateIsDel(String isDel){
        this.isDel = isDel;
    }

    public Book(Long bid,
                Long mid,
                String bookTitle,
                String bookSubTitle,
                String bookAuthor,
                String bookTranslator,
                String bookContent,
                String bookIndex,
                String bookPreview,
                int bookPage,
                String bookIsbn,
                int bookPrice,
                int bookSalePrice,
                String bookSize,
                String bookThumb,
                String bookPublisher,
                LocalDate bookPublishedDate,
                String bookTag,
                String bookKeyword,
                String isDel ) {
        this.bid = bid;
        this.mid = mid;
        this.bookTitle = bookTitle;
        this.bookSubTitle = bookSubTitle;
        this.bookAuthor = bookAuthor;
        this.bookTranslator = bookTranslator;
        this.bookContent = bookContent;
        this.bookIndex = bookIndex;
        this.bookPreview = bookPreview;
        this.bookPage = bookPage;
        this.bookIsbn = bookIsbn;
        this.bookPrice = bookPrice;
        this.bookSalePrice = bookSalePrice;
        this.bookSize = bookSize;
        this.bookThumb = bookThumb;
        this.bookPublisher = bookPublisher;
        this.bookPublishedDate = bookPublishedDate;
        this.bookTag = bookTag;
        this.bookKeyword = bookKeyword;
        this.isDel = isDel;
    }
}
