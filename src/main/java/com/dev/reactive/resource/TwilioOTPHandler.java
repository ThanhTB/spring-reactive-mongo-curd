package com.dev.reactive.resource;

import com.dev.reactive.dto.PasswordResetRequestDto;
import com.dev.reactive.service.TwilioOTPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class TwilioOTPHandler {
    @Autowired
    private TwilioOTPService service;

    public Mono<ServerResponse> sendOTP(ServerRequest request) {
        return request
                .bodyToMono(PasswordResetRequestDto.class)
                .flatMap(dto -> service.sendOTPForPassword(dto))
                .flatMap(dto -> ServerResponse.status(HttpStatus.OK).body(BodyInserters.fromValue(dto)));
    }

    public Mono<ServerResponse> validateOTP(ServerRequest request) {
        return request
                .bodyToMono(PasswordResetRequestDto.class)
                .flatMap(dto -> service.validateOTP(dto.getUsername(), dto.getOneTimePassword()))
                .flatMap(dto -> ServerResponse.status(HttpStatus.OK).bodyValue(dto));
    }
}
