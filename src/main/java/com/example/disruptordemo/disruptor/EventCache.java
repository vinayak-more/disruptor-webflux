package com.example.disruptordemo.disruptor;

import com.example.disruptordemo.disruptor.event.MyEvent;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.FutureTask;

@Component
public class EventCache {

    private Map<MyEvent, FutureTask> futureByEventMap = new ConcurrentHashMap<>();

    public void clear(MyEvent event) {
        futureByEventMap.remove(event);
    }

    public void putFutureTask(MyEvent event, FutureTask task) {
        futureByEventMap.put(event, task);
    }

    public FutureTask getFutureTask(MyEvent event) {
        return futureByEventMap.get(event);
    }


}
