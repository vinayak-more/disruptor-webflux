package com.example.disruptordemo.disruptor.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;
@Getter
@Setter
@ToString
@NoArgsConstructor
public class MyEvent {
    private MyEventType eventType;
    private String payload;
    private long sequence;
    private String responsePayload;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyEvent event = (MyEvent) o;
        return sequence == event.sequence && Objects.equals(payload, event.payload);
    }

    @Override
    public int hashCode() {
        return Objects.hash(payload, sequence);
    }

}
