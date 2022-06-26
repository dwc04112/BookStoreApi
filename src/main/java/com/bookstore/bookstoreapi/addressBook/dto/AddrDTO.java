package com.bookstore.bookstoreapi.addressBook.dto;

import lombok.Data;

@Data
public class AddrDTO {
    private long addrId;
    private long mid;
    private String postcode;        // 우편번호
    private String addr;            // 주소
    private String detailAddr;      // 상세주소
}
