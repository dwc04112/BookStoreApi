package com.bookstore.bookstoreapi.wishlist.controller;



import com.bookstore.bookstoreapi.common.ApiResponse;
import com.bookstore.bookstoreapi.wishlist.DTO.WishListDTO;
import com.bookstore.bookstoreapi.wishlist.DTO.WishListDataDTO;
import com.bookstore.bookstoreapi.wishlist.DTO.WishListTitleInterface;
import com.bookstore.bookstoreapi.wishlist.model.Wishlist;
import com.bookstore.bookstoreapi.wishlist.service.WishlistService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/wish")
public class WishlistController {

    final WishlistService wishlistService;

    @GetMapping("/title")
    public List<WishListTitleInterface> getWishListTitle(){
        return wishlistService.getWishListTitle();
    }

    @GetMapping("/")
    public List<Wishlist> getWishList(){
        return wishlistService.getWishList();
    }

    @PostMapping("/")
    public Wishlist saveWishList(@RequestBody WishListDTO wishListDTO){
        log.debug("post data : " + wishListDTO);
        return wishlistService.saveWishList(wishListDTO);
    }

    @DeleteMapping("/{wid}")
    public ApiResponse<Wishlist> deleteWishList(@PathVariable int wid){
        log.debug("delete data : " + wid);
        return wishlistService.deleteWishList(wid);
    }
}
