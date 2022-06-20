package com.example.disruptordemo.disruptor.service;

import com.example.disruptordemo.disruptor.EventCache;
import com.example.disruptordemo.disruptor.event.MyEvent;
import com.example.disruptordemo.disruptor.event.MyEventFactory;
import com.example.disruptordemo.disruptor.event.MyEventType;
import com.example.disruptordemo.disruptor.handler.MyEventHandler;
import com.example.disruptordemo.disruptor.handler.MyFutureEventHandler;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class MyDisruptorService {
    @Autowired
    private MyEventFactory eventFactory;

    @Autowired
    private MyEventHandler eventHandler;
    @Autowired
    private EventCache eventCache;

    @Autowired
    private MyFutureEventHandler futureEventHandler;

    @Autowired
    private EventCache cache;

    private Disruptor<MyEvent> disruptor;

    public void submitEvent(String payload) {
        disruptor.publishEvent((myEvent, l) -> {
            myEvent.setPayload(payload);
            myEvent.setSequence(l);
        });
    }

    public MyEvent submitAsync(String payload) {
        AtomicReference<MyEvent> result = new AtomicReference<>();
        disruptor.publishEvent((myEvent, sequence) -> {
            result.set(myEvent);
            myEvent.setEventType(MyEventType.ASYNC);
            myEvent.setPayload(payload);
            myEvent.setSequence(sequence);
        });
        return result.get();
    }

    @PostConstruct
    public void init() {
        disruptor = new Disruptor<MyEvent>(eventFactory, 1024, DaemonThreadFactory.INSTANCE);
        disruptor.handleEventsWith(eventHandler)
                .then(futureEventHandler);
        disruptor.start();
    }

    @PreDestroy
    public void tearDown() {
        disruptor.shutdown();
    }

    public Future<String> submitFutureEvent(String payload) {
        MyEvent event = submitAsync(payload);
        FutureTask<String> futureTask = new FutureTask<>(new MyEventCallable<>(event));
        eventCache.putFutureTask(event,futureTask);
        return futureTask;
    }


}
