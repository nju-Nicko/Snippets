package com.huawei.nlz.snippets.datastructure.stack.linkedliststack;

import com.huawei.nlz.snippets.datastructure.list.linkedlist.LinkedList;
import com.huawei.nlz.snippets.datastructure.stack.Stack;

/**
 * 基于链表实现栈
 *
 * @param <E> 栈元素类型
 */
public class LinkedListStack<E> implements Stack<E> {
    // 用于数据保存的LinkedList
    private LinkedList<E> stack;

    /**
     * 构造函数
     */
    public LinkedListStack() {
        stack = new LinkedList<>();
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public boolean isFull() {
        return false;  // 只要内存够就可以一直压栈
    }

    @Override
    public void push(E data) {
        if (null == data) {
            throw new IllegalArgumentException("入栈的元素为null");
        }

        stack.add(data);
    }

    @Override
    public E peek() {
        return stack.get(stack.size() - 1);
    }

    @Override
    public E pop() {
        if (stack.isEmpty()) {
            throw new RuntimeException("栈为空...");
        }

        return stack.remove(stack.size() - 1);
    }

    @Override
    public int size() {
        return stack.size();
    }

    @Override
    public String toString() {
        return stack.toString();
    }
}
