package com.bookstore.bookstoreapi.security.controller;

import com.bookstore.bookstoreapi.bookjpa.dto.BookDataDTO;
import com.bookstore.bookstoreapi.bookjpa.model.Book;
import com.bookstore.bookstoreapi.bookjpa.service.BookJpaService;
import com.bookstore.bookstoreapi.comment.model.Comment;
import com.bookstore.bookstoreapi.comment.service.CommentService;
import com.bookstore.bookstoreapi.common.ApiResponse;
import com.bookstore.bookstoreapi.member.DTO.MemberByAdmin;
import com.bookstore.bookstoreapi.member.MemberService;
import com.bookstore.bookstoreapi.order.model.Orders;
import com.bookstore.bookstoreapi.order.service.OrderService;
import com.bookstore.bookstoreapi.payments.dto.ImportDTO;
import com.bookstore.bookstoreapi.payments.service.PaymentsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(value = "/admin")
public class AdminController {

    final OrderService orderService;
    final MemberService memberService;
    final CommentService commentService;
    final PaymentsService paymentsService;
    final BookJpaService bookJpaService;


    private boolean adminFilter() throws Exception{
        boolean result = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString().contains("ROLE_ADMIN");
        if(!result){
            throw new Exception("not Admin");
        }else{
            return true;
        }
    }


    // 주문관련
    @GetMapping("/order")
    public List<Orders> orderManagement()throws Exception{
        if(adminFilter()){
            return orderService.findOrdersByAdmin();
        }
        return null;
    }
    @PostMapping("/payCancel")
    public ApiResponse<String> orderCancelManagement(@RequestBody ImportDTO importDTO)throws Exception{
        if(adminFilter()){
            log.debug("Admin : cancel Payments : " + importDTO);
            return paymentsService.cancelPay(importDTO);
        }
        return null;
    }


    // 회원관련
    @GetMapping("/member")
    public List<MemberByAdmin> memberManagement() throws Exception{
        if(adminFilter()){
            return memberService.getMemberByAdmin();
        }
        return null;
    }

    // 댓글관련
    @GetMapping("/comment")
    public List<Comment> commentManagement()throws Exception{
        if(adminFilter()){
            return commentService.getCommentList();
        }
        return null;
    }
    @DeleteMapping("/comment/{cidArr}")
    public String commentManagement(@PathVariable List<Long> cidArr)throws Exception{
        if(adminFilter()){
            return commentService.updateIsDelByAdmin(cidArr);
        }
        return null;
    }

    // Book
    @GetMapping("/book")
    public List<BookDataDTO> bookManagement()throws Exception{
        if(adminFilter()){
            return bookJpaService.getBookByAdmin();
        }
        return null;
    }

    //책 상태변경
    @DeleteMapping("/book/{bid}")
    public ApiResponse<Book> updateIsDelBookById(@PathVariable long bid) throws Exception {
        if(adminFilter()){
            return bookJpaService.updateIsDelBookById(bid);
        }
        return null;
    }
}
