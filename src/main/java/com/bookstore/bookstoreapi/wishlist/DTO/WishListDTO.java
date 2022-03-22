package com.bookstore.bookstoreapi.wishlist.DTO;


import lombok.Data;

@Data
public class WishListDTO {
    private long wid;
    private long mid;
    private String wishlistTitle;
    private long bid;
}
