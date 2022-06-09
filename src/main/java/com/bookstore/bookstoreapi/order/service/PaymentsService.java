package com.bookstore.bookstoreapi.order.service;


import com.bookstore.bookstoreapi.order.dto.PaymentsRsp;
import com.bookstore.bookstoreapi.order.model.Payments;
import com.bookstore.bookstoreapi.order.model.PaymentsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class PaymentsService {
    final PaymentsRepository paymentsRepository;

    public Payments setPaymentsInfo(PaymentsRsp data) {

        Payments payments = Payments.builder()
                .paymentId(data.getImp_uid())
                .orderId(data.getMerchant_uid())
                .payMethod(data.getPay_method())
                .pgProvide(data.getPg_provider())
                .paidAt(data.getPaid_at())
                .payStatus(data.getStatus())
                .payAmount(data.getAmount())
                .buyerName(data.getBuyer_name())
                .bankName(data.getVbank_name())
                .bankHorder(data.getVbank_holder())
                .build();
        return paymentsRepository.save(payments);
    }
}
