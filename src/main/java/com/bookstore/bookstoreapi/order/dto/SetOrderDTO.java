package com.bookstore.bookstoreapi.order.dto;

import lombok.Data;

import java.util.List;


@Data
public class SetOrderDTO {
    private long bid;
    private long bookCount;
    private List<Long> cartArr;

    private long mid;               //2. 사용자 id
    private int postcode;           //3. 우편번호
    private String addr;            //4. 주소
    private String detailAddr;      //5. 상세주소
    private String phoneNum;        //6. 핸드폰 번호
}
