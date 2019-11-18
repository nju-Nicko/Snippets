package com.huawei.nlz.snippets.datastructure.queue;

/**
 * 队列数据结构接口规格
 * @param <E> 元素类型
 */
public interface Queue<E> {
    /**
     * 添加元素到队尾
     *
     * @param data 数据
     */
    void add(E data);

    /**
     * 删除队头并返回队头元素的值
     *
     * @return 头元素的值
     */
    E poll();

    /**
     * 返回队头元素的值但不移除元素
     *
     * @return 头元素的值
     */
    E peek();

    /**
     * 队列是否已满
     *
     * @return true 已满; false 未满
     */
    boolean isFull();

    /**
     * 队列是否为空
     *
     * @return true 为空; false 不为空
     */
    boolean isEmpty();

    /**
     * 队列大小
     *
     * @return 队列大小
     */
    int size();
}
