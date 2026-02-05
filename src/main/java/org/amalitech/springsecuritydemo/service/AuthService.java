package org.amalitech.springsecuritydemo.service;

import org.amalitech.springsecuritydemo.dtos.AuthRequest;
import org.amalitech.springsecuritydemo.dtos.AuthResponse;
import org.amalitech.springsecuritydemo.entity.User;
import org.amalitech.springsecuritydemo.repository.UserRepository;
import org.amalitech.springsecuritydemo.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }


    public AuthResponse loginUser(AuthRequest authRequest) {
        String username = authRequest.username();
        String password = authRequest.password();

        User user = userRepository.findByUsernameIgnoreCase(username).orElseThrow(
                () -> new RuntimeException("Login failed. User not found")
        );

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtService.generateToken(authentication);

        return new AuthResponse("Login successful", username, token);
    }

}