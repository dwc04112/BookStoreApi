package com.bookstore.bookstoreapi.member;

import lombok.*;
import javax.persistence.*;

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

    public void updatePicture(String profilePicture){
        this.profilePicture = profilePicture;
    }

    public void updateNickName(String nickName){
        this.nickName = nickName;
    }

    public Member(String email, String password, String nickName, String fullName, String phoneNum, String profilePicture) {
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.fullName = fullName;
        this.phoneNum = phoneNum;
        this.profilePicture = profilePicture;
    }

    public static Member createMember(String email, String password, String nickName, String fullName, String phoneNum, String profilePicture){
        return new Member(email, password, nickName, fullName, phoneNum, profilePicture);
    }

}
