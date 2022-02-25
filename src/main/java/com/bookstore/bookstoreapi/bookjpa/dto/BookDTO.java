package com.bookstore.bookstoreapi.bookjpa.dto;


import lombok.Data;

import java.time.LocalDate;

@Data
public class BookDTO {
    private Long bid;
    private long mid;
    private String b_title;
    private String b_subTitle;
    private String b_author;
    private String b_translator;
    private String b_content;
    private String b_index;
    private String b_preview;
    private int b_page;
    private String b_isbn;
    private int b_price;
    private int b_salePrice;
    private String b_size;
    private String b_thumb;
    private String b_publisher;
    private LocalDate b_publishedDate;
    private String b_tag;
    private String b_detailTag;
    private String b_keyword;
    private String isDel;
}
