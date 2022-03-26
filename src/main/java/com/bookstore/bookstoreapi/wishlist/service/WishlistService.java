package com.bookstore.bookstoreapi.wishlist.service;

import com.bookstore.bookstoreapi.bookjpa.model.Book;
import com.bookstore.bookstoreapi.bookjpa.model.BookRepository;
import com.bookstore.bookstoreapi.common.ApiResponse;
import com.bookstore.bookstoreapi.member.MemberRepository;
import com.bookstore.bookstoreapi.wishlist.DTO.WishListDTO;
import com.bookstore.bookstoreapi.wishlist.DTO.WishListTitleInterface;
import com.bookstore.bookstoreapi.wishlist.model.Wishlist;
import com.bookstore.bookstoreapi.wishlist.model.WishlistRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class WishlistService {

   final WishlistRepository wishlistRepository;
   final MemberRepository memberRepository;
   final BookRepository bookRepository;

    public long getMemberId(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return memberRepository.getMemberIdByEmail(email);
    }


    public List<Wishlist> getWishListByTitle(int titleNum) {
        return wishlistRepository.findWishlistByMidAndTitleNumOrderByWidAsc(getMemberId(),titleNum);
    }

    public List<WishListTitleInterface> getWishListTitle() {
        return wishlistRepository.getWishListTitle(getMemberId());
    }

    public List<Wishlist> getWishList() {
        log.debug("my Wish List");
        log.debug("my id : " +getMemberId() );
        return wishlistRepository.findWishlistByMid(getMemberId());
    }



    public ApiResponse<Wishlist> saveWishList(WishListDTO wishListDTO) {
        boolean result = wishlistRepository.existsWishlistByMidAndWishlistTitleAndBid(getMemberId() ,wishListDTO.getWishlistTitle() ,wishListDTO.getBid());

        if(!result) {
            long newWishIdValue = this.getnewWishIdValue(wishlistRepository);
            Book data = getWishBookData(bookRepository, wishListDTO.getBid());

            Wishlist postData = Wishlist.builder()
                    .wid(newWishIdValue)
                    .mid(getMemberId())
                    .wishlistTitle(wishListDTO.getWishlistTitle())
                    .bid(wishListDTO.getBid())
                    .bookTitle(data.getBookTitle())
                    .bookAuthor(data.getBookAuthor())
                    .bookSalePrice(data.getBookSalePrice())
                    .bookThumb(data.getBookThumb())
                    .build();
            wishlistRepository.save(postData);
            return new ApiResponse<>(true,data.getBookTitle() +" is successfully save for" + wishListDTO.getWishlistTitle(), postData);
        }else{
            return new ApiResponse<>(false, "이미 등록된 책입니다",null);
        }
    }

    private Book getWishBookData(BookRepository bookRepository, long bid) {
        return bookRepository.getWishBook(bid);
    }

    private long getnewWishIdValue(WishlistRepository wishlistRepository) {
        long result;
        Wishlist wishOfMaxId = wishlistRepository.findTopByOrderByWidDesc();
        if (wishOfMaxId == null) {
            result = 1;
            log.debug("no board data, maxId is 1");
        } else {
            result = wishOfMaxId.getWid() + 1;
            log.debug("maxIdFromBoard=" + wishOfMaxId.getWid());
        }
        log.debug("newBoardIdValue=" + result);
        return result;
    }

    public ApiResponse<Wishlist> deleteWishList(int wid) {
        Optional<Wishlist> wishData = wishlistRepository.findWishlistByWid(wid);
        Wishlist data = wishData.orElseThrow(() -> new RuntimeException("no data"));

        boolean matchInfo= this.matchInfo(wishlistRepository, data.getMid());
        if(!matchInfo){
            return new ApiResponse<>(false, "failed to delete board id " + wid);
        }else{
            wishlistRepository.deleteWishlistByWid(wid);
            return new ApiResponse<>(true,"wid " + wid +" is successfully deleted");
        }
    }

    private boolean matchInfo(WishlistRepository wishlistRepository, long targetMid){
        long loginMid = getMemberId();
        if(loginMid!=targetMid){
            log.debug("게시글의 Id와 사용자의 ID가 일치하지 않습니다");
            return false;
        }
        return true;
    }

}
