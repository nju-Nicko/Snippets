package com.huawei.nlz.snippets.datastructure.stack.arraystack;

import java.util.Arrays;

/**
 * 基于数组实现栈
 *
 * @param <E> 栈元素类型
 */
public class ArrayStack<E> {
    private Object[] stack;    // 支撑数组
    private int top;    // 栈顶指针
    private int maxCapacity;    // 栈的最大容量

    /**
     * 创建一个具有默认最大容量的数组栈
     */
    public ArrayStack() {
        this(10);
    }

    /**
     * 创建一个具有指定最大容量的数组栈
     *
     * @param maxCapacity 最大容量
     */
    public ArrayStack(int maxCapacity) {
        this.stack = new Object[maxCapacity];
        this.top = -1;
        this.maxCapacity = maxCapacity;
    }

    /**
     * 栈是否为空
     *
     * @return true 为空; false 不为空
     */
    public boolean isEmpty() {
        return top == -1;
    }

    /**
     * 出栈
     *
     * @return 栈顶元素
     */
    @SuppressWarnings("unchecked")
    public E pop() {
        if (top == -1) {
            throw new RuntimeException("栈为空...");
        }
        E element = (E) stack[top];
        stack[top--] = null;    // 使得gc可以删除该元素，避免内存泄漏
        return element;
    }

    /**
     * 元素压栈
     *
     * @param e 元素
     */
    public void push(E e) {
        if (top == maxCapacity - 1) {
            throw new RuntimeException("栈已满...");
        }
        stack[++top] = e;
    }

    @Override
    public String toString() {
        return Arrays.toString(stack);
    }
}
