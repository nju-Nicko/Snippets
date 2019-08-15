package com.huawei.nlz.snippets.playground.exceptionstacktrace;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

    public static void main(String[] args) {
        try {
            wire();
        } catch (Exception e) {
            log.error("注入失败。", e);
        }

        /*
         * 输出如下：
         * 01:08:32.635 [main] ERROR c.h.n.s.p.exceptionstacktrace.Main - 注入失败。
           com.huawei.nlz.snippets.playground.exceptionstacktrace.BeanWireException: 注入bean失败，创建bean失败！
	           at com.huawei.nlz.snippets.playground.exceptionstacktrace.Main.wire(Main.java:23)
	           at com.huawei.nlz.snippets.playground.exceptionstacktrace.Main.main(Main.java:10)
           Caused by: com.huawei.nlz.snippets.playground.exceptionstacktrace.BeanCreateException: 创建bean失败，数字格式错误！
	           at com.huawei.nlz.snippets.playground.exceptionstacktrace.Main.createBean(Main.java:35)
	           at com.huawei.nlz.snippets.playground.exceptionstacktrace.Main.wire(Main.java:21)
	           ... 1 common frames omitted
           Caused by: java.lang.NumberFormatException: 数字格式错误，'123abc'是非法的数字格式！
	           at com.huawei.nlz.snippets.playground.exceptionstacktrace.Main.createBean(Main.java:33)
	           ... 2 common frames omitted
         */
    }

    /**
     * 模拟bean的注入
     */
    public static void wire() throws BeanWireException {
        try {
            createBean();
        } catch (BeanCreateException e) {
            throw new BeanWireException("注入bean失败，创建bean失败！", e);
        }
    }

    /**
     * 模拟bean的创建
     */
    public static void createBean() throws BeanCreateException {
        try {
            // 模拟在bean创建过程中由于数字格式错误抛了一个NumberFormatException.
            throw new NumberFormatException("数字格式错误，'123abc'是非法的数字格式！");
        } catch (NumberFormatException e) {
            throw new BeanCreateException("创建bean失败，数字格式错误！", e);
        }
    }

}
