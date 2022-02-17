package com.bookstore.bookstoreapi.bookjpa.model;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@ToString
@Table(name = "book")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Book {

    @Id
    @Column(name = "bid")
    private long bid;
// 1 title 2 publisher 3 size 4 thumb 5 translator 6 isbn 7 content
    @Column(unique = true)
    private long mid;
    private String title;
    private String sub_title;
    private String author;
    private String translator;
    private String content;
    private int page;
    private int isbn;
    private int price;
    private String size;
    private String thumb;
    private String publisher;
    private LocalDate published_date;
    private String tag;
    private String detail_tag;
    private String keyword;
    private String isDel;

    public void updateIsDel(String isDel){
        this.isDel = isDel;
    }

    public Book(
            Long bid,
            Long mid,
            String title ,
            String sub_title,
            String author,
            String translator,
            String content,
            int page,
            int isbn,
            int price,
            String size,
            String thumb,
            String publisher,
            LocalDate published_date,
            String tag,
            String detail_tag,
            String keyword,
            String isDel) {
        this.bid = bid;
        this.mid = mid;
        this.title = title;
        this.sub_title = sub_title;
        this.author = author;
        this.translator = translator;
        this.content = content;
        this.page = page;
        this.isbn = isbn;
        this.price = price;
        this.size = size;
        this.thumb = thumb;
        this.publisher = publisher;
        this.published_date = published_date;
        this.tag = tag;
        this.detail_tag = detail_tag;
        this.keyword = keyword;
        this.isDel = isDel;
    }

}
