package com.bookstore.bookstoreapi.addressBook.service;

import com.bookstore.bookstoreapi.addressBook.dto.AddrDTO;
import com.bookstore.bookstoreapi.addressBook.model.AddrRepository;
import com.bookstore.bookstoreapi.addressBook.model.AddressBook;
import com.bookstore.bookstoreapi.comment.model.Comment;
import com.bookstore.bookstoreapi.common.ApiResponse;
import com.bookstore.bookstoreapi.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

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

    // 2. 내 주소록
    public List<AddressBook> getMyAddr() {
        return addrRepository.findAllByMidAndIsDel(getMemberIdByEmail(), "N");
    }

    //3. 새 주소록 추가
    public ApiResponse<AddressBook> addAddr(AddrDTO addrDTO) {
        long newAddrId = this.getNewAddrIdValue(addrRepository);

        AddressBook postData = AddressBook.builder()
                .addrId(newAddrId)
                .mid(getMemberIdByEmail())
                .addrName(addrDTO.getAddrName())
                .postcode(addrDTO.getPostcode())
                .addr(addrDTO.getAddr())
                .detailAddr(addrDTO.getDetailAddr())
                .phoneNum(addrDTO.getPhoneNum())
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

    //3. 해당id 주소록 수정
    public ApiResponse<AddressBook> editAddr(AddrDTO addrDTO) {
        Optional<AddressBook> addrData = addrRepository.findAddressBookByAddrIdAndIsDel(addrDTO.getAddrId(), "N");
        AddressBook data = addrData.orElseThrow(() -> new RuntimeException("no data"));
        //북 오너 정보와 매치 필요
        if (data.getMid() == getMemberIdByEmail()) {
            data.editAddr(addrDTO.getAddrName(), addrDTO.getPostcode(), addrDTO.getAddr(), addrDTO.getDetailAddr(), addrDTO.getPhoneNum());
            AddressBook result = addrRepository.save(data);
            return new ApiResponse<>(true, "Address id " + addrDTO.getAddrId() +" is successfully Edit", result);
        } else {
            return new ApiResponse<>(false, "failed to edit Address id " + addrDTO.getAddrId());
        }
    }

    public ApiResponse<AddressBook> deleteByAddrId(long addrId) {
        Optional<AddressBook> addrData = addrRepository.findAddressBookByAddrIdAndIsDel(addrId, "N");
        AddressBook data = addrData.orElseThrow(() -> new RuntimeException("no data"));
        //북 오너 정보와 매치 필요
        if (data.getMid() == getMemberIdByEmail()) {
            data.updateIsDel("Y");
            AddressBook result = addrRepository.save(data);
            return new ApiResponse<>(true, "Address id " + addrId +" is successfully deleted", result);
        } else {
            return new ApiResponse<>(false, "failed to delete Address id " + addrId);
        }
    }

}
