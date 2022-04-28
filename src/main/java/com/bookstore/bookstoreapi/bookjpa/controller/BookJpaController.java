package com.bookstore.bookstoreapi.bookjpa.controller;


import com.bookstore.bookstoreapi.bookjpa.dto.*;
import com.bookstore.bookstoreapi.bookjpa.model.Book;
import com.bookstore.bookstoreapi.bookjpa.service.BookJpaService;
import com.bookstore.bookstoreapi.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
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

    //about 화면
    @GetMapping("/info")
    public List<BookMainDTO> getBookList2(){
        return bookJpaService.getBookList2();
    }
    //main 화면
    @GetMapping("/main")
    public List<BookMainDetailInterface> getBookListMain(){
        return bookJpaService.getBookListMain();
    }
    //책 Detail
    @GetMapping("/{bid}")
    public Book getBookId(@PathVariable long bid){
        return bookJpaService.getBookId(bid);
    }

    // 자동완성
    @GetMapping("/complete/{searchTitle}")
    public List<BookMainInterface> getBookListByComplete(@PathVariable String searchTitle){
        log.debug(searchTitle);
        return bookJpaService.getBookListByComplete(searchTitle);
    }


    @PostMapping("/")
    public ApiResponse<Book> postBook(@RequestBody BookDTO bookDTO){

        log.debug("post data : " + bookDTO);
        log.debug("책 등록");
        return bookJpaService.postBook(bookDTO);
    }

    @DeleteMapping("/{bid}")
    public ApiResponse<Book> updateIsDelBookById(@PathVariable long bid){
        return bookJpaService.updateIsDelBookById(bid);
    }

    //책 검색
    @PostMapping("/keyword")
    public List<BookMainDTO> searchBookList(@RequestBody BookDTO bookDTO){
        String keyword = bookDTO.getBookKeyword();
        log.debug("keyword : " + keyword);
        return bookJpaService.searchBookByKeyword(keyword);
    }

    @GetMapping("/category/{bookTag}")
    public List<BookMainDTO> searchByMainTag(@PathVariable String bookTag){
        return bookJpaService.searchByMainTag(bookTag);
    }

}
