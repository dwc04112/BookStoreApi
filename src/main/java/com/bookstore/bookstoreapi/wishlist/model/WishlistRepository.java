package com.bookstore.bookstoreapi.wishlist.model;


import com.bookstore.bookstoreapi.wishlist.DTO.WishListTitleInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    List<Wishlist> findWishlistByMid(long mid);

    Wishlist findTopByOrderByWidDesc();

    boolean existsWishlistByMidAndWishlistTitleAndBid(long mid, String wishlistTitle, long bid);

    Optional<Wishlist> findWishlistByWid(long wid);

    @Transactional
    void deleteWishlistByWid(long wid);

    @Query("Select wishlistTitle as wishlistTitle, count(wishlistTitle) as countTitle from Wishlist where mid=?1 group by wishlistTitle")
    List<WishListTitleInterface> getWishListTitle(long mid);
}
