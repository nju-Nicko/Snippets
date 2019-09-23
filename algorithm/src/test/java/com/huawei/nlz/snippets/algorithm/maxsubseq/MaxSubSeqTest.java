package com.huawei.nlz.snippets.algorithm.maxsubseq;

import org.junit.Assert;
import org.junit.Test;

public class MaxSubSeqTest {
    @Test
    public void testMaxSubSeq() {
        int[] arr = {4, -3, 5, -2, -1, 2, 6, -2};
        Assert.assertEquals(11, MaxSubSeqSolution.maxSubSeq(arr));

        int[] arr2 = {-3, -2};
        Assert.assertEquals(0, MaxSubSeqSolution.maxSubSeq(arr2));
    }

    @Test
    public void testMaxSubSeq2() {
        int[] arr = {4, -3, 5, -2, -1, 2, 6, -2};
        Assert.assertEquals(11, MaxSubSeqSolution.maxSubSeq2(arr));

        int[] arr2 = {-3, -2};
        Assert.assertEquals(0, MaxSubSeqSolution.maxSubSeq2(arr2));
    }

    @Test
    public void testMaxSubSeq01() {
        int[] arr = {};
        Assert.assertEquals(0, MaxSubSeqSolution.maxSubSeq(arr));
        Assert.assertEquals(0, MaxSubSeqSolution.maxSubSeq2(arr));
    }

    @Test
    public void testMaxSubSeq02() {
        int[] arr = {-2};
        Assert.assertEquals(0, MaxSubSeqSolution.maxSubSeq(arr));
        Assert.assertEquals(0, MaxSubSeqSolution.maxSubSeq2(arr));
    }

    @Test
    public void testMaxSubSeq03() {
        int[] arr = {2};
        Assert.assertEquals(2, MaxSubSeqSolution.maxSubSeq(arr));
        Assert.assertEquals(2, MaxSubSeqSolution.maxSubSeq2(arr));
    }
}
