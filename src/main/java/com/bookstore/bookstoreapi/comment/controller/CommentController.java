package com.bookstore.bookstoreapi.comment.controller;


import com.bookstore.bookstoreapi.comment.dto.CommentDTO;
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

    //전체
    @GetMapping(value = "/")
    public ApiResponse<Comment> getCommentList(){
        List<Comment> data = commentService.getCommentList();
        return new ApiResponse(true, data);
    }

    //해당 Book id 댓글만
    @GetMapping(value = "/{bid}")
    public ApiResponse<Comment> getCommentById(@PathVariable Long bid)throws Exception{
        List<Comment> data = commentService.getCommentById(bid);
        return new ApiResponse(true, data);
    }

    //3. 댓글 쓰기
    @PostMapping(value = "/")
    public ApiResponse<Comment> postComment(@RequestBody CommentDTO commentDTO)throws Exception{
        log.debug("hi post" + commentDTO);
        return commentService.postComment(commentDTO);
    }
}
