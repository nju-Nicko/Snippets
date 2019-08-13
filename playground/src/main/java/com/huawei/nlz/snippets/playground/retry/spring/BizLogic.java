package com.huawei.nlz.snippets.playground.retry.spring;

import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import java.util.Collections;
import java.util.Random;

/**
 * 业务逻辑类
 */
public class BizLogic {

    public RpcResponse rpcWithRetry() {
        // 创建RetryTemplate实例
        RetryTemplate retryTemplate = new RetryTemplate();

        // 创建重试策略
        // SimpleRetryPolicy构造器第一个参数表示最大重试次数，第二个参数表示需要重试的异常，必须是Throwable的子类
        RetryPolicy simpleRetryPolicy = new SimpleRetryPolicy(
                5, Collections.singletonMap(Exception.class, true));

        // 两次重试之间的回避策略，这里使用了固定时间退避策略
        FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
        fixedBackOffPolicy.setBackOffPeriod(3000);

        retryTemplate.setRetryPolicy(simpleRetryPolicy);
        retryTemplate.setBackOffPolicy(fixedBackOffPolicy);

        // 通过RetryCallback重试回调实例包装正常逻辑逻辑，第一次执行和重试执行执行的都是这段逻辑
        // RetryCallback第一个泛型参数是doWithRetry的返回对象类型，第二个泛型参数是doWithRetry的抛出异常的类型
        RetryCallback<RpcResponse, Exception> retryCallback = context -> {
            System.out.println("执行重试逻辑！！");
            RpcResponse rpcResponse = rpc();
            if (rpcResponse.getResultCode() != 0) {
                // 这边必须要转化为异常抛出，否则不会重试
                throw new RuntimeException("rpc调用业务异常，业务结果码是" + rpcResponse.getResultCode());
            }
            return rpcResponse;
        };

        // 当重试超过最大重试时间或最大重试次数后可以调用RecoveryCallback进行恢复，比如返回假数据或托底数据
        // RecoverCallback的泛型参数是recover的返回对象类型
        RecoveryCallback<RpcResponse> recoveryCallback = context -> {
            System.out.println("执行恢复逻辑！！");
            return new RpcResponse(13, "rpc调用失败!!");
        };

        RpcResponse rpcResponse = new RpcResponse(13, "rpc调用失败!!");

        try {
            rpcResponse = retryTemplate.execute(retryCallback, recoveryCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rpcResponse;
    }

    /**
     * 表示执行正常逻辑处理的方法，有可能会抛异常，也有可能会返回业务失败。
     */
    public RpcResponse rpc() {
        System.out.println("调用rpc()!!");
        Random random = new Random();
        int a = random.nextInt(10);
        if (a == 8) {
            throw new RuntimeException("远程调用失败，连接服务器失败!!");
        } else if (a == 9) {
            return new RpcResponse(1, "远程调用失败，远程座席未迁入!!");
        }
        return new RpcResponse(0, "远程调用成功!!");
    }

}
