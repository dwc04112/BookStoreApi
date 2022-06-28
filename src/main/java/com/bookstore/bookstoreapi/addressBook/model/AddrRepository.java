package com.bookstore.bookstoreapi.addressBook.model;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddrRepository extends JpaRepository<AddressBook, Long> {

    List<AddressBook> findAllBy();

    List<AddressBook> findAllByMidAndIsDel(Long mid, String isDel);

    Optional<AddressBook> findAddressBookByAddrIdAndIsDel(Long mid, String isDel);

    AddressBook findTopByOrderByAddrIdDesc();
}
