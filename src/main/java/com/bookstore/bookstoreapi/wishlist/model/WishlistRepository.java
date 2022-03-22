package com.bookstore.bookstoreapi.wishlist.model;


import com.bookstore.bookstoreapi.wishlist.DTO.WishListDataDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    List<Wishlist> findWishlistBy();


    Wishlist findTopByOrderByWidDesc();


}
