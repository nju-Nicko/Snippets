package com.huawei.nlz.snippets.playground.guavaeventbus;

import com.alibaba.fastjson.JSON;
import com.google.common.eventbus.Subscribe;

public class ListenerB {

    @Subscribe
    public void handle(Event event) {
        System.out.println("listenerB begin to process " + JSON.toJSONString(event)); // 会被打印
    }

}
