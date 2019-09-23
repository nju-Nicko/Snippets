package com.huawei.nlz.snippets.algorithm.combination;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class CombinationTest {
    @Test
    public void testCombination0() {
        int[] arr = {7, 8, 1, 2, 6};
        List<List<Integer>> results = Combination.combination(arr, 0);
        Assert.assertNotNull("result cannot be null.", results);
        System.out.println(results);
    }

    @Test
    public void testCombination1() {
        int[] arr = {7, 8, 1, 2, 6};
        List<List<Integer>> results = Combination.combination(arr, 1);
        Assert.assertNotNull("result cannot be null.", results);
        System.out.println(results);
    }

    @Test
    public void testCombination2() {
        int[] arr = {7, 8, 1, 2, 6};
        List<List<Integer>> results = Combination.combination(arr, 2);
        Assert.assertNotNull("result cannot be null.", results);
        System.out.println(results);
    }

    @Test
    public void testCombination3() {
        int[] arr = {7, 8, 1, 2, 6};
        List<List<Integer>> results = Combination.combination(arr, 3);
        Assert.assertNotNull("result cannot be null.", results);
        System.out.println(results);
    }

    @Test
    public void testCombination4() {
        int[] arr = {7, 8, 1, 2, 6};
        List<List<Integer>> results = Combination.combination(arr, 4);
        Assert.assertNotNull("result cannot be null.", results);
        System.out.println(results);
    }

    @Test
    public void testCombination5() {
        int[] arr = {7, 8, 1, 2, 6};
        List<List<Integer>> results = Combination.combination(arr, 5);
        Assert.assertNotNull("result cannot be null.", results);
        System.out.println(results);
    }

    @Test
    public void testCombination6() {
        int[] arr = {11};
        List<List<Integer>> results = Combination.combination(arr, 1);
        Assert.assertNotNull("result cannot be null.", results);
        System.out.println(results);
    }

    @Test
    public void testCombination7() {
        int[] arr = {11, -92};
        List<List<Integer>> results = Combination.combination(arr, 1);
        Assert.assertNotNull("result cannot be null.", results);
        System.out.println(results);
    }

    @Test
    public void testCombination8() {
        int[] arr = {11, -92};
        List<List<Integer>> results = Combination.combination(arr, 2);
        Assert.assertNotNull("result cannot be null.", results);
        System.out.println(results);
    }
}
