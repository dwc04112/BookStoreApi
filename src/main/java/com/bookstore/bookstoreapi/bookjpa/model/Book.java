package com.bookstore.bookstoreapi.bookjpa.model;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
@Table(name = "book")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Book {

    @Id
    @Column(name = "bid")
    private long bid;

    @Column(unique = true)
    private String author;
    private String subject;
    private int price;
    private LocalDate published_date;
    private String tag;
    private String detail_tag;
    private String keyword;
    private String isDel;

    public Book(Long bid, String author, String subject, int price, LocalDate published_date, String tag, String detail_tag, String keyword, String isDel) {
        this.bid = bid;
        this.author = author;
        this.subject = subject;
        this.price = price;
        this.published_date = published_date;
        this.tag = tag;
        this.detail_tag = detail_tag;
        this.keyword = keyword;
        this.isDel = isDel;
    }

}
