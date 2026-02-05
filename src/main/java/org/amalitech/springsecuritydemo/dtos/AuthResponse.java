package org.amalitech.springsecuritydemo.dtos;

public record AuthResponse(
        String message,
        String username,
        String token
) {
}