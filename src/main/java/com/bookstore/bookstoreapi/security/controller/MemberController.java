package com.bookstore.bookstoreapi.security.controller;


import com.bookstore.bookstoreapi.common.ApiResponse;
import com.bookstore.bookstoreapi.member.DTO.MemberDTO;
import com.bookstore.bookstoreapi.member.DTO.UpdatePassDto;
import com.bookstore.bookstoreapi.member.Member;
import com.bookstore.bookstoreapi.member.MemberRepository;
import com.bookstore.bookstoreapi.member.MemberService;
import com.bookstore.bookstoreapi.member.SimpleInfo;
import com.bookstore.bookstoreapi.security.service.JwtUserDetailsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import jdk.internal.org.objectweb.asm.TypeReference;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;


@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {

    final MemberRepository memberRepository;
    final PasswordEncoder passwordEncoder;
    final MemberService memberService;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @PostMapping("/api/member")
    public String saveMember(@RequestBody MemberDTO memberDto){
        memberRepository.save(Member.createMember(memberDto.getEmail(),
                passwordEncoder.encode(memberDto.getPassword()) , memberDto.getNickName(), memberDto.getFullName(), memberDto.getPhoneNum(), memberDto.getProfilePicture()));
        return "success";
    }

    @PostMapping("/signup/doublecheck")
    public Integer doubleCheck(@RequestBody MemberDTO memberDto){
        log.debug("닉네임? : "+ memberDto.getNickName());
        String nick = memberDto.getNickName();
        return check(nick);
        //중복확인
    }
    public Integer check(String nick){
        return memberRepository.getMemberByNickname(nick);
    }

    @PostMapping("/user/info")
    public SimpleInfo getUserInfo(@RequestBody MemberDTO memberDto){
        log.debug("user info " + memberDto);
        memberService.editInfo(memberDto);
        return null;
    }

    @SneakyThrows
    @PostMapping("/user/profile")
    public MemberDTO editProfile(@RequestParam("nick")String nickName, @RequestParam("file")MultipartFile multipartFile) {

        return memberService.editProfile( nickName.replace("\"", ""), multipartFile);
/*
        if(memberDto.getNickName()!=null){
         memberRepository.EditMemberNickName(memberDto.getNickName(), memberDto.getMid());
         return "successful edit nickname !";
        }
        else if(memberDto.getFullName()!=null){
            memberRepository.EditMemberName(memberDto.getFullName(),memberDto.getMid());
            return "successful edit name !";
        }

 */
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

