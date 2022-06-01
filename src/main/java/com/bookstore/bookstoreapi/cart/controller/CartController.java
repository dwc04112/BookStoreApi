package com.bookstore.bookstoreapi.cart.controller;

import com.bookstore.bookstoreapi.cart.dto.CartByOrder;
import com.bookstore.bookstoreapi.cart.dto.CartDTO;
import com.bookstore.bookstoreapi.cart.dto.CartInterface;
import com.bookstore.bookstoreapi.cart.model.Cart;
import com.bookstore.bookstoreapi.cart.service.CartService;
import com.bookstore.bookstoreapi.common.ApiResponse;
import com.bookstore.bookstoreapi.wishlist.model.Wishlist;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(value = "/cart")
public class CartController {

    public final CartService cartService;

    //전체 보여주기
    @GetMapping("/")
    public List<CartInterface> getCartList(){
        return cartService.getCartList();
    }
    //선택한 데이터만 보여주기
    @GetMapping("/list/{cartIdArr}")
    public List<CartByOrder> getCartList(@PathVariable List<Long> cartIdArr){
        return cartService.getCartListByIdArr(cartIdArr);
    }

    //장바구니 담기 - not list
    @GetMapping("/add/{bid}")
    public ApiResponse<CartInterface> addToCart(@PathVariable Long bid){
        return cartService.addToCart(bid);
    }
    //장바구니 담기 - list (wishList id 받음)
    @GetMapping("/fromWish/{widArr}")
    public ApiResponse<List<Long>> addToCart(@PathVariable List<Long> widArr){
        return cartService.addListToCart(widArr);
    }

    @PostMapping("/count")
    public ApiResponse<Cart> updateCount(@RequestBody CartDTO cartDTO){
        return cartService.updateCount(cartDTO);
    }

    //cartId 책 삭제
    @DeleteMapping("/{cartArr}")
    public ApiResponse<Cart> deleteCartList(@PathVariable List<Long> cartArr){
        log.debug("delete data : " + cartArr);
        return cartService.deleteCartList(cartArr);
    }
}
