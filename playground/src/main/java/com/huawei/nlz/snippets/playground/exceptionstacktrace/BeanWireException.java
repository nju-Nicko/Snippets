package com.huawei.nlz.snippets.playground.exceptionstacktrace;

public class BeanWireException extends Exception {
    public BeanWireException(String message) {
        super(message);
    }

    public BeanWireException() {
        super();
    }

    public BeanWireException(Throwable cause) {
        super(cause);
    }

    public BeanWireException(String message, Throwable cause) {
        super(message, cause);
    }
}
