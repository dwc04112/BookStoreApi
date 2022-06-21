package com.bookstore.bookstoreapi.comment.dto;

import lombok.Data;

import java.util.List;

@Data
public class Total {
    private int totalCount;
    private String ratings;
    private List<RatingsCount> ratingsCount;
}
