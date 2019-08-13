package com.huawei.nlz.snippets.playground.eventbus.guava;

import lombok.Data;

@Data
public class CallEvent extends Event {
    private String callId;
}
