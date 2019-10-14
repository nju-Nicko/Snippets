package com.huawei.nlz.snippets.playground.chars;

import java.util.Objects;

public class Chars {

    public static void main(String[] args) {
        chars("\uD83D\uDE09你好呀！\uD83D\uDE2C");
        /*
         * 输出：
         * 😉
         * 你
         * 好
         * 呀
         * ！
         * 😬
         */
    }

    public static void chars(String str) {
        Objects.requireNonNull(str);
        for (int i = 0; i <= str.length() - 1; ) {
            // 获取代码点
            int codePoint = str.codePointAt(i);
            // 判断代码点是不是位于Unicode增补平面
            if (Character.isSupplementaryCodePoint(codePoint)) {
                // 如果位于增补平面，则该字符在UTF16下使用两个代码单元编码，
                // 需要通过Character.toChars(int codePoint)输出
                System.out.println(Character.toChars(codePoint));
                i += 2;
            } else {
                // 否则直接把int强转成char输出即可，只占一个代码单元
                System.out.println((char) codePoint);
                i += 1;
            }
        }
    }

}
