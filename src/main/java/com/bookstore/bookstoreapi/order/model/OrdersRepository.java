package com.bookstore.bookstoreapi.order.model;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {

    List<Orders> findOrdersByMid(Long mid);

    Orders findOrdersByMidAndOrderId(Long mid, Long orderId);

    Optional<Orders> findOrdersByOrderId(Long orderId);
}
