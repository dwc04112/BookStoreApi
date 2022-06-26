package com.bookstore.bookstoreapi.addressBook.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Table(name = "addressBook")
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class AddressBook {

    @Id
    @Column(name = "addrId")
    private long addrId;

    @Column(unique = true)
    private long mid;
    private String postcode;        // 우편번호
    private String addr;            // 주소
    private String detailAddr;      // 상세주소
    private String isDel;

    public void updateIsDel(String isDel){
        this.isDel = isDel;
    }

    public AddressBook(
                long addrId,
                long mid,
                String postcode,
                String addr,
                String detailAddr,
                String isDel){
        this.addrId = addrId;
        this.mid = mid;
        this.postcode = postcode;
        this.addr = addr;
        this.detailAddr = detailAddr;
        this.isDel = isDel;
    }
}
