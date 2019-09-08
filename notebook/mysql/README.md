# MySQL Notebook
>学不可以已。
>--荀子《劝学篇》
>
本文里的实验基于`Ver 14.14 Distrib 5.7.20`。
## 安装配置
### Windows下的安装和配置
有多种安装方式。本人使用的方式是从https://dev.mysql.com/downloads/windows/installer/下载安装MySQL Installer，然后通过MySQL Installer来安装MySQL Server、MySQL Workbench、MySQL Connector for Java等组件。  
安装完成MySQL服务后，可以在Windows的服务列表里找到它。右键MySQL服务->属性，可以看到可执行命令: `C:\Program Files (x86)\MySQL\MySQL Server 5.7\bin\mysqld.exe" --defaults-file="C:\ProgramData\MySQL\MySQL Server 5.7\my.ini" MySQL57`，Windows便是通过这条命令来启动MySQL服务。其中的`--defaults-file`便是MySQL配置文件的路径，后续对MySQL默认配置的修改便可以修改此文件。如果想自定义配置文件路径，可以修改注册表。  
除了可以在Windows服务列表里可视化地启动和关闭MySQL，也可以自己通过命令行来启动和关闭。启动命令与上面无异，关闭可以通过`mysqladmin -uroot -p shutdown`来完成。
### Linux下的安装和配置
[Linux下的MySQL安装和配置](https://blog.csdn.net/qq_32723447/article/details/80284118 "Linux下的MySQL安装和配置")
## SQL
创建数据库:
````
create database ${dbname}
````
&nbsp;  
查询系统里有哪些数据库:
````
show databases
````
例如我当前的数据库有:  
![show databases](assets/show%20databases.png "show databases")  
&nbsp;  
选择数据库:
````
use ${dbname}
````
&nbsp;  
选中数据库后我们可以查看数据库中有哪些表:
````
show tables
````
查询结果如图:  
![show tables](assets/show%20tables.png "show tables")  
