package com.bookstore.bookstoreapi.wishlist.model;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    List<Wishlist> findWishlistByMid(long mid);

    Wishlist findTopByOrderByWidDesc();

    Optional<Wishlist> findWishlistByWid(long wid);

    @Transactional
    void deleteWishlistByWid(long wid);
}
