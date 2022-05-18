package com.bookstore.bookstoreapi.comment.dto;

import lombok.Data;

@Data
public class SortDTO {
    private long bid;
    private int sortType;
    private int page;
    private int size;
}
