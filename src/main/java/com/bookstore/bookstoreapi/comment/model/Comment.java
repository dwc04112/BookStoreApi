package com.bookstore.bookstoreapi.comment.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Table(name = "comment")
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Comment {

    @Id
    @Column(name = "cid")
    private long cid;

    @Column(unique = true)
    private long bid;
    private long mid;
    private int ratings;
    private String nickName;
    private String content;
    private int cPopularity;
    private LocalDate cDate;
    private LocalTime cTime;
    private String isDel;



    public Comment(Long cid,
                   Long bid,
                   Long mid,
                   int ratings,
                   String nickName,
                   String content,
                   int cPopularity,
                   LocalDate cDate,
                   LocalTime cTime,
                   String isDel){
        this.cid = cid;
        this.bid = bid;
        this.mid = mid;
        this.ratings = ratings;
        this.nickName = nickName;
        this.content = content;
        this.cPopularity = cPopularity;
        this.cDate = cDate;
        this.cTime = cTime;
        this.isDel = isDel;
    }
}
