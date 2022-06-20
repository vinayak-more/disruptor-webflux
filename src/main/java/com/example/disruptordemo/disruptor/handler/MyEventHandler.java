package com.example.disruptordemo.disruptor.handler;

import com.example.disruptordemo.disruptor.event.MyEvent;
import com.lmax.disruptor.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MyEventHandler implements EventHandler<MyEvent> {
    Logger logger = LoggerFactory.getLogger(MyEventHandler.class);
    @Override
    public void onEvent(MyEvent myEvent, long l, boolean b) throws Exception {
        logger.info("Processing event :{}",myEvent);
        myEvent.setResponsePayload(myEvent.getPayload().toUpperCase());
        Thread.sleep(500);
    }
}
