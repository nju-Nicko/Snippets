package com.huawei.nlz.snippets.playground.random;

import java.util.concurrent.ThreadLocalRandom;

public class RandomExercise {

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Player().start();
        }
    }

    private static class Player extends Thread {
        @Override
        public void run() {
            /*
             * 多线程下使用ThreadLocalRandom生成随机数性能比使用Random要好。
             * 需注意每个线程都要调用ThreadLocalRandom.current()，否则会生成重复的随机数。
             */
            System.out.println(getName() + ": " + ThreadLocalRandom.current().nextInt(100));
        }
    }

}
