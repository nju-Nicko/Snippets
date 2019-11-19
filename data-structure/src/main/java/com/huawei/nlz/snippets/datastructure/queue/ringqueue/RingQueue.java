package com.huawei.nlz.snippets.datastructure.queue.ringqueue;

import com.huawei.nlz.snippets.datastructure.queue.Queue;

/**
 * 环形队列
 *
 * @param <E> 元素类型
 */
public class RingQueue<E> implements Queue<E> {

    private static final int DEFAULT_SIZE = 10;

    private int capacity; // 数组容量

    private Object[] elementData; // 定义一个数组用于保存环形队列的元素

    private int front; // 队头

    private int rear; // 队尾

    private int size; //队列大小

    /**
     * 创建具有默认最大容量的环形队列。
     */
    public RingQueue() {
        this(DEFAULT_SIZE);
    }

    /**
     * 创建具有指定最大容量的环形队列
     *
     * @param capacity 队列最大容量
     */
    public RingQueue(int capacity) {
        this.capacity = capacity;
        elementData = new Object[capacity];
        front = 0;
        rear = -1;
    }

    /**
     * 获取环形队列的大小
     *
     * @return 环形队列的大小
     */
    @Override
    public int size() {
        return size;
    }

    @Override
    public void add(E element) {
        if (null == element) {
            throw new IllegalArgumentException("添加的值为null");
        }
        if (size == capacity) {
            throw new IllegalStateException("队列已满");
        }

        // 移动rear，如果已经到末尾，那就转到头部。
        rear = (rear + 1) % capacity;
        elementData[rear] = element;
        ++size;
    }

    @Override
    public E poll() {
        if (isEmpty()) {
            return null;
        }

        E oldValue = (E) elementData[front];
        elementData[front] = null;
        front = (front + 1) % capacity;
        --size;
        return oldValue;
    }

    @Override
    public E peek() {
        if (isEmpty()) {
            return null;
        }
        return (E) elementData[front];
    }

    @Override
    public boolean isFull() {
        return size == capacity;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "[]";
        } else {
            if (front <= rear) {
                StringBuilder sb = new StringBuilder("[");
                for (int i = front; i <= rear; i++) {
                    sb.append(elementData[i].toString()).append(", ");
                }
                int len = sb.length();
                return sb.delete(len - 2, len).append("]").toString();
            } else {
                StringBuilder sb = new StringBuilder("[");
                for (int i = front; i < capacity; i++) {
                    sb.append(elementData[i].toString() + ", ");
                }
                for (int i = 0; i <= rear; i++) {
                    sb.append(elementData[i].toString() + ", ");
                }
                int len = sb.length();
                return sb.delete(len - 2, len).append("]").toString();
            }
        }
    }
}