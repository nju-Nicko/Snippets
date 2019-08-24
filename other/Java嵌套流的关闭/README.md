# Java嵌套流如何关闭
先看一段代码：
```
FileOutputStream out1 = new FileOutputStream("D:\\SingleTon.txt");
ObjectOutputStream out2 = new ObjectOutputStream(out1);
out1.close();//是否需要关闭内层的IO流？
out2.close();
```
考虑一下，像这样的嵌套IO流，是否应该从内到外依次关闭呢?

答案是不需要！这些IO类都是JDK自带的，调用了最外层的close方法，其实是一层一层向内调用了最内层的IO类的close方法，这也就是装饰者模式。

当然你肯定想问，为什么我之前自内向外逐层关闭也不会抛出异常？

因为就算你对某个流重复关闭多次，也不会抛出异常。