package com.bookstore.bookstoreapi.bookjpa.dto;


import lombok.Data;

@Data
public class BookOrderDTO {
    private Long bid;
    private int bookCount;
}
