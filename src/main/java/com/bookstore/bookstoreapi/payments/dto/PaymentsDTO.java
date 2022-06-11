package com.bookstore.bookstoreapi.payments.dto;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@Data
public class PaymentsDTO {
    private int code;
    private String message;
    private PaymentsRsp response;
}
