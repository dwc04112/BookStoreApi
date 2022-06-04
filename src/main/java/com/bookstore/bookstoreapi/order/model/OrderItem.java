package com.bookstore.bookstoreapi.order.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Getter
@Table(name = "orderItem")
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id
    @Column(name = "orderItemId")
    private String orderItemId;     //1. 상품번호

    @Column(unique = true)
    private long orderId;           //2. 주문번호
    private long bid;               //3. 책 id
    private int bookCount;          //4. 책 수량
    private int bookSalePrice;      //5. 판매 가격
}
