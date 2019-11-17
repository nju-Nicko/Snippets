package com.huawei.nlz.snippets.datastructure.queue.linkedlistqueue;

/**
 * 队列节点类
 *
 * @param <T> 节点元素类型
 */
public class QueueNode<T> {

    QueueNode<T> next;
    T data;

    /**
     * 构造一个新节点
     *
     * @param data 新元素数据
     */
    QueueNode(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return data.toString();
    }
}
