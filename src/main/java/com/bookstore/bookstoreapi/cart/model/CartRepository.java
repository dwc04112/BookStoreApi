package com.bookstore.bookstoreapi.cart.model;

import com.bookstore.bookstoreapi.cart.dto.CartInterface;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("SELECT " +
            "c.cartId as cartId," +
            "c.mid as mid," +
            "c.book.bid as bid, " +
            "c.book.bookTitle as bookTitle, " +
            "c.book.bookAuthor as bookAuthor, " +
            "c.book.bookPublisher as bookPublisher, " +
            "c.book.bookSalePrice as bookSalePrice, " +
            "c.book.bookThumb as bookThumb, " +
            "c.book.isDel as bookIsDel, " +
            "c.bookCount as bookCount " +
            "FROM Cart c where c.mid =?1 and c.book.isDel=?2 order by cartId desc ")
    List<CartInterface> getMyCart(Long mid, String isDel);

    @Query("select count(c.cartId) from Cart c where c.mid=?1 and c.book.bid=?2")
    Integer selectByMidAndBid(Long mid, Long bid);

    Cart findTopByOrderByCartIdDesc();


    @Transactional
    @Modifying
    @Query(value = "Insert into Cart value (:cartId,:mid,:bid,:bookCount)", nativeQuery = true)
    Integer addToCart(@Param("cartId") Long cartId, @Param("mid") Long mid, @Param("bid") Long bid, @Param("bookCount") Integer bookCount);

    //수량변경
    Optional<Cart> findCartByCartId(Long cartId);

    //리스트 삭제
    @Transactional
    @Modifying
    void deleteCartsByCartIdIn(List<Long> cartArr);
}
