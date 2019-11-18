package com.huawei.nlz.snippets.datastructure.queue.stackqueue;

import com.huawei.nlz.snippets.datastructure.queue.Queue;
import com.huawei.nlz.snippets.datastructure.stack.linkedliststack.LinkedListStack;

/**
 * 基于栈实现的队列
 * <p>
 * 思路：
 * 使用两个栈stack1和stack2。
 * 入队列时，将元素压入stack1；
 * 出队列时，先判断stack2是否有元素，若有，则stack2 pop；若没有，则把stack1当前的元素依次弹栈并压入stack2，然后stack2弹栈。
 *
 * @param <E> 元素类型
 */
public class StackQueue<E> implements Queue<E> {
    private LinkedListStack<E> stack1; // 入队栈
    private LinkedListStack<E> stack2; // 出队栈

    public StackQueue() {
        stack1 = new LinkedListStack<>();
        stack2 = new LinkedListStack<>();
    }

    public void add(E e) {
        if (null == e) {
            throw new IllegalArgumentException("添加的值为null");
        }
        stack1.push(e);
    }

    public E poll() {
        if (stack2.isEmpty()) {
            while (!stack1.isEmpty()) {
                stack2.push(stack1.pop());
            }
        }
        return stack2.pop();
    }

    public E peek() {
        if (stack2.isEmpty()) {
            while (!stack1.isEmpty()) {
                stack2.push(stack1.pop());
            }
        }
        return stack2.peek();
    }

    public boolean isEmpty() {
        return stack1.isEmpty() && stack2.isEmpty();
    }

    public boolean isFull() {
        return false;  // 基于的链表式栈，内存够就可以一直入队列
    }

    public int size() {
        return stack1.size() + stack2.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (!stack2.isEmpty()) {
            sb.append(stack2.toString());
        }
        return sb.append(
                new StringBuilder(stack1.toString()).reverse().toString())
                .toString();
    }
}
