package com.bookstore.bookstoreapi.cart.dto;

public interface CartByOrder {
    Long getCartId();
    Long getBid();
    String getBookTitle();
    String getBookAuthor();
    String getBookPublisher();
    Integer getBookSalePrice();
    String getBookThumb();
    Integer getBookCount();
}
