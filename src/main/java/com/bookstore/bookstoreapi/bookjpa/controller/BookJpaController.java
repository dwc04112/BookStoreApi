package com.bookstore.bookstoreapi.bookjpa.controller;


import com.bookstore.bookstoreapi.bookjpa.dto.BookDTO;
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

    @GetMapping("/{bid}")
    public Book getBookId(@PathVariable long bid){
        return bookJpaService.getBookId(bid);
    }

    @PostMapping("/")
    public Book postBook(@RequestBody BookDTO bookDTO){
        Book data = bookJpaService.postBook(bookDTO);
        return data;
    }

    @DeleteMapping("/{bid}")
    public ApiResponse<Book> updateIsDelBookById(@PathVariable long bid){
        return bookJpaService.updateIsDelBookById(bid);
    }
}
