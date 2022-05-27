package com.bookstore.bookstoreapi.cart.controller;

import com.bookstore.bookstoreapi.cart.dto.CartInterface;
import com.bookstore.bookstoreapi.cart.model.Cart;
import com.bookstore.bookstoreapi.cart.service.CartService;
import com.bookstore.bookstoreapi.common.ApiResponse;
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

    @GetMapping("/")
    public List<CartInterface> getCartList(){
        return cartService.getCartList();
    }

    @GetMapping("/{bid}")
    public ApiResponse<CartInterface> addToCart(@PathVariable Long bid){
        return cartService.addToCart(bid);
    }
}
