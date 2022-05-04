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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class WishlistService {

   final WishlistRepository wishlistRepository;
   final MemberRepository memberRepository;
   final BookRepository bookRepository;

    //로그인된 정보에서 member id 가져오기
    public long getMemberId(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return memberRepository.getMemberIdByEmail(email);
    }

    //member id와 title num 으로 데이터 가져옴
    public List<Wishlist> getWishListByTitle(int titleNum) {
        return wishlistRepository.findWishlistByMidAndTitleNumOrderByWidAsc(getMemberId(),titleNum);
    }

    //전체 위시리스트 목록 이름만 불러오기
    public List<WishListTitleInterface> getWishListTitle() {
        return wishlistRepository.getWishListTitle(getMemberId());
    }

    public List<Wishlist> getWishList() {
        log.debug("my Wish List");
        log.debug("my id : " +getMemberId() );
        return wishlistRepository.findWishlistByMid(getMemberId());
    }

    public ApiResponse<Wishlist> saveWishList(WishListDTO wishListDTO) {

        // 로그인 id의 해당 위시리스트에 해당 책이 있는지 확인
        boolean result = wishlistRepository.existsWishlistByMidAndWishlistTitleAndBid(getMemberId() ,wishListDTO.getWishlistTitle() ,wishListDTO.getBid());

        if(!result) {
            // 로그인 id에 해당 위시리스트가 있는지 확인
            Integer getTitleNum = wishlistRepository.findTitleNum(getMemberId(),wishListDTO.getWishlistTitle());
            if(getTitleNum==null){
               getTitleNum = getNewTitleNumber(wishlistRepository);
            }
            long newWishIdValue = this.getNewWishIdValue(wishlistRepository);
            //책정보 받아오기
            Book data = getWishBookData(bookRepository, wishListDTO.getBid());

            Wishlist postData = Wishlist.builder()
                    .wid(newWishIdValue)
                    .mid(getMemberId())
                    .titleNum(getTitleNum)
                    .wishlistTitle(wishListDTO.getWishlistTitle())
                    .bid(wishListDTO.getBid())
                    .bookTitle(data.getBookTitle())
                    .bookAuthor(data.getBookAuthor())
                    .bookSalePrice(data.getBookSalePrice())
                    .bookThumb(data.getBookThumb())
                    .build();
            wishlistRepository.save(postData);
            return new ApiResponse<>(true, data.getBookTitle() + " is successfully save for" + wishListDTO.getWishlistTitle(), postData);

        }else{
            return new ApiResponse<>(false, "이미 등록된 책입니다",null);
        }

    }

    private Book getWishBookData(BookRepository bookRepository, long bid) {
        return bookRepository.getWishBook(bid);
    }

    private long getNewWishIdValue(WishlistRepository wishlistRepository) {
        //새로운 Wish id를 정하는 로직
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

    private Integer getNewTitleNumber(WishlistRepository wishlistRepository){
        //새로운 TitleNum을 정하는 로직
        int result;
        Wishlist TitleOfMaxId = wishlistRepository.findTopByOrderByTitleNumDesc();
        if (TitleOfMaxId == null) {
            result = 1;
            log.debug("no Title data, maxId is 1");
        } else {
            result = TitleOfMaxId.getTitleNum() + 1;
            log.debug("maxIdFromTitleNum=" + TitleOfMaxId.getTitleNum());
        }
        log.debug("new Title Num=" + result);
        return result;
    }


    //DELETE Wid Array List
    public ApiResponse<Wishlist> deleteWishList(List<Long> widArr) {

        for (long wid : widArr) {
            //해당 Wish id 와 일치하는 책이 있는지 확인. 없으면 Exception
            Optional<Wishlist> wishData = wishlistRepository.findWishlistByWid(wid);
            Wishlist data = wishData.orElseThrow(() -> new RuntimeException("no data"));

            //해당 Wish id를 등록한 사용자가 맞는지 확인.
            boolean matchInfo = this.matchInfo(wishlistRepository, data.getMid());
            if(!matchInfo) {
                return new ApiResponse<>(false, "failed to delete board id List " + widArr);
            }
        }

        wishlistRepository.deleteWishlistsByWidIn(widArr);
        return new ApiResponse<>(true,"wid List : " + widArr +" is successfully deleted");

    }

    private boolean matchInfo(WishlistRepository wishlistRepository, long targetMid){
        long loginMid = getMemberId();
        if(loginMid!=targetMid){
            log.debug("게시글의 Id와 사용자의 ID가 일치하지 않습니다");
            return false;
        }
        return true;
    }

    //Delete Title
    public ApiResponse<Wishlist> deleteWishListTitle(int titleNum) {
        List<Long> ids = wishlistRepository.findWidList(getMemberId(),titleNum);
        log.debug("Wid list : " + ids);
        if(ids.size()==0){
            return new ApiResponse<>(false,"no data");
        }else {
            wishlistRepository.deleteWishlistsByWidIn(ids);
            return new ApiResponse<>(true, "title Number : " + titleNum + " is successfully deleted");
        }
    }
}
