package com.bookstore.bookstoreapi.wishlist.DTO;

import com.bookstore.bookstoreapi.bookjpa.model.Book;
import com.bookstore.bookstoreapi.wishlist.model.Wishlist;
import lombok.Data;

@Data
public class WishListDataDTO {
    private long wid;
    private long mid;
    private String wishlistTitle;
    private long bid;
    private String bookTitle;
    private String bookAuthor;
    private int bookSalePrice;
    private String bookThumb;

    public WishListDataDTO(Wishlist wishlist){
        wid = wishlist.getWid();
        mid = wishlist.getMid();
        wishlistTitle = wishlist.getWishlistTitle();
        bid = wishlist.getBid();
        bookTitle = wishlist.getBookTitle();
        bookAuthor = wishlist.getBookAuthor();
        bookSalePrice = wishlist.getBookSalePrice();
        bookThumb = wishlist.getBookThumb();

    }
}
