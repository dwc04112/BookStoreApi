package com.bookstore.bookstoreapi.payments.dto;

import com.bookstore.bookstoreapi.payments.model.AccessToken;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Data
public class GetTokenDTO {
    private int code;
    private String message;
    private AccessToken response;
}
