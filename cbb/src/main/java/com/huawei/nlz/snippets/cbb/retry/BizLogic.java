package com.huawei.nlz.snippets.cbb.retry;

import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * 业务逻辑组件
 */
@Component
@Slf4j
@EnableRetry  // 注意要开启@EnableRetry，这个注解可以放在组件类上，也可以放在Configuration类上。
public class BizLogic {

    /**
     * 表示执行正常逻辑处理的方法，有可能会抛异常，也有可能会返回业务失败。
     */
    /*
     * @Retryable的参数说明:
     * •value：抛出指定异常才会重试
     * •include：和value一样，默认为空，当exclude也为空时，所有异常都会重试
     * •exclude：指定不处理的异常
     * •maxAttempts：最大重试次数，默认3次
     * •backoff：重试等待策略，默认使用@Backoff，@Backoff的value默认为1000L，我们设置为2000L；multiplier（指定延迟倍数）默认为0。
     */
    @Retryable(value = Exception.class, maxAttempts = 5, backoff = @Backoff(delay = 2000L, multiplier = 1.5))
    public RpcResponse rpc() {
        log.info("开始执行rpc调用!!");
        Random random = new Random();
        int a = random.nextInt(10);
        if (a == 8) {
            log.info("远程调用失败，连接服务器失败!!");
            // 模拟一个调http失败的场景，抛出ConnectionException之类的。
            throw new RuntimeException("远程调用失败，连接服务器失败!!");
        } else if (a == 9) {
            log.info("远程调用失败，远程座席未迁入!!");
            // 注意这里业务失败要转化未异常抛出，否则不会重试。
            throw new RuntimeException("远程调用失败，远程座席未迁入!!");
        }
        log.info("远程调用成功!!");
        return new RpcResponse(0, "远程调用成功!!");
    }

    @Recover
    public RpcResponse recover(Exception e) {
        log.info("执行恢复逻辑!!重试上下文里的异常信息是: {}", e.getMessage());
        return new RpcResponse(13, "rpc调用失败!!");
    }
}
