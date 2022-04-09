package com.dev.reactive.dto;

import lombok.Data;

@Data
public class PasswordResetRequestDto {
    private String phoneNumber;
    private String username;
    private String oneTimePassword;
}
