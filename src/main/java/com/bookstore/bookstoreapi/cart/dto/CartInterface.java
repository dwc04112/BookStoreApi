package com.bookstore.bookstoreapi.cart.dto;
import com.bookstore.bookstoreapi.bookjpa.model.Book;


public interface CartInterface {
    Long getCartId();
    Long getMid();
    Long getBid();
    String getBookTitle();
    String getBookAuthor();
    String getBookPublisher();
    Integer getBookSalePrice();
    String getBookThumb();
    String getBookIsDel();
    Integer getBookCount();
}
