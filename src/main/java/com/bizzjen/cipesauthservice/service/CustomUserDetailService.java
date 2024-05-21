package com.bizzjen.cipesauthservice.service;

import com.bizzjen.cipesauthservice.dto.CustomUserDetail;
import com.bizzjen.cipesauthservice.entity.UserCredential;
import com.bizzjen.cipesauthservice.repository.UserCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomUserDetailService implements UserDetailsService {


    @Autowired
    private UserCredentialRepository repository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserCredential> credential = repository.findByName(username);
        return credential.map(CustomUserDetail::new).orElseThrow(
                () -> new UsernameNotFoundException("user not found with name: " + username ));
    }
}
