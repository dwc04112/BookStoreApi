package com.bookstore.bookstoreapi.member;

import com.bookstore.bookstoreapi.member.DTO.MemberDTO;
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
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberService {
    final MemberRepository memberRepository;

    @Value("${profileImg.path}")
    private String uploadFolder;


    @Transactional
    public MemberDTO editProfile(String nickName, MultipartFile multipartFile) {
        Optional<Member> memberData = memberRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        Member data = memberData.orElseThrow(() -> new RuntimeException("no data : find order by order_id"));

        String imgFileName = data.getMid() + "_" + multipartFile.getOriginalFilename();
        Path imgFilePath = Paths.get(uploadFolder+imgFileName);

        MemberDTO memberDTO = new MemberDTO();

        if(multipartFile.getSize() !=0){
            try{
                if(data.getProfilePicture() != null){
                    File file = new File(uploadFolder+ data.getProfilePicture());
                    file.delete();
                }
                Files.write(imgFilePath, multipartFile.getBytes());
            } catch (Exception e){
                e.printStackTrace();
            }
            data.updatePicture(imgFileName);
            Member result = memberRepository.save(data);
            memberDTO.setProfilePicture(result.getProfilePicture());
        }

        if(nickName!=null && nickName.length()>0){
            data.updateNickName(nickName);
            Member result = memberRepository.save(data);
            memberDTO.setProfilePicture(result.getNickName());
        }


        return memberDTO;
    }

    public void editInfo(MemberDTO memberDto) {
        Optional<SimpleInfo> member = memberRepository.findSimpleInfo(SecurityContextHolder.getContext().getAuthentication().getName());
        member.orElseThrow(() -> new RuntimeException("no data : find order by order_id"));
    }
}
