package com.huawei.nlz.snippets.cbb.asynctask;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Data
@Slf4j
public class BizRunnable implements Runnable {

    private String backgroundTaskId;
    private Object bizParam0;
    private Object bizParam1;

    @Override
    public void run() {
        Thread.currentThread().setName(Constants.THREAD_NAME_PREFIX + backgroundTaskId);
        // 执行任务。
    }

}
