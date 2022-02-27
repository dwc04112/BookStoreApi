package com.bookstore.bookstoreapi.bookjpa.dto;


import lombok.Data;

import java.time.LocalDate;

@Data
public class BookDTO {
    private Long bid;
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
}
