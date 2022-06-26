package com.bookstore.bookstoreapi.addressBook.controller;

import com.bookstore.bookstoreapi.addressBook.dto.AddrDTO;
import com.bookstore.bookstoreapi.addressBook.model.AddressBook;
import com.bookstore.bookstoreapi.addressBook.service.AddrService;
import com.bookstore.bookstoreapi.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(value = "/addr")
public class AddrController {
    public final AddrService addrService;

    //1. 전체
    @GetMapping(value = "/")
    public List<AddressBook> getCommentList(){
        return addrService.getAllAddr();
    }

    //3. 주소록 추가
    @PostMapping(value = "/")
    public ApiResponse<AddressBook> postComment(@RequestBody AddrDTO addrDTO)throws Exception{
        return addrService.addAddr(addrDTO);
    }
}
