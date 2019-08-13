package com.huawei.nlz.snippets.playground.eventbus.guava;

import com.google.common.eventbus.EventBus;

public class Main {

    public static void main(String[] args) {
        EventBus eventBus = new EventBus();
        ListenerA listenerA = new ListenerA();
        ListenerB listenerB = new ListenerB();
        eventBus.register(listenerA);
        eventBus.register(listenerB);
        Event event = new Event();
        event.setEventId("osdfksdfsdf");
        eventBus.post(event);
    }

}
