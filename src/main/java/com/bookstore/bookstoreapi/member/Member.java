package com.bookstore.bookstoreapi.member;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mid")
    private Long mid;

    @Column(unique = true)
    private String email;
    private String password;
    private String nickName;
    private String fullName;
    private String phoneNum;
    private String profilePicture;
    private String userRule;
    private LocalDate createDate;
    private String isDel;

    public void updatePicture(String profilePicture){
        this.profilePicture = profilePicture;
    }
    public void updateNickName(String nickName){
        this.nickName = nickName;
    }
    public void updateEmail(String email){
        this.email = email;
    }
    public void updatePhoneNum(String phoneNum){
        this.phoneNum = phoneNum;
    }

    public Member(String email, String password, String nickName, String fullName, String phoneNum, String profilePicture, String userRule, LocalDate createDate,String isDel) {
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.fullName = fullName;
        this.phoneNum = phoneNum;
        this.profilePicture = profilePicture;
        this.userRule = userRule;
        this.createDate = createDate;
        this.isDel = isDel;
    }

    public static Member createMember(String email, String password, String nickName, String fullName, String phoneNum, String profilePicture, String userRule, LocalDate createDate,String isDel){
        return new Member(email, password, nickName, fullName, phoneNum, profilePicture, userRule , createDate, isDel);
    }

}
