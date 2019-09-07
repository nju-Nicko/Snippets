package com.huawei.nlz.snippets.playground.stopwatch.spring;

import org.springframework.util.StopWatch;

import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        // 创建一个新的秒表对象
        StopWatch stopwatch = new StopWatch("计时器");
        // Spring的StopWatch内部没有多线程并发问题处理，所以只能在线程安全的场景下使用，否则就会出问题。
        // 开始一个新的计时任务
        stopwatch.start("扫地");
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 结束当前任务的计时
        stopwatch.stop();
        // getLastTaskName()返回最近结束的任务的名字，getLastTaskTimeMillis()返回最近结束的任务所花费的毫秒数
        System.out.println(stopwatch.getLastTaskName() + "花了" + stopwatch.getLastTaskTimeMillis() / 1000 + "秒");

        stopwatch.start("烧开水");
        try {
            TimeUnit.SECONDS.sleep(8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        stopwatch.stop();
        System.out.println(stopwatch.getLastTaskName() + "花了" + stopwatch.getLastTaskTimeMillis() / 1000 + "秒");

        // getTotalTimeMillis()返回所有任务总耗时毫秒数
        System.out.println("所有任务总共花了" + stopwatch.getTotalTimeMillis() / 1000 + "秒");
        // 打印所有任务的耗时及耗时百分比等信息
        System.out.println(stopwatch.prettyPrint());
    }

}
