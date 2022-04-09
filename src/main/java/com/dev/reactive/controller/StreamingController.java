package com.dev.reactive.controller;

import com.dev.reactive.service.StreamingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/video")
public class StreamingController {
    @Autowired
    private StreamingService service;

    @GetMapping(value = "/{title}", produces = "video/mp4")
    public Mono<Resource> getVideo(@RequestHeader("Range") String range, @PathVariable String title) {
        System.out.println("ranges in byte: " + range);
        return service.getVideo(title);
    }
}
