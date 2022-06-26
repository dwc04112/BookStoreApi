package com.bookstore.bookstoreapi.addressBook.model;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddrRepository extends JpaRepository<AddressBook, Long> {

    List<AddressBook> findAllBy();

    AddressBook findTopByOrderByAddrIdDesc();
}
