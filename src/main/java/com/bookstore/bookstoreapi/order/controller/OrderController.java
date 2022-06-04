package com.bookstore.bookstoreapi.order.controller;


import com.bookstore.bookstoreapi.order.dto.SetOrderDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(value = "/order")
public class OrderController {

    //전체 보여주기
    @PostMapping("/")
    public void getCartList(@RequestBody SetOrderDTO setOrderDTO){
        log.debug("order test : "+setOrderDTO);
    }
}
