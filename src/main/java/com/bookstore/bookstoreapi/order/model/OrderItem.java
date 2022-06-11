package com.bookstore.bookstoreapi.order.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Getter
@Table(name = "orderItem")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem implements Serializable {

    @Id
    @Column(name = "orderItemId")
    private long orderItemId;     //1. 상품번호

    //1대N 양방향
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "orderId", insertable = false, updatable = false)
    @JsonIgnore
    private Orders orders;           //2. 주문번호

    @Column(unique = true)
    private long bid;               //3. 책 id
    private int bookCount;          //4. 책 수량
    private int bookSalePrice;      //5. 판매 가격
    private String bookTitle;
    private String bookThumb;

    public OrderItem(Long orderItemId,
                      Orders orders,
                      Long bid,
                      int bookCount,
                      int bookSalePrice,
                      String bookTitle,
                      String bookThumb)
    {
        this.orderItemId = orderItemId;
        this.orders = orders;
        this.bid = bid;
        this.bookCount = bookCount;
        this.bookSalePrice = bookSalePrice;
        this.bookTitle = bookTitle;
        this.bookThumb = bookThumb;
    }
}
