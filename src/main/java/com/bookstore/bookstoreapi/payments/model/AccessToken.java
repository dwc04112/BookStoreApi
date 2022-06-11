package com.bookstore.bookstoreapi.payments.model;

import lombok.Data;

@Data
public class AccessToken {

    String token;
    int expired_at;
    int now;

    public String getToken() {
        return this.token;
    }
}
