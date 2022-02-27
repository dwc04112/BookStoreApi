package com.bookstore.bookstoreapi.bookjpa.controller;


import com.bookstore.bookstoreapi.bookjpa.dto.BookDTO;
import com.bookstore.bookstoreapi.bookjpa.dto.BookMainDTO;
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

    @GetMapping("/info")
    public List<BookMainDTO> getBookList2(){
        return bookJpaService.getBookList2();
    }

    @GetMapping("/{bid}")
    public Book getBookId(@PathVariable long bid){
        return bookJpaService.getBookId(bid);
    }

    @PostMapping("/")
    public ApiResponse<Book> postBook(@RequestBody BookDTO bookDTO){
        log.debug("책 등록");
        return bookJpaService.postBook(bookDTO);
    }

    @DeleteMapping("/{bid}")
    public ApiResponse<Book> updateIsDelBookById(@PathVariable long bid){
        return bookJpaService.updateIsDelBookById(bid);
    }
}
