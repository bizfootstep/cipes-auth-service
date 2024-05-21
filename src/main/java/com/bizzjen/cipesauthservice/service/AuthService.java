package com.bizzjen.cipesauthservice.service;

import com.bizzjen.cipesauthservice.entity.UserCredential;
import com.bizzjen.cipesauthservice.repository.UserCredentialRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {


    private final Logger logger = LoggerFactory.getLogger(AuthService.class);
    private final UserCredentialRepository repository;
    private final PasswordEncoder passwordEncoder;


    private final JwtService jwtService;

    public AuthService(UserCredentialRepository repository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public String saveUser(UserCredential credential) {
        credential.setPassword(passwordEncoder.encode(credential.getPassword()));
        repository.save(credential);
        logger.info("User information is added to the System");
        return "User added to the system";
    }

    public String generateToken(String username) {
        return jwtService.generateToken(username);
    }

    public void validateToken(String token) {
        jwtService.validateToken(token);
    }
}
