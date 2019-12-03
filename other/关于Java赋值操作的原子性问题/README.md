> #### Non-Atomic Treatment of double and long
>For the purposes of the Java programming language memory model, a single write to a non-volatile long or double value is treated as two separate writes: one to each 32-bit half. This can result in a situation where a thread sees the first 32 bits of a 64-bit value from one write, and the second 32 bits from another write.  
Writes and reads of volatile long and double values are always atomic.  
Writes to and reads of references are always atomic, regardless of whether they are implemented as 32-bit or 64-bit values.  
Some implementations may find it convenient to divide a single write action on a 64-bit long or double value into two write actions on adjacent 32-bit values. For efficiency's sake, this behavior is implementation-specific; an implementation of the Java Virtual Machine is free to perform writes to long and double values atomically or in two parts.  
Implementations of the Java Virtual Machine are encouraged to avoid splitting 64-bit values where possible. Programmers are encouraged to declare shared 64-bit values as volatile or synchronize their programs correctly to avoid possible complications.
>
在64位的mac上，long和int都不具有原子性，而在64位的debian上，long和int都具有原子性，所以，在不同的操作系统上是不一样的。所以，对于数值类型的变量如果是竞争的，就要显式的使用锁。  
但是，对于读写references，都是atomic的。  
另外，使用volatile关键字也可以使得long和double具有原子性。