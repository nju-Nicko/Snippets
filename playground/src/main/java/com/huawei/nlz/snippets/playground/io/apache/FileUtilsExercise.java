package com.huawei.nlz.snippets.playground.io.apache;

import org.apache.commons.io.FileUtils;

import java.io.*;

/**
 * FileUtils演练
 */
public class FileUtilsExercise {

    /**
     * 测试copyFile
     */
    public void testCopyFile() {
        File sourceFile = new File("pom.xml");
        File destFile = new File("target.xml");
        try {
            FileUtils.copyFile(sourceFile, destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试forceDelete一个文件
     */
    public void testForceDeleteFile() {
        try {
            FileUtils.forceDelete(new File("target.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试forceDelete一个目录，目录下面有子目录和文件
     */
    public void testForceDeleteDir() {
        // 事先创建一个测试目录mydir[d](subdir[d], testfile.txt[f])
        try {
            FileUtils.forceDelete(new File("mydir"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
         * 可以看到，子目录和文件都被删除。
         */
    }

    /**
     * 测试copyInputStreamToFile
     * <p>
     * 这是一个很有用的工具类，可以把InputStream的内容写入到目标文件。
     */
    public void testCopyInputStreamToFile() {
        try {
            InputStream inputStream = new FileInputStream("pom.xml");
            FileUtils.copyInputStreamToFile(inputStream, new File("targetPom.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
