package com.bookstore.bookstoreapi.payments.controller;

import com.bookstore.bookstoreapi.common.ApiResponse;
import com.bookstore.bookstoreapi.payments.dto.ImportDTO;
import com.bookstore.bookstoreapi.payments.dto.PayAndOrderItems;
import com.bookstore.bookstoreapi.payments.model.Payments;
import com.bookstore.bookstoreapi.payments.service.PaymentsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(value = "/payments")
public class PaymentsController {
    private final PaymentsService paymentsService;

    @ResponseBody
    @PostMapping("/complete")
    public ApiResponse<String> getOrderInfo(@RequestBody ImportDTO importDTO)throws Exception{
        return paymentsService.getOrderInfo(importDTO);
    }


    //환불관련
    @ResponseBody
    @PostMapping("/cancel")
    public ApiResponse<String> cancelPay(@RequestBody ImportDTO importDTO)throws Exception{
        log.debug("cancel Payments : " + importDTO);
        return paymentsService.cancelPay(importDTO);
    }

    @GetMapping("/{orderId}")
    public ApiResponse<PayAndOrderItems> getPaymentsByOrderId(@PathVariable Long orderId) throws Exception{
        return paymentsService.getPaymentById(orderId);
    }

}