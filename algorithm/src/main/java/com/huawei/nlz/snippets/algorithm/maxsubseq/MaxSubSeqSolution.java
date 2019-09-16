package com.huawei.nlz.snippets.algorithm.maxsubseq;

public class MaxSubSeqSolution {
    /**
     * 最大子序列和。可以是空子序列，和认为是0。
     * <p>
     * 思路: 分治法，输入数组劈两半，递归求出左半部分的最大子序列和和右半部分的最大子序列和，
     * 然后计算横跨横跨中间的最大子序列和，最后将这三者求max。
     * <p>
     * 时间复杂度:
     * f(n)=2f(n/2)+n+2 = 2[2f(n/4)+n/2+2]+n+2 = 4f(n/4)+2n+6 = ... = O(nlgn)
     *
     * @param arr 数组
     * @return 最大子序列和
     */
    public static int maxSubSeq(int[] arr) {
        if (null == arr) {
            throw new IllegalArgumentException("input array is null");
        }

        return maxSubSeq0(arr, 0, arr.length - 1);
    }

    private static int maxSubSeq0(int[] arr, int lo, int hi) {
        /*
         * 递归的基准条件定义为lo和hi相等或者只差1的时候，
         * 因为所有的情况最终都会落到这两个场景上，且易于计算。
         */
        if (lo == hi) {
            return max2(0, arr[lo]);
        }
        if (hi - lo == 1) {
            return max3(arr[lo], arr[hi], 0);
        }

        int mid = (lo + hi) / 2;
        int leftSideMax = maxSubSeq0(arr, lo, mid);
        int rightSideMax = maxSubSeq0(arr, mid + 1, hi);
        // 计算横跨中间的子序列的最大和，即必须选择mid与mid+1。
        // 以mid为基准往低地址端算，求出最大和，若为负则以0记。
        int maxLeftBorderSum = 0;
        int currentLeftBorderSum = 0;
        for (int i = mid; i >= lo; i--) {
            currentLeftBorderSum += arr[i];
            if (currentLeftBorderSum > maxLeftBorderSum) {
                maxLeftBorderSum = currentLeftBorderSum;
            }
        }
        // 以mid+1为基准往高地址端算，求出最大和，若为负则以0记。
        int maxRightBorderSum = 0;
        int currentRightBorderSum = 0;
        for (int i = mid + 1; i <= hi; i++) {
            currentRightBorderSum += arr[i];
            if (currentRightBorderSum > maxRightBorderSum) {
                maxRightBorderSum = currentRightBorderSum;
            }
        }

        return max3(leftSideMax, rightSideMax, maxLeftBorderSum + maxRightBorderSum);
    }

    private static int max2(int a, int b) {
        return Math.max(a, b);
    }

    private static int max3(int a, int b, int c) {
        int abMax = Math.max(a, b);
        int abcMax = Math.max(abMax, c);
        return abcMax;
    }

    /**
     * 暴力枚举解法。
     * <p>
     * 时间复杂度:
     * n-1  n-1  j         n-1  n-1            n-1
     * ∑    ∑    ∑    1  = ∑    ∑    (j-i+1) = ∑    [(1-i)*(n-1) + (n-1)n/2] = .... = O(n^3)
     * i=0  j=i  k=i       i=0  j=1            i=0
     *
     * @param arr 数组
     * @return 最大子序列和
     */
    public static int maxSubSeq2(int[] arr) {
        int maxSum = 0;
        for (int i = 0; i < arr.length; i++)
            for (int j = i; j < arr.length; j++) {
                int thisSum = 0;
                for (int k = i; k <= j; k++)
                    thisSum += arr[k];
                if (thisSum > maxSum)
                    maxSum = thisSum;
            }
        return maxSum;
    }
}
