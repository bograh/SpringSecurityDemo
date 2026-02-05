package org.amalitech.springsecuritydemo.service;

import jakarta.servlet.http.HttpServletRequest;
import org.amalitech.springsecuritydemo.dtos.UserResponse;
import org.amalitech.springsecuritydemo.entity.User;
import org.amalitech.springsecuritydemo.repository.UserRepository;
import org.amalitech.springsecuritydemo.security.JwtService;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public UserResponse getUser(HttpServletRequest request) {
        String username = jwtService.extractUsernameFromTokenHeader(request);
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new RuntimeException("User not found")
        );
        return new UserResponse(
                String.valueOf(user.getId()),
                user.getName(),
                user.getUsername()
        );
    }

}