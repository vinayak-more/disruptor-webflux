package com.example.disruptordemo.service;

import com.example.disruptordemo.disruptor.EventCache;
import com.example.disruptordemo.disruptor.event.MyEvent;
import com.example.disruptordemo.disruptor.service.MyDisruptorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.Future;

@Service
public class EventService {

    @Autowired
    private MyDisruptorService disruptorService;

    @Autowired
    private EventCache eventCache;

    public void onEvent(String payload){
        disruptorService.submitEvent(payload);
    }

    public String process(String payload) throws Exception {
        DeferredResult<ResponseEntity<String>> result = new DeferredResult<>(30000L);
        Future<String> resultFuture = disruptorService.submitFutureEvent(payload);
        return resultFuture.get();
    }
}
