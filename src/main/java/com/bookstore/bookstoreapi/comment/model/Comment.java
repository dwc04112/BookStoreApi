package com.bookstore.bookstoreapi.comment.model;

import com.bookstore.bookstoreapi.bookjpa.model.Book;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Getter
@Table(name = "comment")
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Comment implements Serializable {



    @Id
    @Column(name = "cid")
    private long cid;

    @Column(unique = true)
    private long bid;
    private long mid;
    private int ratings;
    private String nickName;
    private String content;
    private int popularity;
    private LocalDate commentDate;
    private LocalTime commentTime;
    private String isDel;



    public void updateIsDel(String isDel){
        this.isDel = isDel;
    }

    public Comment(Long cid,
                   Long bid,
                   Long mid,
                   int ratings,
                   String nickName,
                   String content,
                   int popularity,
                   LocalDate commentDate,
                   LocalTime commentTime,
                   String isDel)
                   {
        this.cid = cid;
        this.bid = bid;
        this.mid = mid;
        this.ratings = ratings;
        this.nickName = nickName;
        this.content = content;
        this.popularity = popularity;
        this.commentDate = commentDate;
        this.commentTime = commentTime;
        this.isDel = isDel;
    }
}
