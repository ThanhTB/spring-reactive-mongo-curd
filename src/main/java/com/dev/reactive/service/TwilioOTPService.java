package com.dev.reactive.service;

import com.dev.reactive.config.TwilioConfig;
import com.dev.reactive.constants.OtpStatus;
import com.dev.reactive.dto.PasswordResetRequestDto;
import com.dev.reactive.dto.PasswordResetResponseDto;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class TwilioOTPService {
    Map<String, String> otpMap = new HashMap<>();
    @Autowired
    private TwilioConfig twilioConfig;

    public Mono<PasswordResetResponseDto> sendOTPForPassword(PasswordResetRequestDto passwordResetRequestDto) {
        PasswordResetResponseDto passwordResetResponseDto = null;
        try {
            PhoneNumber to = new PhoneNumber(passwordResetRequestDto.getPhoneNumber());
            PhoneNumber from = new PhoneNumber(twilioConfig.getTrialNumber());

            String otp = generateOTP();
            String otpMessage = "Dear " + passwordResetRequestDto.getUsername() + ", your OTP is ##" + otp + "##. Use this passcode to complete your transaction. Thanks you!";
            Message message = Message.creator(to, from, otpMessage).create();

            otpMap.put(passwordResetRequestDto.getUsername(), otp);
            passwordResetResponseDto = new PasswordResetResponseDto(OtpStatus.DELIVERED, otpMessage);
        } catch (Exception ex) {
            passwordResetResponseDto = new PasswordResetResponseDto(OtpStatus.FAILED, ex.getMessage());
        }

        return Mono.just(passwordResetResponseDto);
    }

    public Mono<String> validateOTP(String username, String userInputOtp) {
        if (userInputOtp.equals(otpMap.get(username))) {
            otpMap.remove(username, userInputOtp);
            return Mono.just("Valid OTP please proceed with your transaction!");
        }

        return Mono.error(new IllegalArgumentException("Invalid OTP please retry!"));
    }

    // 6 digit OTP
    private String generateOTP() {
        return new DecimalFormat("000000").format(new Random().nextInt(999999));
    }
}
