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
    @GetMapping(value = "/all")
    public List<AddressBook> getAllAddrList(){
        return addrService.getAllAddr();
    }

    //2. 내주소록만
    @GetMapping(value = "/")
    public List<AddressBook> getMyAddrList(){
        return addrService.getMyAddr();
    }

    //3. 주소록 추가 or 수정
    @PostMapping(value = "/")
    public ApiResponse<AddressBook> postComment(@RequestBody AddrDTO addrDTO)throws Exception{
        log.debug("0이면 수정, 아니면 추가 : " + addrDTO.getAddrId());
        if(addrDTO.getAddrId()==0){
            return addrService.addAddr(addrDTO);
        }else{
            return addrService.editAddr(addrDTO);
        }
    }

    //2. 내주소록만
    @DeleteMapping(value = "/{addrId}")
    public ApiResponse<AddressBook> deleteByAddrId(@PathVariable long addrId){
        return addrService.deleteByAddrId(addrId);
    }
}
