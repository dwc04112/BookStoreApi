package com.bookstore.bookstoreapi.bookjpa.service;


import com.bookstore.bookstoreapi.bookjpa.dto.*;
import com.bookstore.bookstoreapi.bookjpa.model.Book;
import com.bookstore.bookstoreapi.bookjpa.model.BookRepository;
import com.bookstore.bookstoreapi.common.ApiResponse;
import com.bookstore.bookstoreapi.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RequiredArgsConstructor
@Slf4j
@Service
public class BookJpaService {

    final BookRepository bookRepository;
    final MemberRepository memberRepository;

    // 리스트 가져오기
    public List<Book> getBookList() {
        return bookRepository.findBookByIsDel("N");
    }

    //1-1 about 화면
    public List<BookMainDTO> getBookList2() {
        return bookRepository.findBookBy();
    }
    //1-2 main 화면
    public List<BookMainDetailInterface> getBookListMain(){
        return bookRepository.getMainBook();
    }

    //2-1 책 상세화면
    public Book getBookId(long bid) {
        Optional<Book> bookData = bookRepository.findBookByBidAndIsDel(bid, "N");
        return bookData.orElseThrow(() -> new RuntimeException("no data"));
    }
    //2-2 책 상세화면 (info 인터페이스 사용)
    public BookMainDTO getBookInfoId(long bid) {
        return bookRepository.getBookByBidAndIsDel(bid, "N");
    }
    //2-3 order.vue 에서 호출
    public List<BookMainDTO> getBookByIdArr(List<Long> bidArr) {
        return bookRepository.getBookByBidInAndIsDel(bidArr, "N");
    }

    //책 등록
    public ApiResponse<Book> postBook(BookDTO bookDTO) {
        long newBookBidValue = this.getNewBookBidValue(bookRepository);
        long memberId = getMemberIdByEmail(memberRepository);
        log.debug("member id : "+memberId);

        Book postData = Book.builder()
                .bid(newBookBidValue)
                .mid(memberId)
                .bookTitle(bookDTO.getBookTitle())
                .bookSubTitle(bookDTO.getBookSubTitle())
                .bookAuthor(bookDTO.getBookAuthor())
                .bookTranslator(bookDTO.getBookTranslator())
                .bookContent(bookDTO.getBookContent())
                .bookIndex(bookDTO.getBookIndex())
                .bookPreview(bookDTO.getBookPreview())
                .bookPage(bookDTO.getBookPage())
                .bookIsbn(bookDTO.getBookIsbn())
                .bookPrice(bookDTO.getBookPrice())
                .bookSalePrice(bookDTO.getBookSalePrice())
                .bookSize(bookDTO.getBookSize())
                .bookThumb(bookDTO.getBookThumb())
                .bookPublisher(bookDTO.getBookPublisher())
                .bookPublishedDate(bookDTO.getBookPublishedDate())
                .bookTag(bookDTO.getBookTag())
                .bookKeyword(bookDTO.getBookKeyword())
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

    //여기서부터 책 검색
    public List<BookMainInterface> searchBook(String searchData) {
        searchData = searchData.replace('+','|');
        //타이틀로 검색
        return bookRepository.searchByTitleAndKeyword(searchData,"N");

        /*
        List<BookMainInterface> searchByTitle = bookRepository.searchByRegExp( searchData , "N") ;
        //키워드로 검색
        List<BookMainInterface> searchByKeyword = bookRepository.searchByRegExpKeyword( searchData , "N");




        Map<String , List<BookMainInterface> > searchMap = new HashMap<String, List<BookMainInterface>>();
        searchMap.put("title",searchByTitle);
        searchMap.put("keyword",searchByKeyword);

        return searchMap;

         */
    }

    //자동완성 : 입력한 값 like 책 가져오기
    public List<BookMainInterface> getBookListByComplete(String searchTitle) {
        searchTitle = searchTitle.replace('+','|');
        return bookRepository.searchByRegExp(searchTitle,"N");
    }

    public List<BookMainInterface> searchBookByKeyword(String selectKeyword) {
        return bookRepository.searchByRegExpKeyword(selectKeyword,"N");
    }

    public List<BookMainDTO> searchByMainTag(String bookTag) {
        return bookRepository.findBookByBookTagStartingWithAndIsDel(bookTag, "N");
    }
}
