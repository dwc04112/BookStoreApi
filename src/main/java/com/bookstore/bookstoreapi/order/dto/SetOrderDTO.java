package com.bookstore.bookstoreapi.order.dto;

import com.bookstore.bookstoreapi.bookjpa.dto.BookOrderDTO;
import lombok.Data;

import java.util.List;


@Data
public class SetOrderDTO {
    private List<BookOrderDTO> bookOrder;

    private long mid;               //2. 사용자 id
    private String postcode;           //3. 우편번호
    private String addr;            //4. 주소
    private String detailAddr;      //5. 상세주소
    private String phoneNum;        //6. 핸드폰 번호
}
