package com.example.disruptordemo.disruptor.handler;

import com.example.disruptordemo.disruptor.EventCache;
import com.example.disruptordemo.disruptor.event.MyEvent;
import com.example.disruptordemo.disruptor.event.MyEventType;
import com.lmax.disruptor.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
@Component
public class MyFutureEventHandler implements EventHandler<MyEvent> {
    Logger logger = LoggerFactory.getLogger(MyFutureEventHandler.class);
    @Autowired
    private EventCache cache;
    private List<MyEventType> supportedEventTypes = Arrays.asList(MyEventType.ASYNC);
    private ExecutorService executor = Executors.newFixedThreadPool(10);
    @Override
    public void onEvent(MyEvent event, long l, boolean b) throws Exception {
        if (!supportedEventTypes.contains(event.getEventType()))
            return;
        logger.info("Processing event:{}",event);
        FutureTask task = cache.getFutureTask(event);
        executor.submit(task);
        cache.clear(event);
    }
}
