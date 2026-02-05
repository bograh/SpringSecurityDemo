package org.amalitech.springsecuritydemo.dtos;

public record ApiResponse<T>(
        String message,
        Boolean success,
        T data
) {
}