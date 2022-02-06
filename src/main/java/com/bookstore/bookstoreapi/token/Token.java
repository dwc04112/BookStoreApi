package com.bookstore.bookstoreapi.token;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Token {

    @Id
    @Column(name = "tokenid")
    private long tokenid;

    @Column(name = "token")
    private String token;
    @Column(name = "expirationTime")
    private LocalTime expirationTime;

    public Token(Long tokenid,
                 String token,
                 LocalTime expirationTime){
        this.tokenid = tokenid;
        this.token = token;
        this.expirationTime = expirationTime;
    }
}
