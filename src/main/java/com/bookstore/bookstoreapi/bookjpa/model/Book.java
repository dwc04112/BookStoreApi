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
    private String b_title;
    private String b_subTitle;
    private String b_author;
    private String b_translator;
    private String b_content;
    private String b_index;
    private String b_preview;
    private int b_page;
    private String b_isbn;
    private int b_price;
    private int b_salePrice;
    private String b_size;
    private String b_thumb;
    private String b_publisher;
    private LocalDate b_publishedDate;
    private String b_tag;
    private String b_detailTag;
    private String b_keyword;
    private String isDel;

    public void updateIsDel(String isDel){
        this.isDel = isDel;
    }

    public Book(
            Long bid,
            Long mid,
            String b_title ,
            String b_subTitle,
            String b_author,
            String b_translator,
            String b_content,
            String b_index,
            String b_preview,
            int b_page,
            String b_isbn,
            int b_price,
            int b_salePrice,
            String b_size,
            String b_thumb,
            String b_publisher,
            LocalDate b_publishedDate,
            String b_tag,
            String b_detailTag,
            String b_keyword,
            String isDel) {
        this.bid = bid;
        this.mid = mid;
        this.b_title = b_title;
        this.b_subTitle = b_subTitle;
        this.b_author = b_author;
        this.b_translator = b_translator;
        this.b_content = b_content;
        this.b_index = b_index;
        this.b_preview = b_preview;
        this.b_page = b_page;
        this.b_isbn = b_isbn;
        this.b_price = b_price;
        this.b_salePrice = b_salePrice;
        this.b_size = b_size;
        this.b_thumb = b_thumb;
        this.b_publisher = b_publisher;
        this.b_publishedDate = b_publishedDate;
        this.b_tag = b_tag;
        this.b_detailTag = b_detailTag;
        this.b_keyword = b_keyword;
        this.isDel = isDel;
    }

}
