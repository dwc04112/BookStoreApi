package com.bookstore.bookstoreapi.comment.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    //전체
    List<Comment> findCommentByIsDel(String isDel);
    //해당책
    List<Comment> findCommentByBidAndIsDel(Long bid, String isDel);

    Comment findTopByOrderByCidDesc();
}
