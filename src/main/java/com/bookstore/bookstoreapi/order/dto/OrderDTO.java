package com.bookstore.bookstoreapi.order.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class OrderDTO {
    private String orderId;         //1. 주문번호
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
