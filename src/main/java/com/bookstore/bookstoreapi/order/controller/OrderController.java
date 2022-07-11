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

    // 로그인한 mid 의 전체 주문 보여주기
    @GetMapping("/")
    public List<Orders> getCartList() throws Exception{
        return orderService.getOrderList();
    }

    @GetMapping("/range")
    public List<Orders> getCartListByDate(@RequestParam(value="fromDate") String fromDate, @RequestParam(value="toDate") String toDate) throws Exception{
        return orderService.getCartListByDate(toDate, fromDate);
    }

    //주문번호 생성 및 주문아이템 등록
    @PostMapping("/")
    public ApiResponse<Long> setCartList(@RequestBody SetOrderDTO setOrderDTO) throws Exception {
        log.debug("order controller : "+setOrderDTO);
        return orderService.setCartList(setOrderDTO);
    }

    //결제 중단
    @GetMapping("/stop/{orderId}")
    public ApiResponse<Long> stopCartList(@PathVariable Long orderId) throws Exception {
        log.debug("order controller : "+orderId);
        return orderService.stopPayments(orderId);
    }
}
