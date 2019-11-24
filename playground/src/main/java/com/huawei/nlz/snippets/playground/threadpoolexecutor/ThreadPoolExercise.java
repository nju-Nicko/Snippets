package com.huawei.nlz.snippets.playground.threadpoolexecutor;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExercise {
    public static void main(String[] args) {
        /*testPrestartCoreThread();
        testKeepAliveTime();
        testRejectedExecutionHandler_AbortPolicy();
        testRejectedExecutionHandler_CallerRunsPolicy();*/
        testRejectedExecutionHandler_DiscardPolicy();
    }

    public static void testPrestartCoreThread() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 4,
                1, TimeUnit.MINUTES, new LinkedBlockingQueue<>());
        threadPoolExecutor.execute(() -> {
            System.out.println("running");
        });
        /*
         * prestartCoreThread()会预先启动一个核心线程，
         * 如果所有的核心线程都已经启动，就返回false；否则返回true。
         * 这里当corePoolSize为1的时候，prestartCoreThread()会返回false，大于1的时候，返回true。
         *
         * prestartAllCoreThreads()，会预先启动所有核心线程，并返回实际启动的线程数。
         **/
        System.out.println(threadPoolExecutor.prestartCoreThread());
        System.out.println(threadPoolExecutor.prestartAllCoreThreads());
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (Exception e) {
            e.printStackTrace();
        }
        threadPoolExecutor.shutdown();

        /*
         * 输出：
         * false
         * 0
         * running
         */
    }

    public static void testKeepAliveTime() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 4,
                10, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1));
        threadPoolExecutor.execute(() -> {
            System.out.println("第一个任务的线程号是: " + Thread.currentThread().getId());
            try {
                // 这边睡眠2秒钟，确保第二个任务会进阻塞队列。
                TimeUnit.SECONDS.sleep(2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        try {
            // 这边睡眠1秒钟，确保上一步的任务已经执行。
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        threadPoolExecutor.execute(() -> {
            System.out.println("第二个任务的线程号是: " + Thread.currentThread().getId());
        });
        threadPoolExecutor.execute(() -> {
            System.out.println("第三个任务的线程号是: " + Thread.currentThread().getId());
            try {
                // 这边睡眠5秒钟，确保比一开始的线程晚空闲。
                TimeUnit.SECONDS.sleep(5);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        try {
            // 这边睡眠20秒钟，确保所有的超过空闲时间的线程被终止。
            TimeUnit.SECONDS.sleep(20);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 再提交一笔任务，看看是哪个线程在执行它。
        threadPoolExecutor.execute(() -> {
            System.out.println("第四个任务的线程号是: " + Thread.currentThread().getId());
        });

        /*
         * 输出：
         * 第一个任务的线程号是：10
         * 第三个任务的线程号是：11
         * 第二个任务的线程号是：10
         * 第四个任务的线程号是：11
         *
         * 可见线程池会终止空闲时间超过keepAliveTime时间的线程直到线程数<=corePoolSize。
         * 被终止的线程可能是corePoolSize未满时创建的线程，也可能是corePoolSize和阻塞队列都慢了，但是最大大小没满时创建的线程。
         */
    }

    public static void testRejectedExecutionHandler_AbortPolicy() {
        // 抛出RejectedExecutionException
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1,
                1, TimeUnit.MINUTES, new LinkedBlockingQueue<>(1), new ThreadPoolExecutor.AbortPolicy());
        threadPoolExecutor.execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        threadPoolExecutor.execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        threadPoolExecutor.execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static void testRejectedExecutionHandler_CallerRunsPolicy() {
        // 超出的maximumPoolSize的任务由调用线程串行执行。

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1,
                1, TimeUnit.MINUTES, new LinkedBlockingQueue<>(1), new ThreadPoolExecutor.CallerRunsPolicy());
        threadPoolExecutor.execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        threadPoolExecutor.execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        threadPoolExecutor.execute(() -> {
            System.out.println(Thread.currentThread().getName() + " " + System.currentTimeMillis());
        });
        threadPoolExecutor.execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " " + System.currentTimeMillis());
        });
        /*
         * 输出：
         *
         * main 1574610418023
         * main 1574610428024
         *
         *
         * 可以看到两次的时间打印相差10秒。
         */
    }

    public static void testRejectedExecutionHandler_DiscardPolicy() {
        // 超出maximumPoolSize的任务直接丢弃，不抛异常。

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1,
                1, TimeUnit.MINUTES, new LinkedBlockingQueue<>(1), new ThreadPoolExecutor.DiscardPolicy());
        threadPoolExecutor.execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        threadPoolExecutor.execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        threadPoolExecutor.execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static void testRejectedExecutionHandler_DiscardOldestPolicy() {
        // ...
    }

}
