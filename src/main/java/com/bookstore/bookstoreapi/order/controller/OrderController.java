package com.bookstore.bookstoreapi.order.controller;


import com.bookstore.bookstoreapi.common.ApiResponse;
import com.bookstore.bookstoreapi.order.dto.SetOrderDTO;
import com.bookstore.bookstoreapi.order.model.Orders;
import com.bookstore.bookstoreapi.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(value = "/order")
public class OrderController {

    public final OrderService orderService;

    //전체 보여주기
    @GetMapping("/")
    public List<Orders> getCartList(){
        return orderService.getOrderList();
    }

    //주문 및 주문아이템 등록
    @PostMapping("/")
    public ApiResponse<Long> setCartList(@RequestBody SetOrderDTO setOrderDTO) {
        log.debug("order test : "+setOrderDTO);
        return orderService.setCartList(setOrderDTO);
    }
}
