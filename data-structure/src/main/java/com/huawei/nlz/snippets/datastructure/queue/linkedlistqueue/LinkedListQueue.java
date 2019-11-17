package com.huawei.nlz.snippets.datastructure.queue.linkedlistqueue;

/**
 * 基于链表的队列实现。
 *
 * @param <E> 元素类型
 */
public class LinkedListQueue<E> {
    private QueueNode<E> head;  // 头指针
    private QueueNode<E> rear;   // 尾指针
    private int size;   // 队列大小

    public LinkedListQueue() {
        head = rear = new QueueNode<E>(null);  // 一个位于最前方的哨兵节点，初始状态下head和rear都指向它。后续的增删操作会改变rear，但head永远指向该哨兵。
    }

    /**
     * 添加元素到队尾
     *
     * @param data 数据
     * @throws IllegalArgumentException 如果添加null就抛出IllegalArgumentException
     */
    public void add(E data) {
        if (null == data) {
            throw new IllegalArgumentException("添加的值未null");
        }
        QueueNode<E> node = new QueueNode<>(data);
        rear.next = node;
        rear = node;
        size++;
    }

    /**
     * 删除队头并返回队头元素的值
     *
     * @return 头元素的值
     */
    public E remove() {
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

    /**
     * 返回队头元素的值
     *
     * @return 头元素的值
     */
    public E peek() {
        if (!isEmpty()) {
            return head.next.data;
        }
        return null;
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
        QueueNode<E> cur = head.next;
        StringBuilder sb = new StringBuilder();
        while (cur != null) {
            sb.append(cur.data).append(" ");
            cur = cur.next;
        }
        return sb.toString();
    }
}
