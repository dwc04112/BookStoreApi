package com.bookstore.bookstoreapi.wishlist.service;

import com.bookstore.bookstoreapi.bookjpa.model.Book;
import com.bookstore.bookstoreapi.bookjpa.model.BookRepository;
import com.bookstore.bookstoreapi.wishlist.DTO.WishListDTO;
import com.bookstore.bookstoreapi.wishlist.model.Wishlist;
import com.bookstore.bookstoreapi.wishlist.model.WishlistRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class WishlistService {

   final WishlistRepository wishlistRepository;
   final BookRepository bookRepository;

    public List<Wishlist> getWishList() {
        log.debug("data : " + wishlistRepository.findAll());
        return wishlistRepository.findWishlistBy();
    }



    public Wishlist saveWishList(WishListDTO wishListDTO) {
        long newWishIdValue = this.getnewWishIdValue(wishlistRepository);
        Book data = getWishBookData(bookRepository, wishListDTO.getBid());

        Wishlist postData = Wishlist.builder()
                .wid(newWishIdValue)
                .mid(wishListDTO.getMid())
                .wishlistTitle(wishListDTO.getWishlistTitle())
                .bid(wishListDTO.getBid())
                .bookTitle(data.getBookTitle())
                .bookAuthor(data.getBookAuthor())
                .bookSalePrice(data.getBookSalePrice())
                .bookThumb(data.getBookThumb())
                .build();
        wishlistRepository.save(postData);
        return postData;

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
}
