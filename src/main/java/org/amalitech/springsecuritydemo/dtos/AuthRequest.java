package org.amalitech.springsecuritydemo.dtos;

public record AuthRequest(
        String username,
        String password
) {
}