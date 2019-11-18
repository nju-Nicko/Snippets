package com.huawei.nlz.snippets.datastructure.stack;

/**
 * 栈数据结构接口规格类
 *
 * @param <E> 元素类型
 */
public interface Stack<E> {
    /**
     * 查看堆栈顶部的对象，但不从堆栈中移除它
     *
     * @return 栈顶对象
     */
    E peek();

    /**
     * 栈是否为空
     *
     * @return true 为空; false 不为空
     */
    boolean isEmpty();

    /**
     * 栈是否已满
     *
     * @return true 已满; false 未满
     */
    boolean isFull();

    /**
     * 元素压栈
     *
     * @param e 元素
     */
    void push(E e);

    /**
     * 出栈
     *
     * @return 栈顶元素
     */
    E pop();

    /**
     * 栈大小
     *
     * @return 栈大小
     */
    int size();
}
