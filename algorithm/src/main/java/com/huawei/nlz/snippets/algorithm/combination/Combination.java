package com.huawei.nlz.snippets.algorithm.combination;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Combination {
    /**
     * 数组里取m个元素的所有组合。
     * <p>
     * 思路: 递归，n个元素取m个的所有组合，可以看作前n-1个元素取m个的所有取法，
     * 并上前n-1个元素取m-1个元素的所有取法再加上第n个元素。
     * 递归方程：
     * f(n, m) = f(n-1, m) ∪ [ f(n-1, m-1) join eleM ]
     *
     * @param arr 数组
     * @param m   取m个元素
     * @return 取m个元素的所有组合
     */
    public static List<List<Integer>> combination(int[] arr, int m) {
        if (null == arr) {
            throw new NullPointerException("array is null.");
        }
        if (arr.length < m) {
            throw new IllegalArgumentException("array length cannot be smaller than m.");
        }
        if (arr.length == 0) {
            return Collections.emptyList();
        }

        return combination0(arr, m, 0, arr.length - 1);
    }

    private static List<List<Integer>> combination0(int[] arr, int m, int lo, int hi) {
        if (m == 0) {
            List<List<Integer>> lists = new ArrayList<>();
            List<Integer> list = new ArrayList<>();
            lists.add(list);
            return lists;
        }

        if (hi - lo + 1 == m) {
            List<List<Integer>> lists = new ArrayList<>();
            List<Integer> list = new ArrayList<>();
            for (int i = lo; i <= hi; i++) {
                list.add(arr[i]);
            }
            lists.add(list);
            return lists;
        }

        List<List<Integer>> com0 = combination0(arr, m, lo, hi - 1);
        List<List<Integer>> com1 = combination0(arr, m - 1, lo, hi - 1);
        for (List<Integer> list : com1) {
            list.add(arr[hi]);
        }
        com0.addAll(com1);
        return com0;
    }
}
