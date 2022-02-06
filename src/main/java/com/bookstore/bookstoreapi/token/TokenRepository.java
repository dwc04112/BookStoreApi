package com.bookstore.bookstoreapi.token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TokenRepository extends JpaRepository<Token, String> {

    @Query("select count(t.token) FROM Token t where t.token = ?1")
    Integer compareToken(String jwtToken);
}
