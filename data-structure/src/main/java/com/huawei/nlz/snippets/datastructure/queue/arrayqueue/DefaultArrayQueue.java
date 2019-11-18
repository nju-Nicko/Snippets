package com.huawei.nlz.snippets.datastructure.queue.arrayqueue;

import com.huawei.nlz.snippets.datastructure.queue.Queue;

import java.util.Arrays;

/**
 * 基于数组的队列的简易实现。通过数组存储元素，同时实时维护当前元素数目size，当入队时就是往index: size上添加;
 * 当出队时则将1~size-1的所有元素左移，同时将size-1位置赋null以让GC回收这部分对象空间。
 * 入队O(1)，出队时需要O(N)的移动操作。
 *
 * @param <E> 元素类型
 */
public class DefaultArrayQueue<E> implements Queue<E> {
    private Object[] queue;
    private int size;
    private int maxCapacity;    // 最大容量

    public DefaultArrayQueue(int maxCapacity) {
        this.maxCapacity = maxCapacity;
        queue = new Object[maxCapacity];
    }

    public void add(E data) {
        if (null == data) {
            throw new IllegalArgumentException("添加的值为null");
        }
        if (!isFull()) {
            queue[size] = data;
            size++;
        }
    }

    public E poll() {
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

    public E peek() {
        if (!isEmpty()) {
            return (E) queue[0];
        }
        return null;
    }

    public boolean isFull() {
        return size == maxCapacity;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    @Override
    public String toString() {
        return Arrays.toString(queue);
    }
}
