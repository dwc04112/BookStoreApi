package com.bookstore.bookstoreapi.order.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

@NoArgsConstructor
@Data
public class BuyerInfoDTO {
    private String code;
    private String message;
    private JSONObject response = new JSONObject();
}
