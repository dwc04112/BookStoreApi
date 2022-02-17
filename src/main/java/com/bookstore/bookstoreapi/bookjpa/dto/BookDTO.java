package com.bookstore.bookstoreapi.bookjpa.dto;


import lombok.Data;

import java.time.LocalDate;

@Data
public class BookDTO {
    private Long bid;
    private long mid;
    private String title;
    private String sub_title;
    private String author;
    private String translator;
    private String content;
    private int page;
    private int isbn;
    private int price;
    private String size;
    private String thumb;
    private String publisher;
    private LocalDate published_date;
    private String tag;
    private String detail_tag;
    private String keyword;
    private String isDel;
}
