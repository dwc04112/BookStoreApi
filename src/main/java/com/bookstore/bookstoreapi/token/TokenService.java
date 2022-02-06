package com.bookstore.bookstoreapi.token;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalTime;

@RequiredArgsConstructor
@Service
@Slf4j
public class TokenService {

    final TokenRepository tokenRepository;

    public Token insertToken(String jwtToken, LocalTime jwtTokenExpTime){
        log.debug("token service : " + jwtToken+" && " + jwtTokenExpTime);
        Token tokenInsert = Token.builder()
                .token(jwtToken)
                .expirationTime(jwtTokenExpTime)
                .build();
        return tokenRepository.save(tokenInsert);
    }

    public boolean compareToken(String jwtToken) {
        int result = tokenRepository.compareToken(jwtToken);
        return result == 0;
    }
}
