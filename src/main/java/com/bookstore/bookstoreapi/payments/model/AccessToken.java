package com.bookstore.bookstoreapi.payments.model;

import lombok.Data;

@Data
public class AccessToken {

    String access_token;
    int now;
    int expired_at;

    public String getToken() {
        return this.access_token;
    }
}
