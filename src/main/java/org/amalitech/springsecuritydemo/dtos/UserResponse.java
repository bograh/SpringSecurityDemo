package org.amalitech.springsecuritydemo.dtos;

public record UserResponse(
        String id,
        String name,
        String username
) {
}