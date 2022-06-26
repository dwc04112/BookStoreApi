package com.bookstore.bookstoreapi.addressBook.service;

import com.bookstore.bookstoreapi.addressBook.dto.AddrDTO;
import com.bookstore.bookstoreapi.addressBook.model.AddrRepository;
import com.bookstore.bookstoreapi.addressBook.model.AddressBook;
import com.bookstore.bookstoreapi.common.ApiResponse;
import com.bookstore.bookstoreapi.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class AddrService {

    final AddrRepository addrRepository;
    final MemberRepository memberRepository;

    //SecurityContextHolder 저장된 사용자 Email 통해 사용자 Mid 가져오는 로직
    private Long getMemberIdByEmail() {
        String memberEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return memberRepository.getMemberIdByEmail(memberEmail);
    }


    // 1. 모든 주소록 보기
    public List<AddressBook> getAllAddr() {
       return addrRepository.findAllBy();
    }


    //3. 새 주소록 추가
    public ApiResponse<AddressBook> addAddr(AddrDTO addrDTO) {
        long newAddrId = this.getNewAddrIdValue(addrRepository);

        AddressBook postData = AddressBook.builder()
                .addrId(newAddrId)
                .mid(getMemberIdByEmail())
                .postcode(addrDTO.getPostcode())
                .addr(addrDTO.getAddr())
                .detailAddr(addrDTO.getDetailAddr())
                .isDel("N")
                .build();
        addrRepository.save(postData);
        return new ApiResponse<>(true, "add to addressBook successfully ", postData);
    }

    //3. 주소록 id를 정하는 로직
    private long getNewAddrIdValue(AddrRepository addrRepository) {
        long result;
        AddressBook AddrOfMaxId = addrRepository.findTopByOrderByAddrIdDesc();
        if (AddrOfMaxId == null) {
            result = 1;
            log.debug("no addr data, maxId is 1");
        } else {
            result = AddrOfMaxId.getAddrId() + 1;
            log.debug("maxId From addr =" + AddrOfMaxId.getAddrId());
        }
        log.debug("new addr Id =" + result);
        return result;
    }
}
