package com.bookstore.bookstoreapi.security.controller;


import com.bookstore.bookstoreapi.common.ApiResponse;
import com.bookstore.bookstoreapi.member.DTO.MemberDTO;
import com.bookstore.bookstoreapi.member.DTO.UpdatePassDto;
import com.bookstore.bookstoreapi.member.Member;
import com.bookstore.bookstoreapi.member.MemberRepository;
import com.bookstore.bookstoreapi.member.MemberService;
import com.bookstore.bookstoreapi.member.DTO.SimpleInfo;
import com.bookstore.bookstoreapi.security.service.JwtUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDate;


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
                passwordEncoder.encode(memberDto.getPassword()) , memberDto.getNickName(), memberDto.getFullName(), memberDto.getPhoneNum(), memberDto.getProfilePicture(), "user" , LocalDate.now(), "N" ));
        return "success";
    }

    @PostMapping("/api/profile")
    public String saveProfile(@RequestParam("email")String email, @RequestParam("file")MultipartFile multipartFile) {
        log.debug("member : 회원가입 프로필 : " + email + " file : " + multipartFile.getOriginalFilename());
        return memberService.saveProfile( email.replace("\"", ""), multipartFile);
    }



    @GetMapping("/signup/doublecheck/{nick}")
    public Integer doubleCheck(@PathVariable String nick){
        log.debug("member : 닉네임 중복확인 : "+ nick);
        return memberRepository.getMemberByNickname(nick);
    }


    //로그인 정보로 정보 받아오기
    @PostMapping("/user/info")
    public SimpleInfo getUserInfo(@RequestBody MemberDTO memberDto){
        return memberService.getUserInfo(memberDto);
    }

    @PostMapping("/edit/info")
    public Member editEmail(@RequestBody MemberDTO memberDto) {
        log.debug("member : edit/info : "+memberDto);
        return memberService.editInfo(memberDto);
    }

    @SneakyThrows
    @PostMapping("/user/profile")
    public MemberDTO editProfile(@RequestParam("nick")String nickName, @RequestParam("file")MultipartFile multipartFile) {
        log.debug("profile ");
        log.debug("member : 프로필 수정 : " + nickName + " file : " + multipartFile.getOriginalFilename());
        return memberService.editProfile( nickName.replace("\"", ""), multipartFile);
    }

    @PostMapping("/user/nick")
    public MemberDTO editProfile(@RequestParam("nick")String nickName) {
        log.debug("member : 닉네임 수정 : " + nickName);
        return memberService.editProfile( nickName.replace("\"", ""),null);
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

