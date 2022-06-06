package com.bookstore.bookstoreapi.order.dto;

import lombok.Data;

@Data
public class OrderItemDTO {
    private long orderItemId;         //1. 상품번호
    private long orderId;           //2. 주문번호
    private long bid;               //3. 책 id
    private int bookCount;          //4. 책 수량
    private int bookSalePrice;      //5. 판매 가격
}
