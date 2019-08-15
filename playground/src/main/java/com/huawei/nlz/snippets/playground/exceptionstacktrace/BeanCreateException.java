package com.huawei.nlz.snippets.playground.exceptionstacktrace;

/**
 * bean创建异常
 */
public class BeanCreateException extends Exception {

    public BeanCreateException(String message) {
        super(message);
    }

    public BeanCreateException() {
        super();
    }

    public BeanCreateException(Throwable cause) {
        super(cause);
    }

    public BeanCreateException(String message, Throwable cause) {
        super(message, cause);
    }

}
