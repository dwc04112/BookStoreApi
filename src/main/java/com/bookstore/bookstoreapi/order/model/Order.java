package com.bookstore.bookstoreapi.order.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Table(name = "order")
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id
    @Column(name = "impUid")
    private String merUid;           //주문번호

    @Column(unique = true)
    private long mid;                //사용자 id
    private int paidAmount;          //결제 금액
    private LocalDate commentDate;   //주문날짜
    private LocalTime commentTime;
    private int bookPrice;  //책가격
    private int postage;    //배송료

}
