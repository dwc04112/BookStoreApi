package com.bookstore.bookstoreapi.member;

import com.bookstore.bookstoreapi.member.DTO.MemberByAdmin;
import com.bookstore.bookstoreapi.member.DTO.MemberDTO;
import com.bookstore.bookstoreapi.member.DTO.SimpleInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberService {
    final MemberRepository memberRepository;

    @Value("${profileImg.path}")
    private String uploadFolder;

    private Member memberData(){
        Optional<Member> memberData = memberRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        return memberData.orElseThrow(() -> new RuntimeException("no data : find member by email"));
    }

    public SimpleInfo getUserInfo(MemberDTO memberDto) {
        Optional<SimpleInfo> member = memberRepository.findSimpleInfo(memberDto.getEmail());
        return member.orElseThrow(() -> new RuntimeException("no data : find member by email"));
    }


    @Transactional
    public MemberDTO editProfile(String nickName, MultipartFile multipartFile) {
        Member data = memberData();

        String imgFileName = data.getMid() + "mid" + multipartFile.getOriginalFilename();
        Path imgFilePath = Paths.get(uploadFolder+imgFileName);

        MemberDTO memberDTO = new MemberDTO();

        if(multipartFile.getSize() !=0){
            try{
                if(data.getProfilePicture() != null){
                    File file = new File(uploadFolder+data.getProfilePicture());
                    file.delete();
                }
                Files.write(imgFilePath, multipartFile.getBytes());
            } catch (Exception e){
                e.printStackTrace();
            }
            data.updatePicture(imgFileName);
            Member result = memberRepository.save(data);
            memberDTO.setProfilePicture(result.getProfilePicture());
            memberDTO.setNickName(result.getNickName());
        }

        if(nickName!=null && nickName.length()>0){
            data.updateNickName(nickName);
            Member result = memberRepository.save(data);
            memberDTO.setProfilePicture(result.getProfilePicture());
            memberDTO.setNickName(result.getNickName());
        }
        return memberDTO;
    }


    @Transactional
    public Member editInfo(MemberDTO memberDto) {
        Member data = memberData();
        try{
            data.updateEmail(memberDto.getEmail());
            data.updatePhoneNum(memberDto.getPhoneNum());
            return memberRepository.save(data);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public List<MemberByAdmin> getMemberByAdmin(){
        return memberRepository.findMemberByIsDel("N");
    }

}
