package com.huawei.nlz.snippets.playground.trywithresources;

import java.io.Closeable;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Main {

    public static void main(String[] args) {
        try(MyResource mr = new MyResource(); InputStream is = new FileInputStream("pom1.xml")){
            System.out.println("enter try block.");
        }catch(Exception e){
            System.out.println("enter catch block.");
            e.printStackTrace();
        }finally {
            System.out.println("enter finally.");
        }

        /*
         * 新建资源时未抛异常，会输出：
         * enter try block.
         * my resource is closed
         * enter finally.
         *
         * 新建资源时抛了异常，会输出：
         * my resource is closed
         * enter catch block.
         * java.io.FileNotFoundException: pom1.xml (系统找不到指定的文件。)
	     *     at java.io.FileInputStream.open0(Native Method)
	     *     at java.io.FileInputStream.open(FileInputStream.java:195)
	     *     at java.io.FileInputStream.<init>(FileInputStream.java:138)
	     *     at java.io.FileInputStream.<init>(FileInputStream.java:93)
	     *     at com.huawei.nlz.snippets.playground.trywithresources.Main.main(Main.java:11)
         * enter finally.
         */
    }

    public static class MyResource implements Closeable{

        @Override
        public void close() throws IOException {
            System.out.println("my resource is closed");
        }
    }

}
