package com.bookstore.bookstoreapi.payments.dto;


import com.bookstore.bookstoreapi.order.model.Orders;
import com.bookstore.bookstoreapi.payments.model.Payments;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class PayAndOrderItems {
    private Payments payments;
    private Orders orders = null;
    public void setPay(Payments payments){
        this.payments = payments;
    }
    public void setItems(Orders orders){
        this.orders = orders;
    }
}
