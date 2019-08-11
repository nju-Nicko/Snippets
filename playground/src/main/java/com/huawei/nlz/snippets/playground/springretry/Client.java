package com.huawei.nlz.snippets.playground.springretry;

import com.alibaba.fastjson.JSON;

public class Client {

    public static void main(String[] args) {
        BizLogic bizLogic = new BizLogic();
        RpcResponse rpcResponse = bizLogic.rpcWithRetry();
        System.out.println("最终客户端取到的rpc调用结果是: " + JSON.toJSONString(rpcResponse));
    }

}
