package com.bookstore.bookstoreapi.bookjpa.controller;


import com.bookstore.bookstoreapi.bookjpa.dto.*;
import com.bookstore.bookstoreapi.bookjpa.model.Book;
import com.bookstore.bookstoreapi.bookjpa.service.BookJpaService;
import com.bookstore.bookstoreapi.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


import java.util.List;



@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(value = "/book")
public class BookJpaController {

    public final BookJpaService bookJpaService;

    @GetMapping("/")
    public List<Book> getBookList(){
        return bookJpaService.getBookList();
    }

    //1-1 about 화면
    @GetMapping("/info")
    public List<BookMainDTO> getBookList2(){
        return bookJpaService.getBookList2();
    }
    //1-2 main 화면
    @GetMapping("/main")
    public List<BookMainDetailInterface> getBookListMain(){
        return bookJpaService.getBookListMain();
    }

    //2-1 책 상세화면
    @GetMapping("/{bid}")
    public Book getBookId(@PathVariable long bid){
        return bookJpaService.getBookId(bid);
    }

    //2-2 책 상세화면 (info 인터페이스 사용)
    @GetMapping("/info/{bid}")
    public BookMainDTO getBookIdMain(@PathVariable long bid){
        return bookJpaService.getBookInfoId(bid);
    }

    //2-3 order.vue 에서 호출
    @GetMapping("/order/{bidArr}")
    public List<BookMainDTO> getBookByIdArr(@PathVariable List<Long> bidArr){
        return bookJpaService.getBookByIdArr(bidArr);
    }

    //책 등록
    @PostMapping("/")
    public ApiResponse<Book> postBook(@RequestBody BookDTO bookDTO){

        log.debug("post data : " + bookDTO);
        log.debug("책 등록");
        return bookJpaService.postBook(bookDTO);
    }

    //책 삭제
    @DeleteMapping("/{bid}")
    public ApiResponse<Book> updateIsDelBookById(@PathVariable long bid){
        return bookJpaService.updateIsDelBookById(bid);
    }


    /*
     * 여기서부터 책 검색
     */
    //메인 책 검색 ( 타이틀 || 키워드 )
    @GetMapping("/search/{searchData}")
    public List<BookMainInterface> searchBook(@PathVariable String searchData){
        log.debug("title? : "  + searchData);
        return bookJpaService.searchBook(searchData);
    }

    // 자동완성
    @GetMapping("/complete/{searchTitle}")
    public List<BookMainInterface> getBookListByComplete(@PathVariable String searchTitle){
        log.debug(searchTitle);
        return bookJpaService.getBookListByComplete(searchTitle);
    }

    //키워드 클릭
    @GetMapping("/keyword/{selectKeyword}")
    public List<BookMainInterface> searchBookByKeyword(@PathVariable String selectKeyword){
        log.debug("keyword : " + selectKeyword);
        return bookJpaService.searchBookByKeyword(selectKeyword);
    }

    @GetMapping("/category/{bookTag}")
    public List<BookMainDTO> searchByMainTag(@PathVariable String bookTag){
        return bookJpaService.searchByMainTag(bookTag);
    }

}
