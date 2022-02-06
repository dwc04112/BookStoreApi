package com.bookstore.bookstoreapi.security.service;


import com.bookstore.bookstoreapi.member.Member;
import com.bookstore.bookstoreapi.member.MemberRepository;
import com.bookstore.bookstoreapi.member.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(Role.USER.getValue()));
        if(email.equals("superuser@email.com")){
            grantedAuthorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
        }
        return new User(member.getEmail(), member.getPassword(), grantedAuthorities);
    }

    public Member authenticateByEmailAndPassword(String email, String password){
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));
        if(!passwordEncoder.matches(password, member.getPassword())){
            throw new BadCredentialsException("Password not matched");
        }
        return member;
    }
}
