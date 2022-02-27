package com.bookstore.bookstoreapi.bookjpa.model;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Entity
@Table(name = "book")
public class Book {

    @Id
    @Column(name = "bid")
    private long bid;

    @Column(unique = true)
    private long mid;
    private String bTitle;
    private String bSubTitle;
    private String bAuthor;
    private String bTranslator;
    private String bContent;
    private String bIndex;
    private String bPreview;
    private int bPage;
    private String bIsbn;
    private int bPrice;
    private int bSalePrice;
    private String bSize;
    private String bThumb;
    private String bPublisher;
    private LocalDate bPublishedDate;
    private String bTag;
    private String bDetailTag;
    private String bKeyword;
    private String isDel;

    public void updateIsDel(String isDel){
        this.isDel = isDel;
    }

    public Book(Long bid,
                Long mid,
                String bTitle,
                String bSubTitle,
                String bAuthor,
                String bTranslator,
                String bContent,
                String bIndex,
                String bPreview,
                int bPage,
                String bIsbn,
                int bPrice,
                int bSalePrice,
                String bSize,
                String bThumb,
                String bPublisher,
                LocalDate bPublishedDate,
                String bTag,
                String bDetailTag,
                String bKeyword,
                String isDel ) {
        this.bid = bid;
        this.mid = mid;
        this.bTitle = bTitle;
        this.bSubTitle = bSubTitle;
        this.bAuthor = bAuthor;
        this.bTranslator = bTranslator;
        this.bContent = bContent;
        this.bIndex = bIndex;
        this.bPreview = bPreview;
        this.bPage = bPage;
        this.bIsbn = bIsbn;
        this.bPrice = bPrice;
        this.bSalePrice = bSalePrice;
        this.bSize = bSize;
        this.bThumb = bThumb;
        this.bPublisher = bPublisher;
        this.bPublishedDate = bPublishedDate;
        this.bTag = bTag;
        this.bDetailTag = bDetailTag;
        this.bKeyword = bKeyword;
        this.isDel = isDel;
    }
}
