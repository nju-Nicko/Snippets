package com.huawei.nlz.snippets.playground.byteexercise;

public class ByteExercise {

    public static void main(String[] args) {
        // 整型字面值转字节类型
        // 我们知道byte是带符号的8位整数，能存储的值的范围是-128~127，如果整型字面值在此范围之间，则可以直接赋值；如果不在，则需要强转。
        // 整型值在Java虚拟机内存里都是32位存储，强转时是直接做的截断处理，截取末位8位。

        byte b0 = 100; // ok
        byte b1 = -128; // ok
        byte b2 = 127; // ok
        // byte b3 = -129; // error，提示int不好赋给byte
        // byte b4 = 128; // error，提示int不好赋给byte
        byte b5 = (byte) 255;
        System.out.println(b5); // 打印-1，截断末位8位

        // byte转int
        int i0 = b5; // 这边我理解是做符号扩展，因为b5是-1，是负数，所以高位补1
        System.out.println(i0); // 打印-1

        // byte转无符号int
        int i1 = b5 & 0xFF;
        System.out.println(i1); // 打印255
    }

}
