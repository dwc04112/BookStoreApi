package com.bookstore.bookstoreapi.wishlist.DTO;


import lombok.Data;

@Data
public class WishListDTO {
    private int titleNum;
    private String wishlistTitle;
    private long bid;
}
