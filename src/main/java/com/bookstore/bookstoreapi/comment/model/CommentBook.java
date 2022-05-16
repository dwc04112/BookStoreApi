package com.bookstore.bookstoreapi.comment.model;

import com.bookstore.bookstoreapi.bookjpa.model.Book;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Table(name = "comment")
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentBook {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "cid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @ManyToOne()
    @JoinColumn(name="bid",insertable = false, updatable = false)
    private Book book;

    public void updateIsDel(String isDel){
        this.isDel = isDel;
    }
}
