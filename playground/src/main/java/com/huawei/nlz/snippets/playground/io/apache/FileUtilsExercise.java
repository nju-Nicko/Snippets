package com.huawei.nlz.snippets.playground.io.apache;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

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

    /**
     * 测试listFiles。
     * <p>
     * 这也是一个很有用的功能，可以按多种过滤方式，过滤出指定目录下符合指定要求的文件或目录。
     * 可参考：https://blog.csdn.net/wangmx1993328/article/details/82216887#listFiles。
     */
    public void testListFiles() {
        // 事先创建entity[d](Case.entity.xml[f], Task.entity.xml[f], subdir[d](Attachment.entity.xml[f], test2.txt[f]), test.txt[f])
        // 注意这边后缀名前不用加"."，FileUtils会自动加上，然后使用文件名后缀过滤器
        Collection<File> files = FileUtils.listFiles(new File("entity"), new String[]{"entity.xml"}, true);
        for (File file : files) {
            System.out.println(file.getName() + " ");
        }
        System.out.println();
    }

}
