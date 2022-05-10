package com.bookstore.bookstoreapi.comment.service;



import com.bookstore.bookstoreapi.comment.dto.CommentDTO;
import com.bookstore.bookstoreapi.comment.model.Comment;
import com.bookstore.bookstoreapi.comment.model.CommentRepository;
import com.bookstore.bookstoreapi.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class CommentService {

    final CommentRepository commentRepository;

    //전체
    public List<Comment> getCommentList() {
        return commentRepository.findCommentByIsDel("N");
    }

    //해당 Book id 댓글만
    public List<Comment> getCommentById(long bid) {
        return commentRepository.findCommentByBidAndIsDel(bid,"N");
    }

    //댓글쓰기
    public ApiResponse<Comment> postComment(CommentDTO commentDTO) {
        long newCidValue = this.getNewCidValue(commentRepository);

        Comment postData = Comment.builder()
                .cid(newCidValue)
                .mid(commentDTO.getMid())
                .bid(commentDTO.getBid())
                .ratings(commentDTO.getRatings())
                .nickName(commentDTO.getNickName())
                .content(commentDTO.getContent())
                .cPopularity(commentDTO.getCPopularity())
                .cDate(LocalDate.now())
                .cTime(LocalTime.now())
                .isDel("N")
                .build();
        commentRepository.save(postData);
        return new ApiResponse<>(true, "Registration successfully ", postData);
    }

    //댓글 id를 정하는 로직
    private long getNewCidValue(CommentRepository commentRepository) {
        long result;
        Comment CommentOfMaxId = commentRepository.findTopByOrderByCidDesc();
        log.debug("section 1" + CommentOfMaxId);
        if (CommentOfMaxId == null) {
            result = 1;
            log.debug("no board data, maxId is 1");
        } else {
            result = CommentOfMaxId.getCid() + 1;
            log.debug("maxId From Comment=" + CommentOfMaxId.getCid());
        }
        log.debug("new Comment Id Value=" + result);
        return result;
    }
}
