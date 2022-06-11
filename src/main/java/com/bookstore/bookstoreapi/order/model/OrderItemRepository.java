package com.bookstore.bookstoreapi.order.model;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;



@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    OrderItem findTopByOrderByOrderItemIdDesc();

    @Transactional
    @Modifying
    @Query(value = "Insert into orderItem value (:orderItemId,:orderId,:bid,:bookCount,:bookSalePrice, :bookTitle, :bookThumb)", nativeQuery = true)
    Integer addToOrderItem(
            @Param("orderItemId") Long orderItemId,
            @Param("orderId") Long orderId,
            @Param("bid") Long bid,
            @Param("bookCount") Integer bookCount,
            @Param("bookSalePrice") Integer bookSalePrice,
            @Param("bookTitle") String bookTitle,
            @Param("bookThumb") String bookThumb
    );

    @Query("select sum (bookSalePrice*bookCount) from OrderItem where orders.orderId = ?1")
    Integer totalAmount(Long orderId);
}