package com.huawei.nlz.snippets.playground.eventbus.guava;

import com.alibaba.fastjson.JSON;
import com.google.common.eventbus.Subscribe;

public class ListenerA {

    @Subscribe
    public void handle(Event event) {
        System.out.println("listenerA begin to process " + JSON.toJSONString(event));  // 会被打印
    }

    @Subscribe
    public void handle(Object obj) {
        System.out.println("listenerA's handle(Object) is called, obj is " + JSON.toJSONString(obj)); // 会被打印
    }

    @Subscribe
    public void handle(CallEvent callEvent) {
        System.out.println("listenerA's handle(CallEvent) is called, callEvent is " + JSON.toJSONString(callEvent)); // 不会被打印
    }

    public void handle1(Event event) {
        System.out.println("listenerA's handle1 is called."); // 不会被打印
    }
}
