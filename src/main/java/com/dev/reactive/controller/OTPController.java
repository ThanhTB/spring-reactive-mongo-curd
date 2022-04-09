package com.dev.reactive.controller;

import com.dev.reactive.resource.TwilioOTPHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/otp")
public class OTPController {
    @Autowired
    private TwilioOTPHandler handler;

    @PostMapping("/send")
    public Mono<ServerResponse> sendOTP(@RequestBody ServerRequest request) {
        return handler.sendOTP(request);
    }

    @PostMapping("/validate")
    public Mono<ServerResponse> validateOTP(@RequestBody ServerRequest request) {
        return handler.validateOTP(request);
    }
}
