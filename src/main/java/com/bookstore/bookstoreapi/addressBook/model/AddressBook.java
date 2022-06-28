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
    private String addrName;
    private String postcode;
    private String addr;
    private String detailAddr;
    private String phoneNum;
    private String isDel;

    public void updateIsDel(String isDel){
        this.isDel = isDel;
    }

    public void editAddr(
            String addrName,
            String postcode,
            String addr,
            String detailAddr,
            String phoneNum){
        this.addrName = addrName;
        this.postcode = postcode;
        this.addr = addr;
        this.detailAddr = detailAddr;
        this.phoneNum = phoneNum;
    }

    public AddressBook(
                long addrId,
                long mid,
                String addrName,
                String postcode,
                String addr,
                String detailAddr,
                String phoneNum,
                String isDel){
        this.addrId = addrId;
        this.mid = mid;
        this.addrName = addrName;
        this.postcode = postcode;
        this.addr = addr;
        this.detailAddr = detailAddr;
        this.phoneNum = phoneNum;
        this.isDel = isDel;
    }
}
