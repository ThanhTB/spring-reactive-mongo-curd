package com.dev.reactive;

import com.dev.reactive.config.TwilioConfig;
import com.twilio.Twilio;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
        title = "Spring Webflux CURD example",
        version = "1.0",
        description = "demo swagger in spring webflux"
))
public class SpringReactiveMongoCurdApplication {

    @Autowired
    private TwilioConfig twilioConfig;

    public static void main(String[] args) {
        SpringApplication.run(SpringReactiveMongoCurdApplication.class, args);
    }

    @PostConstruct
    public void initTwilio() {
        Twilio.init(twilioConfig.getAccountSid(), twilioConfig.getAuthToken());
    }

}
