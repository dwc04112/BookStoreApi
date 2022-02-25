package com.bookstore.bookstoreapi.bookjpa.service;


import com.bookstore.bookstoreapi.bookjpa.dto.BookDTO;
import com.bookstore.bookstoreapi.bookjpa.model.Book;
import com.bookstore.bookstoreapi.bookjpa.model.BookRepository;
import com.bookstore.bookstoreapi.common.ApiResponse;
import com.bookstore.bookstoreapi.member.MemberRepository;
import com.bookstore.bookstoreapi.security.controller.MemberController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
@Slf4j
public class BookJpaService {

    final BookRepository bookRepository;
    final MemberRepository memberRepository;

    //리스트 가져오기
    public List<Book> getBookList() {
        return bookRepository.findBookByIsDel("N");
    }

    //지정한 책만 가져오기
    public Book getBookId(long bid) {
        Optional<Book> bookData = bookRepository.findBookByBidAndIsDel(bid, "N");
        return bookData.orElseThrow(() -> new RuntimeException("no data"));
    }

    //책 등록
    public ApiResponse<Book> postBook(BookDTO bookDTO) {
        long newBookBidValue = this.getNewBookBidValue(bookRepository);
        long memberId = getMemberIdByEmail(memberRepository);
        log.debug("member id : "+memberId);

        Book postData = Book.builder()
                .bid(newBookBidValue)
                .mid(memberId)
                .b_title(bookDTO.getB_title())
                .b_subTitle(bookDTO.getB_subTitle())
                .b_author(bookDTO.getB_author())
                .b_translator(bookDTO.getB_translator())
                .b_content(bookDTO.getB_content())
                .b_index(bookDTO.getB_index())
                .b_preview(bookDTO.getB_preview())
                .b_page(bookDTO.getB_page())
                .b_isbn(bookDTO.getB_isbn())
                .b_price(bookDTO.getB_price())
                .b_salePrice(bookDTO.getB_salePrice())
                .b_size(bookDTO.getB_size())
                .b_thumb(bookDTO.getB_thumb())
                .b_publisher(bookDTO.getB_publisher())
                .b_publishedDate(bookDTO.getB_publishedDate())
                .b_tag(bookDTO.getB_tag())
                .b_detailTag(bookDTO.getB_detailTag())
                .b_keyword(bookDTO.getB_keyword())
                .isDel("N")
                .build();
        bookRepository.save(postData);
        return new ApiResponse<>(true, "Registration successfully ", postData);
    }

    //책 id를 정하는 로직
    private long getNewBookBidValue(BookRepository bookRepository) {
        long result;
        Book boardOfMaxId = bookRepository.findTopByOrderByBidDesc();
        if (boardOfMaxId == null) {
            result = 1;
            log.debug("no board data, maxId is 1");
        } else {
            result = boardOfMaxId.getBid() + 1;
            log.debug("maxIdFromBoard=" + boardOfMaxId.getBid());
        }
        log.debug("newBoardIdValue=" + result);
        return result;
    }

    //SecurityContextHolder에 저장된 사용자Email을 통해 사용자 Mid를 가져오는 로직
    private Long getMemberIdByEmail(MemberRepository memberRepository) {
        String memberEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return memberRepository.getMemberIdByEmail(memberEmail);
    }

    //책 삭제 ( isDel "N" -> "Y" )
    public ApiResponse<Book> updateIsDelBookById(long bid) {
        Optional<Book> bookData = bookRepository.findBookByBidAndIsDel(bid, "N");
        Book data = bookData.orElseThrow(() -> new RuntimeException("no data"));
        //북 오너 정보와 매치 필요
        long midByLoginInfo = getMemberIdByEmail(memberRepository);

        if (data.getMid() == midByLoginInfo) {
            data.updateIsDel("Y");
            bookRepository.save(data);
            return new ApiResponse<>(true, "board id " + bid +" is successfully deleted", data);
        } else {
            return new ApiResponse<>(false, "failed to delete board id " + bid);
        }
    }




}
