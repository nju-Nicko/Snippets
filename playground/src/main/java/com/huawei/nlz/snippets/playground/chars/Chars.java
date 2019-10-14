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
            int codePoint = str.codePointAt(i);
            if (Character.isSupplementaryCodePoint(codePoint)) {
                System.out.println(Character.toChars(codePoint));
                i += 2;
            } else {
                System.out.println((char) codePoint);
                i += 1;
            }
        }
    }

}
