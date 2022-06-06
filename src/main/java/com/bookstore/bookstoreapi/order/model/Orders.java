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
@Table(name = "orders")
@ToString
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Orders {

    @Id
    @Column(name = "orderId")
    private long orderId;         //1. 주문번호

    @Column(unique = true)
    private long mid;               //2. 사용자 id
    private String postcode;           //3. 우편번호
    private String addr;            //4. 주소
    private String detailAddr;      //5. 상세주소
    private String phoneNum;        //6. 핸드폰 번호
    private LocalDate orderDate;    //7. 주문날짜
    private LocalTime orderTime;    //8. 주문시간
    private String orderState;      //9. 주문상태
    private int deliverCost;        //10. 배송료

    public Orders(Long orderId,
                 Long mid,
                 String postcode,
                 String addr,
                 String detailAddr,
                 String phoneNum,
                 LocalDate orderDate,
                 LocalTime orderTime,
                 String orderState,
                 int deliverCost)
    {
        this.orderId = orderId;
        this.mid = mid;
        this.postcode = postcode;
        this.addr = addr;
        this.detailAddr = detailAddr;
        this.phoneNum = phoneNum;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.orderState = orderState;
        this.deliverCost = deliverCost;
    }
}
