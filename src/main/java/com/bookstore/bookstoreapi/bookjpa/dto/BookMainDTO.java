package com.bookstore.bookstoreapi.bookjpa.dto;

import lombok.Data;

@Data
public class BookMainDTO {
    private String bThumb;
    private String bTitle;
    private String bKeyword;
    private String bAuthor;


    public BookMainDTO(String bThumb,
                       String bTitle,
                       String bKeyword,
                       String bAuthor){
        this.bThumb = bThumb;
        this.bTitle = bTitle;
        this.bKeyword = bKeyword;
        this.bAuthor = bAuthor;
    }
}
