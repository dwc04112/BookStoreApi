package com.bookstore.bookstoreapi.bookjpa.dto;


import lombok.Data;

import java.time.LocalDate;

@Data
public class BookDTO {
    private Long bid;
    private Long mid;
    private String author;
    private String subject;
    private int page;
    private int price;
    private LocalDate published_date;
    private String tag;
    private String detail_tag;
    private String keyword;
    private String isDel;
}
