package com.bookstore.bookstoreapi.order.controller;


import com.bookstore.bookstoreapi.order.dto.SetOrderDTO;
import com.bookstore.bookstoreapi.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(value = "/order")
public class OrderController {

    public final OrderService orderService;

    //전체 보여주기
    @PostMapping("/")
    public Long setCartList(@RequestBody SetOrderDTO setOrderDTO){
        log.debug("order test : "+setOrderDTO);
        orderService.setCartList(setOrderDTO);
        return null;
    }
}
