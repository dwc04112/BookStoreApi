package com.bookstore.bookstoreapi.security.controller;


import com.bookstore.bookstoreapi.common.ApiResponse;
import com.bookstore.bookstoreapi.member.Member;
import com.bookstore.bookstoreapi.member.MemberRepository;
import com.bookstore.bookstoreapi.security.service.JwtUserDetailsService;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.Optional;


@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {

    final MemberRepository memberRepository;
    final PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @PostMapping("/api/member")
    public String saveMember(@RequestBody MemberDto memberDto){
        memberRepository.save(Member.createMember(memberDto.getEmail(),
                passwordEncoder.encode(memberDto.getPassword()) , memberDto.getNickName(), memberDto.getFullName() ));
        return "success";
    }

    @PostMapping("/signup/doublecheck")
    public Integer doubleCheck(@RequestBody MemberDto memberDto){
        log.debug("닉네임? : "+ memberDto.getNickName());
        String nick = memberDto.getNickName();
        return check(nick);
        //중복확인
    }
    public Integer check(String nick){
        return memberRepository.getMemberByNickname(nick);
    }



    @PostMapping("/user/edit")
    public String editUserInfo(@RequestBody MemberDto memberDto){
        log.debug("Member : " + memberDto);
        if(memberDto.getNickName()!=null){
         memberRepository.EditMemberNickName(memberDto.getNickName(), memberDto.getMid());
         return "successful edit nickname !";
        }
        else if(memberDto.getFullName()!=null){
            memberRepository.EditMemberName(memberDto.getFullName(),memberDto.getMid());
            return "successful edit name !";
        }
        return null;
    }

    @PostMapping("/edit/password")
    public ApiResponse<String> editPassword(@RequestBody UpdatePassDto updatePassDto){
        String oldPass = updatePassDto.getOldPassword();    //기존 비번
        String newPass = updatePassDto.getNewPassword();    //바꿀 비번

        if(oldPass.equals(newPass)){
            return new ApiResponse<>(false, "기존 비밀번호와 새 비밀번호는 같을 수 없습니다", null);
        }
        if(checkOldPass(oldPass)>0){
            newPass = passwordEncoder.encode(newPass);
            memberRepository.EditNewPassword(newPass,checkOldPass(oldPass));
            return new ApiResponse<>(true, "Successful edit password", null);
        }
        return null;
    }
    public Long checkOldPass(String oldPass) {
        //로그인된 정보에서 (SecurityContextHolder) Email 가져오기
        String memberEmail = SecurityContextHolder.getContext().getAuthentication().getName();  //현재 이메일
        //oldPass 가 현재 로그인한 정보(email)의 비밀번호와 같은 비밀번호인지 검사
        return userDetailsService.authenticateByEmailAndPassword(memberEmail, oldPass).getMid();
    }
}

@Data
class MemberDto{
    private Long mid;
    private String email;
    private String password;
    private String nickName;
    private String fullName;
}

@Data
@Getter
class UpdatePassDto{
    private String oldPassword;
    private String newPassword;
}