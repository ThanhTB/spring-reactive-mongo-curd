package com.dev.reactive.dto;

import com.dev.reactive.constants.OtpStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetResponseDto {
    private OtpStatus status;
    private String message;
}
