package com.bookstore.bookstoreapi.comment.controller;



import com.bookstore.bookstoreapi.comment.dto.CommentDTO;
import com.bookstore.bookstoreapi.comment.dto.PopCountInterface;
import com.bookstore.bookstoreapi.comment.dto.PopularityDTO;
import com.bookstore.bookstoreapi.comment.dto.SortDTO;
import com.bookstore.bookstoreapi.comment.model.Comment;
import com.bookstore.bookstoreapi.comment.service.CommentService;
import com.bookstore.bookstoreapi.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(value = "/comment")
public class CommentController {

    public final CommentService commentService;

    //1. 전체
    @GetMapping(value = "/")
    public ApiResponse<Comment> getCommentList(){
        List<Comment> data = commentService.getCommentList();
        return new ApiResponse(true, data);
    }

    //2. 해당 Book id 댓글. sort by ( 0-default 최신순 , 1-추천순 , 2-인기순 )
    @PostMapping(value = "/")
    public List<Comment> getCommentById(@RequestBody SortDTO sortDTO)throws Exception{
        log.debug("sortType : " + sortDTO);
        return commentService.getCommentById(sortDTO);
    }

    //2-1 추천수 get
    @GetMapping(value = "/pop/{bid}")
    public List<PopCountInterface> getPopularity(@PathVariable long bid){
        log.debug("get pop : " + bid);
        return commentService.getPopularity(bid);
    }

    //2-2. 추천수 update
    @PostMapping(value = "/pop")
    public ApiResponse<Comment> updatePopularity(@RequestBody PopularityDTO popularityDTO)throws Exception{
        log.debug("hi : " + popularityDTO.toString());
        return commentService.updatePopularity(popularityDTO);
    }

    //3. 댓글 쓰기
    @PostMapping(value = "/write")
    public ApiResponse<Comment> postComment(@RequestBody CommentDTO commentDTO)throws Exception{
        return commentService.postComment(commentDTO);
    }

    //4. 댓글 삭제
    @DeleteMapping("/{cid}")
    public ApiResponse<Comment> updateIsDelByCid(@PathVariable long cid){
        return commentService.updateIsDelByCid(cid);
    }
}