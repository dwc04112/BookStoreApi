package com.bookstore.bookstoreapi.cart.dto;

import com.bookstore.bookstoreapi.bookjpa.model.Book;
import lombok.Data;

@Data
public class CartDTO {
    private long cartId;
    private long mid;
    private Book book;
    private String bookCount;
}
