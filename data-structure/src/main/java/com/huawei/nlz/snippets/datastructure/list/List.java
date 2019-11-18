package com.huawei.nlz.snippets.datastructure.list;

/**
 * 链表数据结构接口规格
 *
 * @param <E> 元素类型
 */
public interface List<E> {
    /**
     * 向链表中指定位置插入元素
     *
     * @param data  元素
     * @param index 待插入的索引
     */
    void add(E data, int index);

    /**
     * 向链表末尾添加元素
     *
     * @param data 元素
     */
    void add(E data);

    /**
     * 获取index位置的元素
     *
     * @param index 索引
     * @return 元素
     */
    E get(int index);

    /**
     * 删除链表中指定位置的元素
     *
     * @param index 索引
     * @return 被删除的元素
     */
    E remove(int index);

    /**
     * 删除头部并返回首元素
     *
     * @return 首元素
     */
    E remove();

    /**
     * 判断单链表是否为空
     *
     * @return true 为空; false 不为空
     */
    boolean isEmpty();

    /**
     * 返回链表的长度
     *
     * @return 链表长度
     */
    int size();
}
