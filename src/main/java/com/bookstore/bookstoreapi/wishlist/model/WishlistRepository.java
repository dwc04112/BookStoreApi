package com.bookstore.bookstoreapi.wishlist.model;


import com.bookstore.bookstoreapi.wishlist.DTO.WishListTitleInterface;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    List<Wishlist> findWishlistByMid(long mid);

    List<Wishlist> findWishlistByMidAndTitleNumOrderByWidAsc(long mid, int tilteNum);




    // 해당 위시리스트에 책이 있는지 확인
    boolean existsWishlistByMidAndWishlistTitleAndBid(long mid, String wishlistTitle, long bid);

    Wishlist findTopByOrderByWidDesc();

    Wishlist findTopByOrderByTitleNumDesc();

    @Query("SELECT titleNum from Wishlist where mid= ?1 and wishlistTitle = ?2 group by titleNum")
    Integer findTitleNum(long mid, String wishlistTitle);





    // 해당 위시리스트 책 삭제
    Optional<Wishlist> findWishlistByWid(long wid);

    @Transactional
    @Modifying
    void deleteWishlistByWid(long wid);



    // 위시리스트 카테고리 삭제
    @Query("select wid from Wishlist where mid=?1 and titleNum=?2")
    List<Long> findWidList(Long mid, int titleNum);

    @Transactional
    @Modifying
    void deleteWishlistsByWidIn(List<Long> ids);

    // 로그인된 아이디의 위시리스트 : 카테고리 Num & 카테고리 Title & 카테고리에 등록된 책
    @Query("Select titleNum as titleNum ,wishlistTitle as wishlistTitle, count(titleNum) as countTitle from Wishlist where mid=?1 group by titleNum")
    List<WishListTitleInterface> getWishListTitle(long mid);

    //WishList -> Cart 로 이동할때 wish id로 bid 찾기
    @Query("select bid FROM Wishlist WHERE wid = ?1")
    Long getBidByWid(Long wid);
}
