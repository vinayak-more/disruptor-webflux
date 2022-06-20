package com.example.disruptordemo.disruptor.service;

import com.example.disruptordemo.disruptor.EventCache;
import com.example.disruptordemo.disruptor.event.MyEvent;

import java.util.concurrent.Callable;

public class MyEventCallable<T> implements Callable<T> {
    private MyEvent event;

    public MyEventCallable(MyEvent event) {
        this.event = event;
    }

    @Override
    public T call() throws Exception {
        return (T) event.getResponsePayload();
    }
}
