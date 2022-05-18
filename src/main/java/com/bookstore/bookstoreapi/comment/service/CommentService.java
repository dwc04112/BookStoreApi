package com.bookstore.bookstoreapi.comment.service;



import com.bookstore.bookstoreapi.comment.dto.*;
import com.bookstore.bookstoreapi.comment.model.Comment;
import com.bookstore.bookstoreapi.comment.model.CommentBook;
import com.bookstore.bookstoreapi.comment.model.CommentBookRepository;
import com.bookstore.bookstoreapi.comment.model.CommentRepository;
import com.bookstore.bookstoreapi.common.ApiResponse;
import com.bookstore.bookstoreapi.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class CommentService {

    final CommentRepository commentRepository;
    final CommentBookRepository commentBookRepository;
    final MemberRepository memberRepository;

    //1. 전체
    public List<Comment> getCommentList() {
        // return commentRepository.findCommentByIsDel("N");
        return commentRepository.findCommentByIsDel("N");
    }

    //2. 해당 Book id 댓글만
    public Page<Comment> getCommentById(SortDTO sortDTO) {

        PageRequest pageRequest = PageRequest.of(sortDTO.getPage() , sortDTO.getSize());

        //최신순
        if(sortDTO.getSortType() == 0) {
            return commentRepository.findCommentByBidAndIsDelOrderByCommentDateDescCommentTimeDesc(sortDTO.getBid(), "N", pageRequest);
        }
        //추천순
        if(sortDTO.getSortType() == 1) {
            return commentRepository.findCommentByBidAndIsDelOrderByPopularityDesc(sortDTO.getBid(), "N", pageRequest);
        }
        //별점순
        if(sortDTO.getSortType() == 2) {
            return commentRepository.findCommentByBidAndIsDelOrderByRatingsDesc(sortDTO.getBid(), "N", pageRequest);
        }
        return null;
    }
    //2-1. 추천수 get
    public List<PopCountInterface> getPopularity(long bid) {
        return commentRepository.getPopCount(bid,"N");
    }

    //2-2. 추천수 update
    public ApiResponse<Comment> updatePopularity(PopularityDTO popularityDTO) {
        Optional<Comment> commentData = commentRepository.findCommentByCidAndIsDel(popularityDTO.getCid(), "N");
        Comment data = commentData.orElseThrow(() -> new RuntimeException("no data"));
        int update = 0;
        if(popularityDTO.getUpdate().equals("up")){
            update = 1;
        }else if(popularityDTO.getUpdate().equals("down")){
            update = -1;
        }else{
            return new ApiResponse<>(false,"failed to update Popularity");
        }
        commentRepository.updatePop(update, popularityDTO.getCid());
        return new ApiResponse<>(true,"success to update Popularity up, cid = "+data.getCid() );
    }


    //3. 댓글쓰기
    public ApiResponse<Comment> postComment(CommentDTO commentDTO) {
        long newCidValue = this.getNewCidValue(commentRepository);

        Comment postData = Comment.builder()
                .cid(newCidValue)
                .mid(commentDTO.getMid())
                .bid(commentDTO.getBid())
                .ratings(commentDTO.getRatings())
                .nickName(commentDTO.getNickName())
                .content(commentDTO.getContent())
                .popularity(0)
                .commentDate(LocalDate.now())
                .commentTime(LocalTime.now())
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

    //4. 내가 쓴 모든 댓글 불러오기
    public Page<CommentBookMapping> getMyCommentList(SortDTO sortDTO) {
        PageRequest pageRequest = PageRequest.of(sortDTO.getPage() , sortDTO.getSize());

        //Mid 가져오기
        long midByLoginInfo = getMemberIdByEmail(memberRepository);
        return commentBookRepository.findAllByBidAndIsDel(midByLoginInfo ,"N", pageRequest);
    }

    //5. 삭제
    public ApiResponse<Comment> updateIsDelByCid(long cid) {
        Optional<Comment> commentData = commentRepository.findCommentByCidAndIsDel(cid, "N");
        Comment data = commentData.orElseThrow(() -> new RuntimeException("no data"));
        //북 오너 정보와 매치 필요
        long midByLoginInfo = getMemberIdByEmail(memberRepository);

        if (data.getMid() == midByLoginInfo) {
            data.updateIsDel("Y");
            commentRepository.save(data);
            return new ApiResponse<>(true, "comment id " + cid +" is successfully deleted", data);
        } else {
            return new ApiResponse<>(false, "failed to delete comment id " + cid);
        }
    }

    //SecurityContextHolder 저장된 사용자 Email 통해 사용자 Mid 가져오는 로직
    private Long getMemberIdByEmail(MemberRepository memberRepository) {
        String memberEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return memberRepository.getMemberIdByEmail(memberEmail);
    }

}
