package com.bookstore.bookstoreapi.member.DTO;

import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;

@Data
@Getter
public class MemberDTO {
    private Long mid;
    private String email;
    private String password;
    private String nickName;
    private String fullName;
    private String phoneNum;
    private String userRule;
    private String profilePicture;
    private LocalDate createDate;
}
