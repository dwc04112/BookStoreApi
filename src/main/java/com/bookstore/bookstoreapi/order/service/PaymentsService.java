package com.bookstore.bookstoreapi.order.service;


import com.bookstore.bookstoreapi.order.model.PaymentsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class PaymentsService {
    final PaymentsRepository paymentsRepository;

    public void setPaymentsInfo(JSONObject data) {
        log.debug("pay service : " + data);
    }
}
