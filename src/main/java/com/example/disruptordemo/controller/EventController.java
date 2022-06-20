package com.example.disruptordemo.controller;

import com.example.disruptordemo.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import reactor.core.publisher.Mono;

import java.util.concurrent.ForkJoinPool;

@RestController
@RequestMapping("/")
public class EventController {

    Logger logger = LoggerFactory.getLogger(EventController.class);

    @Autowired
    private EventService service;

    @PostMapping
    public void onEvent(@RequestBody String payload){
        service.onEvent(payload);
    }

    @GetMapping("/mono")
    public Mono<String> mono(@RequestBody String payload) throws Exception{
        String result = service.process(payload);
        return Mono.just(result);
    }
}
