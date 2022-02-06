package com.bookstore.bookstoreapi.member;

import lombok.*;
import javax.persistence.*;

@Entity
@Getter
@Setter
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

    public Member(String email, String password, String nickName, String fullName) {
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.fullName = fullName;
    }

    public static Member createMember(String email, String password, String nickName, String fullName){
        return new Member(email, password, nickName, fullName );
    }

}
