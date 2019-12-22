需要依赖mybatis自身的jar包，以及数据库connector的jar包。
````xml
<!-- 依赖MyBatis -->
<dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis</artifactId>
    <version>3.4.4</version>
</dependency>

<!-- 依赖数据库Connector -->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>6.0.6</version>
</dependency>
````
如上，我们使用了MySQL的java connector。