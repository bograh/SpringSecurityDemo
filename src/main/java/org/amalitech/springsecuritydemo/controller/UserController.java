package org.amalitech.springsecuritydemo.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.amalitech.springsecuritydemo.dtos.ApiResponse;
import org.amalitech.springsecuritydemo.dtos.UserResponse;
import org.amalitech.springsecuritydemo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<UserResponse>> getUser(HttpServletRequest request) {
        try {
            UserResponse userResponse = userService.getUser(request);
            ApiResponse<UserResponse> response = new ApiResponse<>("User retrieved successfully", true, userResponse);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<UserResponse> response = new ApiResponse<>("User profile retrieval failed", false, null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}