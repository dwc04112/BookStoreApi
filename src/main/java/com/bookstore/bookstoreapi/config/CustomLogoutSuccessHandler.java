package com.bookstore.bookstoreapi.config;


import com.bookstore.bookstoreapi.token.Token;
import com.bookstore.bookstoreapi.token.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;


@Slf4j
public class CustomLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler{

    @Autowired
    public TokenService tokenService;

    @Override public void onLogoutSuccess(HttpServletRequest request,
                                          HttpServletResponse response,
                                          Authentication authentication) throws IOException, ServletException {
        log.debug("CustomLogoutSuccessHandler.onLogoutSuccess ::::");

        String requestTokenHeader = request.getHeader("Authorization");
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            String jwtToken = requestTokenHeader.substring(7);
            LocalTime jwtTokenExpTime = getExpTime(jwtToken);
            Token data = tokenService.insertToken(jwtToken,jwtTokenExpTime);
        }else {
            log.warn("JWT Token does not begin with Bearer String");
        }
        super.onLogoutSuccess(request, response, authentication);
    }

    public LocalTime getExpTime(String jwtToken){
        JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
        Date jwtTokenExp = jwtTokenUtil.getExpirationDateFromToken(jwtToken); //jwtTokenUtil 에서 구한 Date
        LocalDateTime time2 = LocalDateTime.ofInstant(jwtTokenExp.toInstant(), ZoneId.systemDefault()); //data -> LocalDataTime
        LocalTime JwtTokenTime = LocalTime.of(time2.getHour(), time2.getMinute(), time2.getSecond()); //LocalDataTime -> LocalTime
        log.debug("time :: " + JwtTokenTime);
        return JwtTokenTime;
    }
}

