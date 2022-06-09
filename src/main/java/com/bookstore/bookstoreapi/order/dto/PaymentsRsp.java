package com.bookstore.bookstoreapi.order.dto;

import lombok.Data;

@Data
public class PaymentsRsp {
    private String imp_uid;
    private long merchant_uid;

    private String pay_method;
    private String pg_provider;

    private int paid_at;
    private String status;
    private int amount;
    private String buyer_name;

    private String vbank_name;
    private String vbank_holder;
}
