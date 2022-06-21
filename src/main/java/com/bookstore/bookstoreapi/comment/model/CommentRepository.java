package com.bookstore.bookstoreapi.comment.model;


import com.bookstore.bookstoreapi.comment.dto.RatingsCount;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {


    //전체 댓글
    List<Comment> findCommentByIsDel(String isDel);

    //2. 해당책의 댓글
    //최신순 0
    Page<Comment> findCommentByBidAndIsDelOrderByCommentDateDescCommentTimeDesc(Long bid, String isDel, Pageable pageable);
    //추천순 1
    Page<Comment> findCommentByBidAndIsDelOrderByPopularityDesc(Long bid, String isDel, Pageable pageable);
    //별점순 2
    Page<Comment> findCommentByBidAndIsDelOrderByRatingsDesc(Long bid, String isDel, Pageable pageable);

    //pop count
    @Query(value = "select c.ratings, count(c.ratings) as count FROM Comment c where c.bid=:bid and c.isDel=:isDel group by c.ratings order by c.ratings DESC ", nativeQuery = true)
    List<RatingsCount> getRatingsCount(@Param("bid") Long bid, @Param("isDel") String isDel);

    //pop update
    @Transactional
    @Modifying
    @Query("update Comment set popularity = popularity + (?1) where cid=?2")
    void updatePop(int update,long cid);

    //post 시 cid 정하는 로직
    Comment findTopByOrderByCidDesc();


    //4. 내가 쓴 댓글
    //불러오기
    List<Comment> findCommentByMidAndIsDel(Long mid, String isDel);
    //4-1 count
    Integer countCommentByMidAndIsDel(Long mid, String isDel);
    //4-1 ratings
    @Query("select avg(c.ratings) from Comment c where c.mid=?1 and c.isDel = ?2")
    Double avgRatings(long mid, String isDel);
    //4-1
    //pop count
    @Query(value = "select c.ratings, count(c.ratings) as count FROM Comment c where c.mid=:mid and c.isDel=:isDel group by c.ratings order by c.ratings DESC ", nativeQuery = true)
    List<RatingsCount> getTotalPopCount(@Param("mid") Long mid, @Param("isDel") String isDel);


    //댓글 삭제
    Optional<Comment> findCommentByCidAndIsDel(long cid, String isDel);
}
