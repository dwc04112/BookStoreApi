package com.bookstore.bookstoreapi.member;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface MemberRepository extends CrudRepository<Member , Long> {
    Optional<Member> findByEmail(String email);

    Optional<Member> findByMid(Long mid);

    @Query("select mid From Member where email= ?1")
    Long getMemberIdByEmail(String email);

    @Query("select m.mid as mid, m.fullName as fullName, m.nickName as nickName,m.phoneNum as phoneNum , m.profilePicture as profilePicture FROM Member m where m.email=?1")
    Optional<SimpleInfo> findSimpleInfo(String email);


    @Query("SELECT count (m.nickName) FROM Member m where m.nickName=?1")
    Integer getMemberByNickname(String nickName);

    @Transactional
    @Modifying
    @Query("update Member m set m.nickName = ?1 where m.mid=?2")
    void EditMemberNickName(String nickName, Long id);

    @Transactional
    @Modifying
    @Query("update Member m set m.fullName =?1  where m.mid=?2")
    void EditMemberName(String fullName, Long id);

    //비밀번호 변경
    //1. 비밀번호 체크
    @Transactional
    @Modifying
    @Query("update Member m set m.password =?1  where m.mid=?2")
    void EditNewPassword(String newPass, Long id);

}
