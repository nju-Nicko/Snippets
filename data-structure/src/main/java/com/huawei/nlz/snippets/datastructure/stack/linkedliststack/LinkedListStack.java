package com.huawei.nlz.snippets.datastructure.stack.linkedliststack;

import com.huawei.nlz.snippets.datastructure.list.linkedlist.LinkedList;

/**
 * 基于链表实现栈
 *
 * @param <E> 栈元素类型
 */
public class LinkedListStack<E> {
    // 用于数据保存的LinkedList
    private LinkedList<E> stack;

    /**
     * 构造函数
     */
    public LinkedListStack() {
        stack = new LinkedList<>();
    }

    /**
     * 栈是否为空
     *
     * @return true 为空; false 不为空
     */
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    /**
     * 压栈
     *
     * @param data 元素
     */
    public void push(E data) {
        stack.add(data);
    }

    /**
     * 出栈
     *
     * @return 栈顶元素
     */
    public E pop() {
        if (stack.isEmpty()) {
            throw new RuntimeException("栈为空...");
        }

        return stack.remove(stack.size() - 1);
    }

    @Override
    public String toString() {
        return stack.toString();
    }
}
