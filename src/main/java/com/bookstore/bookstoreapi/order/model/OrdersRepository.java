package com.bookstore.bookstoreapi.order.model;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {

    List<Orders> findOrdersByMidAndIsDelAndOrderDateBetweenAndOrderTimeBetweenOrderByOrderDateDescOrderTimeDesc(Long mid,String isDel , LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime);

    Orders findOrdersByMidAndOrderIdAndIsDel(Long mid, Long orderId, String isDel);

    Optional<Orders> findOrdersByOrderIdAndIsDel(Long orderId, String isDel);

    List<Orders> findOrdersBy();
}
