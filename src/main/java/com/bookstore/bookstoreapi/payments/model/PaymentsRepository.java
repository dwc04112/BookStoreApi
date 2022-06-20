package com.bookstore.bookstoreapi.payments.model;

import com.bookstore.bookstoreapi.payments.dto.PayAndOrderItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentsRepository extends JpaRepository<Payments, String> {

    Optional<Payments> findPaymentsByOrderId(Long orderId);

    Payments getPaymentsByOrderId(Long orderId);
}
