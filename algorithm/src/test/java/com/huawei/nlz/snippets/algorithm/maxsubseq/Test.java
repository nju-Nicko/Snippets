package com.huawei.nlz.snippets.algorithm.maxsubseq;

import org.junit.Assert;

public class Test {
    @org.junit.Test
    public void testMaxSubSeq() {
        int[] arr = {4, -3, 5, -2, -1, 2, 6, -2};
        Assert.assertEquals(11, MaxSubSeqSolution.maxSubSeq(arr));

        int[] arr2 = {-3, -2};
        Assert.assertEquals(0, MaxSubSeqSolution.maxSubSeq(arr2));
    }

    @org.junit.Test
    public void testMaxSubSeq2() {
        int[] arr = {4, -3, 5, -2, -1, 2, 6, -2};
        Assert.assertEquals(11, MaxSubSeqSolution.maxSubSeq2(arr));

        int[] arr2 = {-3, -2};
        Assert.assertEquals(0, MaxSubSeqSolution.maxSubSeq2(arr2));
    }

    @org.junit.Test
    public void testMaxSubSeq01() {
        int[] arr = {};
        Assert.assertEquals(0, MaxSubSeqSolution.maxSubSeq(arr));
        Assert.assertEquals(0, MaxSubSeqSolution.maxSubSeq2(arr));
    }
}
