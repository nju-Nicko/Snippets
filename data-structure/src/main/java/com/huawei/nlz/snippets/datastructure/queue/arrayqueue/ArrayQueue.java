package com.huawei.nlz.snippets.datastructure.queue.arrayqueue;

import java.util.Arrays;

/**
 * 基于数组的队列实现。
 *
 * @param <E> 元素类型
 */
public class ArrayQueue<E> {
    private Object[] queue;
    private int size;
    private int maxCapacity;    // 最大容量

    public ArrayQueue(int maxCapacity) {
        this.maxCapacity = maxCapacity;
        queue = new Object[maxCapacity];
    }

    /**
     * 添加元素到队尾
     *
     * @param data 数据
     */
    public void add(E data) {
        if (!isFull()) {
            queue[size] = data;
            size++;
        }
    }

    /**
     * 删除队头并返回队头元素的值
     *
     * @return 头元素的值
     */
    public E remove() {
        if (!isEmpty()) {
            E temp = (E) queue[0];
            for (int i = 0; i < size - 1; i++) {
                queue[i] = queue[i + 1];
            }
            queue[size - 1] = null;
            size--;
            return temp;
        }
        return null;
    }

    /**
     * 返回队头元素的值
     *
     * @return 头元素的值
     */
    public E peek() {
        if (!isEmpty()) {
            return (E) queue[0];
        }
        return null;
    }

    /**
     * 队列是否已满
     *
     * @return true 已满; false 未满
     */
    public boolean isFull() {
        return size == maxCapacity;
    }

    /**
     * 队列是否为空
     *
     * @return true 为空; false 不为空
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 队列大小
     *
     * @return 队列大小
     */
    public int size() {
        return size;
    }

    @Override
    public String toString() {
        return Arrays.toString(queue);
    }
}
