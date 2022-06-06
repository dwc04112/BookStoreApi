package com.bookstore.bookstoreapi.cart.service;

import com.bookstore.bookstoreapi.cart.dto.CartDTO;
import com.bookstore.bookstoreapi.cart.dto.CartInterface;
import com.bookstore.bookstoreapi.cart.model.Cart;
import com.bookstore.bookstoreapi.cart.model.CartRepository;
import com.bookstore.bookstoreapi.common.ApiResponse;
import com.bookstore.bookstoreapi.member.MemberRepository;
import com.bookstore.bookstoreapi.wishlist.model.WishlistRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class CartService {
    final CartRepository cartRepository;
    final MemberRepository memberRepository;
    final WishlistRepository wishlistRepository;

    //SecurityContextHolder에 저장된 사용자Email을 통해 사용자 Mid를 가져오는 로직
    private Long getMemberIdByEmail() {
        String memberEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return memberRepository.getMemberIdByEmail(memberEmail);
    }




    //장바구니 목록 보여주기 > mid 에 해당하는 장바구니
    public List<CartInterface> getCartList() {
        Long mid = getMemberIdByEmail();
        //isDel = book.isDel
        return cartRepository.getMyCart(mid, "N");
    }


    //장바구니 담기 - not list
    public ApiResponse<CartInterface> addToCart(Long bid) {
        Long mid = getMemberIdByEmail();
        int matchBid = cartRepository.selectByMidAndBid(mid, bid);
        if(matchBid>0){
            return new ApiResponse<>(false, "bid : " + bid +" already exist");
        }else{
            long newCartIdValue = this.getNewCartIdValue(cartRepository);
            int result = cartRepository.addToCart(newCartIdValue, mid, bid, 1);
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

    //장바구니 담기 - list
    public ApiResponse<List<Long>> addListToCart(List<Long> widArr) {
        Long mid = getMemberIdByEmail();
        ArrayList<Long> successfulBid = new ArrayList<>();
        ArrayList<Long> failedBid = new ArrayList<>();

        for(long wid : widArr){
            long bid = wishlistRepository.getBidByWid(wid);
            int matchBid = cartRepository.selectByMidAndBid(mid, bid);
            if(matchBid>0){
                successfulBid.add(bid);
            }else{
                long newCartIdValue = this.getNewCartIdValue(cartRepository);
                int result = cartRepository.addToCart(newCartIdValue, mid, bid, 1);
                if (result < 1) {
                    failedBid.add(bid);
                }else {
                    successfulBid.add(bid);
                }
            }
        }
        boolean result = failedBid.size() == 0;
        return new ApiResponse<List<Long>>(result,
                "장바구니에 추가된 Bid : " + successfulBid+ " ,장바구니 추가에 실패한 Bid : "+ failedBid, successfulBid);
    }


    public ApiResponse<Cart> updateCount(CartDTO cartDTO) {
        Optional<Cart> cartData = cartRepository.findCartByCartId(cartDTO.getCartId());
        Cart data = cartData.orElseThrow(() -> new RuntimeException("no data"));
        //로그인한 mid 가져오기
        Long mid = getMemberIdByEmail();
        //비교
        if(data.getMid() == mid){
            int setCount = Integer.parseInt(cartDTO.getBookCount());
            data.updateCount(setCount);
            cartRepository.save(data);
            return new ApiResponse<>(true, "successfully update", data);
        }else{
            return new ApiResponse<>(false,"사용자 정보기 일치하지 않습니다");
        }
    }

    public ApiResponse<Cart> deleteCartList(List<Long> cartArr) {
        for(long cartId : cartArr){
            Optional<Cart> cartData = cartRepository.findCartByCartId(cartId);
            Cart data = cartData.orElseThrow(() -> new RuntimeException("no data"));

            //해당 Wish id를 등록한 사용자가 맞는지 확인.
            boolean matchInfo = this.matchInfo(data.getMid());
            if(!matchInfo) {
                return new ApiResponse<>(false, "failed to delete cart id List " + cartArr);
            }
        }
        cartRepository.deleteCartsByCartIdIn(cartArr);
        return new ApiResponse<>(true,"cart List : " + cartArr +" is successfully deleted");
    }
    // mid 비교
    private boolean matchInfo( long targetMid){
        long loginMid = getMemberIdByEmail();
        if(loginMid!=targetMid){
            log.debug("cart Id와 사용자의 ID가 일치하지 않습니다");
            return false;
        }
        return true;
    }

}
