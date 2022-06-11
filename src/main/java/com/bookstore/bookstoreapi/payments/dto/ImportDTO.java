package com.bookstore.bookstoreapi.payments.dto;

import lombok.Data;

@Data
public class ImportDTO {
    private String imp_uid;
    private Long merchant_uid;

    private String refund_holder;
    private String refund_bank;
    private int refund_account;
}
