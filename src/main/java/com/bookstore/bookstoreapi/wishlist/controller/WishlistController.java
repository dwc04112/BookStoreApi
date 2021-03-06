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


    //전체 위시리스트 목록 이름만 불러오기
    @GetMapping("/title")
    public List<WishListTitleInterface> getWishListTitle(){
        return wishlistService.getWishListTitle();
    }

    //해당 위시리스트 목록 삭제하기
    @DeleteMapping("/title/{titleNum}")
    public ApiResponse<Wishlist> deleteWishListByTitle(@PathVariable int titleNum){
        log.debug("delete data title : " + titleNum);
        return wishlistService.deleteWishListTitle(titleNum);
    }


    //해당 위시리스트 목록 불러오기
    @GetMapping("/title/{titleNum}")
    public List<Wishlist> getWishListByTitle(@PathVariable int titleNum){
        return wishlistService.getWishListByTitle(titleNum);
    }

    //내 위시리스트 전부 출력
    @GetMapping("/")
    public List<Wishlist> getWishList(){
        return wishlistService.getWishList();
    }

    //위시리스트에 책 추가
    @PostMapping("/")
    public ApiResponse<Wishlist> saveWishList(@RequestBody WishListDTO wishListDTO){
        log.debug("post data : " + wishListDTO);
        return wishlistService.saveWishList(wishListDTO);
    }

    //위시리스트속 책 삭제
    @DeleteMapping("/{widArr}")
    public ApiResponse<Wishlist> deleteWishList(@PathVariable List<Long> widArr){
        log.debug("delete data : " + widArr);
        return wishlistService.deleteWishList(widArr);
    }
}
