package com.bizzjen.cipesauthservice.controller;

import com.bizzjen.cipesauthservice.dto.AuthRequestDto;
import com.bizzjen.cipesauthservice.entity.UserCredential;
import com.bizzjen.cipesauthservice.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    private final AuthenticationManager authenticationManager;

    public AuthController(AuthService authService, AuthenticationManager authenticationManager) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public String addNewUser(@RequestBody UserCredential user) {
        return authService.saveUser(user);
    }

    @PostMapping("/token")
    public String getToken(@RequestBody AuthRequestDto authRequestDto) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequestDto.getUsername(), authRequestDto.getPassword()
                ));
        if(authenticate.isAuthenticated()) {
           return authService.generateToken(authRequestDto.getUsername());
        } else {
            throw new RuntimeException("invalid access");
        }
    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        authService.validateToken(token);
        return "token is validated";
    }
}
