package com.huawei.nlz.snippets.datastructure.queue.linkedlistqueue;

import com.huawei.nlz.snippets.datastructure.queue.Queue;

/**
 * 基于链表的队列实现。
 *
 * @param <E> 元素类型
 */
public class LinkedListQueue<E> implements Queue<E> {
    private QueueNode<E> head;  // 头指针
    private QueueNode<E> rear;   // 尾指针
    private int size;   // 队列大小

    public LinkedListQueue() {
        head = rear = new QueueNode<E>(null);  // 一个位于最前方的哨兵节点，初始状态下head和rear都指向它。后续的增删操作会改变rear，但head永远指向该哨兵。
    }

    @Override
    public void add(E data) {
        if (null == data) {
            throw new IllegalArgumentException("添加的值为null");
        }
        QueueNode<E> node = new QueueNode<>(data);
        rear.next = node;
        rear = node;
        size++;
    }

    @Override
    public E poll() {
        if (!isEmpty()) {
            E e = null;
            QueueNode<E> temp = head.next;
            head.next = temp.next;
            e = temp.data;
            temp = null;
            size--;
            return e;
        }
        return null;
    }

    @Override
    public E peek() {
        if (!isEmpty()) {
            return head.next.data;
        }
        return null;
    }

    @Override
    public boolean isFull() {
        // 内存够就可以一直添加元素。
        return false;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public String toString() {
        QueueNode<E> cur = head.next;
        StringBuilder sb = new StringBuilder();
        while (cur != null) {
            sb.append(cur.data).append(" ");
            cur = cur.next;
        }
        return sb.toString();
    }
}
