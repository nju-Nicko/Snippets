package com.huawei.nlz.snippets.playground.filecopy.classic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Main {

    public static void main(String[] args) {
        File src = new File("src.txt");
        File dest = new File("dest.txt");

        // 需要源文件存在，不存在则抛出一个异常
        if (!src.exists()) {
            throw new IllegalStateException("Source file doesn't exist!");
        }

        // 4kB的缓冲区
        byte[] buffer = new byte[4096];

        try (InputStream is = new FileInputStream(src); OutputStream out = new FileOutputStream(dest)) {
            int n;
            final int EOF = -1;
            while ((n = is.read(buffer)) != EOF) {
                out.write(buffer, 0, n);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
