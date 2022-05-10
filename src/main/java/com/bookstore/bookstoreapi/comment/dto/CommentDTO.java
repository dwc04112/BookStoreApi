package com.bookstore.bookstoreapi.comment.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class CommentDTO {

    private long cid;
    private long bid;
    private long mid;
    private int ratings;
    private String nickName;
    private String content;
    private int cPopularity;
    private LocalDate cDate;
    private LocalTime cTime;
    private String isDel;
}
