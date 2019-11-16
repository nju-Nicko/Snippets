package com.huawei.nlz.snippets.datastructure.list.linkedlist;

/**
 * 基于指针的单链表，适用于线程安全的环境
 *
 * @param <E> 元素类型
 */
public class LinkedList<E> {
    private ListNode<E> head; // 链表表头，这是一个哑节点(dummy node)，用来简化代码对链表为空时的数据null的判定。

    private int size; // 链表大小

    public LinkedList() {
        head = new ListNode<>(null);
    }

    /**
     * 向链表中指定位置插入元素并返回新的节点
     *
     * @param data  元素
     * @param index 待插入的索引
     */
    public void add(E data, int index) {
        if (index > size) {
            throw new RuntimeException("超出范围...");
        }

        ListNode<E> cur = head;
        for (int i = 0; i < index; i++) {
            cur = cur.next;
        }
        ListNode<E> next = cur.next;
        ListNode<E> node = new ListNode<>(data); // 将新元素链入链表
        cur.next = node;
        node.next = next;
        size++;
    }

    /**
     * 向链表末尾添加元素, 返回新节点
     *
     * @param data 元素
     */
    public void add(E data) {
        add(data, size);
    }

    /**
     * 删除链表中指定位置的元素
     *
     * @param index 索引
     * @return 被删除的元素
     */
    public E remove(int index) {
        if (index > size - 1 || index < 0) {
            throw new RuntimeException("超出范围...");
        }

        ListNode<E> cur = head;
        for (int i = 0; i < index; i++) {
            cur = cur.next;
        }

        ListNode<E> temp = cur.next;
        E data = temp.data;
        cur.next = temp.next;
        temp = null;

        size--;

        return data;
    }

    /**
     * 删除头部并返回首元素
     *
     * @return 首元素
     */
    public E remove() {
        return remove(0);
    }

    /**
     * 删除链表中的重复元素(外循环 + 内循环)
     * 时间复杂度：O(n^2)
     */
    public void removeDuplicateNodes() {
        ListNode<E> cur = head.next;
        while (cur != null) { // 外循环
            ListNode<E> temp = cur;
            while (temp != null && temp.next != null) { // 内循环
                if (cur.data.equals(temp.next.data)) {
                    ListNode<E> duplicateNode = temp.next;
                    temp.next = duplicateNode.next;
                    duplicateNode.next = null;
                    size--;
                }
                temp = temp.next;
            }
            cur = cur.next;
        }
    }

    /**
     * 找出单链表中倒数第k个元素
     * 快慢指针法，快指针先从头走k-1步，然后慢指针从头部出发与快指针一起行进，一次一步，当快指针到末尾时慢指针的位置即为所求
     *
     * @param k k
     * @return 倒数第k个元素
     */
    public ListNode<E> getEndK(int k) {
        ListNode<E> pre = head.next;
        ListNode<E> post = head.next;
        for (int i = 1; i < k; i++) { // pre先走k-1步
            if (pre != null) {
                pre = pre.next;
            }
        }
        if (pre != null) {
            // 当pre走到链表末端时，post正好指向倒数第K个节点
            while (pre != null && pre.next != null) {
                pre = pre.next;
                post = post.next;
            }
            return post;
        }
        return null;
    }

    /**
     * 反转链表
     */
    public void reverseLinkedList() {
        ListNode<E> cur = head.next; // 原链表
        ListNode<E> pre = null; // 反转后的链表

        while (cur != null) { // 对原链表中的每个节点进行反转
            ListNode<E> next = cur.next; // 记录当前节点的下一个节点
            cur.next = pre; // 当前节点指向反转后的链表
            pre = cur; // 更新反转后的链表
            cur = next; // 更新当前节点
        }
        head.next = pre; // 将原链表的头结点指向反转后的链表
    }

    /**
     * 判断单链表是否为空
     *
     * @return true 为空; false 不为空
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 打印输出单链表
     */
    public void print() {
        ListNode<E> cur = head.next;
        while (cur != null) {
            System.out.print(cur.data + " ");
            cur = cur.next;
        }
        System.out.println();
    }

    /**
     * 逆序输出
     */
    public void reversePrint(ListNode<E> head) {
        if (head.next != null) {
            reversePrint(head.next); // 不断"递去"
            System.out.print(head.next.data + " "); // "归来"开始打印
        }
    }

    /**
     * 判断单链表是否有环
     * 快慢指针法，快指针和慢指针同时从头出发，快指针一次走两步，慢指针一次走一步，若两针相遇，则有环; 存在next为null，则无环
     *
     * @return true 有环; false 无环
     */
    public boolean hasLoop() {
        ListNode<E> index1 = head.next; // 慢指针
        ListNode<E> index2 = head.next; // 快指针
        while (index2 != null && index2.next != null
                && index2.next.next != null) {
            index1 = index1.next;
            index2 = index2.next.next;
            if (index1 == index2) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断当前链表与目标链表是否相交(相交与否取决于尾节点是否相同)
     *
     * @return true 相交; false 不相交
     */
    public boolean isIntersect(LinkedList<E> list2) {
        ListNode<E> cur1 = head.next;   // 当前链表
        ListNode<E> cur2 = list2.head.next;  // 目标链表

        // 两链表有一个为空，则返回 false
        if (cur1 == null || cur2 == null) {
            return false;
        }

        // 遍历到第一个链表的尾节点
        while (cur1.next != null) {
            cur1 = cur1.next;
        }

        // 遍历到第二个链表的尾节点
        while (cur2.next != null) {
            cur2 = cur2.next;
        }

        return cur1 == cur2;  // 相交与否取决于尾节点是否相同
    }

    /**
     * 返回两链表的交点(若不相交 ， 返回null)
     *
     * @return 链表交点
     */
    public ListNode<E> getIntersectionPoint(LinkedList<E> list2) {
        ListNode<E> cur1 = head.next;   // 当前链表
        ListNode<E> cur2 = list2.head.next;  // 目标链表

        if (this.isIntersect(list2)) {  // 先判断是否相交
            // 让长度较长的链表先移动step步
            int step = Math.abs(list2.size - this.size);
            if (list2.size > this.size) {
                while (step > 0) {
                    cur2 = cur2.next;
                    step--;
                }
            } else if (list2.size < this.size) {
                while (step > 0) {
                    cur1 = cur1.next;
                    step--;
                }
            }

            // 两个指针同时移动，一旦指向同一个节点，即为交点
            while (cur1 != cur2) {
                cur1 = cur1.next;
                cur2 = cur2.next;
            }
            return cur1;
        }

        return null;
    }

    /**
     * 返回链表的长度
     *
     * @return 链表长度
     */
    public int size() {
        return size;
    }
}
