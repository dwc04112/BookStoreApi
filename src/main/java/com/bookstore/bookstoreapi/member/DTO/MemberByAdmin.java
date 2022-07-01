package com.bookstore.bookstoreapi.member.DTO;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MemberByAdmin {
    private Long mid;
    private String email;
    private String nickName;
    private String fullName;
    private String phoneNum;
    private String userRule;
    private LocalDate createDate;

    public MemberByAdmin(long mid,
                         String email,
                         String nickName,
                         String fullName,
                         String phoneNum,
                         String userRule,
                         LocalDate createDate){
        this.mid = mid;
        this.email = email;
        this.nickName = nickName;
        this.fullName = fullName;
        this.phoneNum = phoneNum;
        this.userRule = userRule;
        this.createDate = createDate;
    }
}
