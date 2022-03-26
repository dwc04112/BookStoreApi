package com.bookstore.bookstoreapi.wishlist.model;




import lombok.*;
import org.hibernate.annotations.DynamicInsert;


import javax.persistence.*;


@Entity
@Getter
@ToString
@Table(name = "wishlist")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@DynamicInsert
public class Wishlist {

/*
    @Id
    @ManyToOne
    @JoinColumn(name="mid",nullable = false)
    private Member member;

    @Id
    @ManyToOne
    @JoinColumn(name="bid",nullable = false)
    private Book book;



    @Column(unique = true)
    private String wishlistTitle;

    public Wishlist(Member member,
                    Book book,
                    String wishlistTitle){
        this.member = member;
        this.book = book;
        this.wishlistTitle = wishlistTitle;
    }


    //@ManyToOne의 기본 FetchType은 EAGER
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="book_bid",nullable = false)
    private Book book;
 */

    @Id
    @Column(name="wid")
    private long wid;



    @Column(unique = true)
    private long mid;
    private int titleNum;

    @Column(columnDefinition = "varchar(200) default '나의 보관함'")
    private String wishlistTitle;

    @Column(unique = true)
    private long bid;
    private String bookTitle;
    private String bookAuthor;
    private int bookSalePrice;
    private String bookThumb;

    public Wishlist(long wid,
                    long mid,
                    int titleNum,
                    String wishlistTitle,
                    long bid,
                    String bookTitle,
                    String bookAuthor,
                    int bookSalePrice,
                    String bookThumb
                    ){
        this.wid = wid;
        this.mid = mid;
        this.titleNum = titleNum;
        this.wishlistTitle = wishlistTitle;
        this.bid = bid;
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.bookSalePrice = bookSalePrice;
        this.bookThumb = bookThumb;
    }
}
