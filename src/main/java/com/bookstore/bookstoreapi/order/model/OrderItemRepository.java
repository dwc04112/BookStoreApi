package com.bookstore.bookstoreapi.order.model;

import com.bookstore.bookstoreapi.cart.model.Cart;
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
    @Query(value = "Insert into orderItem value (:orderItemId,:orderId,:bid,:bookCount,:bookSalePrice)", nativeQuery = true)
    Integer addToOrderItem(@Param("orderItemId") Long orderItemId, @Param("orderId") Long orderId, @Param("bid") Long bid, @Param("bookCount") Integer bookCount, @Param("bookSalePrice") Integer bookSalePrice);

}