package com.huawei.nlz.snippets.playground.guavaeventbus;

import lombok.Data;

@Data
public class CallEvent extends Event {
    private String callId;
}
