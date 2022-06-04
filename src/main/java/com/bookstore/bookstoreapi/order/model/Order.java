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
    @Column(name = "orderId")
    private String orderId;         //1. 주문번호

    @Column(unique = true)
    private long mid;               //2. 사용자 id
    private int postcode;           //3. 우편번호
    private String addr;            //4. 주소
    private String detailAddr;      //5. 상세주소
    private String phoneNum;        //6. 핸드폰 번호
    private LocalDate orderDate;    //7. 주문날짜
    private LocalTime orderTime;    //8. 주문시간
    private String orderState;      //9. 주문상태
    private int deliverCost;        //10. 배송료

}
