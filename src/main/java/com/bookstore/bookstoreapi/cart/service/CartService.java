package com.bookstore.bookstoreapi.cart.service;

import com.bookstore.bookstoreapi.bookjpa.model.Book;
import com.bookstore.bookstoreapi.cart.dto.CartInterface;
import com.bookstore.bookstoreapi.cart.model.Cart;
import com.bookstore.bookstoreapi.cart.model.CartRepository;
import com.bookstore.bookstoreapi.common.ApiResponse;
import com.bookstore.bookstoreapi.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class CartService {
    final CartRepository cartRepository;
    final MemberRepository memberRepository;

    //SecurityContextHolder에 저장된 사용자Email을 통해 사용자 Mid를 가져오는 로직
    private Long getMemberIdByEmail(MemberRepository memberRepository) {
        String memberEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return memberRepository.getMemberIdByEmail(memberEmail);
    }




    //장바구니 목록 보여주기 > mid 에 해당하는 장바구니
    public List<CartInterface> getCartList() {
        Long mid = getMemberIdByEmail(memberRepository);
        return cartRepository.getMyCart(mid, "N");
    }

    //장바구니 담기
    public ApiResponse<CartInterface> addToCart(Long bid) {
        Long mid = getMemberIdByEmail(memberRepository);
        int matchBid = cartRepository.selectByMidAndBid(mid, bid);

        if(matchBid>0){

            return new ApiResponse<>(false, "bid : " + bid +" already exist");
        }else{

            long newCartIdValue = this.getNewCartIdValue(cartRepository);

            int result = cartRepository.addToCart(newCartIdValue, mid, bid, 1, "N");
            if (result < 1) {
                return new ApiResponse<>(false, "items don't add to cart");
            } else {
                return new ApiResponse<>(true, "successfully add to cart. New Cart id : " + newCartIdValue);
            }
        }
    }
    //장바구니 id 생성
    private long getNewCartIdValue(CartRepository cartRepository) {
        long result;
        Cart cartOfMaxId = cartRepository.findTopByOrderByCartIdDesc();
        if (cartOfMaxId == null) {
            result = 1;
            log.debug("no cart data, maxId is 1");
        } else {
            result = cartOfMaxId.getCartId() + 1;
            log.debug("maxIdFromCart=" + cartOfMaxId.getCartId());
        }
        log.debug("newCartId=" + result);
        return result;
    }
}
