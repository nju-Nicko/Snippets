package com.huawei.nlz.snippets.datastructure.list.linkedlist;

/**
 * 链表节点类
 *
 * @param <T> 元素类型
 */
class ListNode<T> {
    T data;

    ListNode<T> next;

    /**
     * 构造函数，创建新的链表节点
     *
     * @param data 新元素数据
     */
    ListNode(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return data.toString();
    }
}
