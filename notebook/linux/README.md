# Linux Notebook
本文中的所有实验都是基于: ubuntu-16.04.1。
## 图形界面使用
+ 打开终端快捷键: ctrl + alt + T。多次使用该快捷键将打开多个终端。  
  关闭终端: ctrl + D快捷键，或者直接点击终端界面的关闭按钮。
+ 显示隐藏文件/取消显示隐藏文件: ctrl + H。
## Shell命令
+ Shell命令的基本格式:  
命令名 [选项]... [参数]...  
命令严格区分大小写。通过分号;可以分隔多条命令，从而在一行中输入多个命令。
+ 命令自动补齐:  
Tab键可以联想命令，如果只有唯一联想结果，那么就会自动在命令行补齐命令。如下图:  
![自动补全](assets/imagine.png "自动补全")
如图，直接在命令行输入mk，然后按两下Tab，则会联想出mk开头的所有命令，有很多；继续输入d，再按两下Tab，联想出了mkdir和mkdosfs；继续输入i，再按两下Tab，则mkdir命令被自动补全。
+ Shell提示符:  
格式为: [登录用户@主机名~]提示符。  
其中，root用户登录成功，提示符为#号；普通用户登录成功，提示符为$；如果当前是用户主目录，则路径部分显示为~。
+ 查看命令帮助:  
可以用man命令或者--help选项或者info命令。  
例如想查看adduser命令的用法，你可以:
````
man adduser    这个很全，而且有例子
adduser --help    这个告诉你一些常用的参数
info adduser
````
此外也可以使用help命令查看Linux内部命令的帮助信息，如查看source:
````
help source    查看source命令的帮助信息
````
+ 内部命令和外部命令:  
内部命令：内部命令是内嵌在Shell程序里的，其包含的往往是一些简单的Linux系统命令。它们被Shell程序识别并通过Shell内部完成运行，通常在Linux系统加载运行时Shell就被加载并驻留在系统内存中。内部命令的执行速度通常都比外部命令快。  
常见的内部命令: alias, bg, bind, break, builtin, caller, cd, command, compgen, complete, compopt,  continue,  declare,  dirs,  disown,  echo,enable,  eval,  exec, exit, export, false, fc, fg, getopts, hash, help,history, jobs, kill, let, local, logout, mapfile, popd, printf,  pushd,pwd,  read, readonly, return, set, shift, shopt, source, suspend, test,times, trap, true, type, typeset, ulimit, umask, unalias, unset, wait。  
 外部命令: Linux系统中能够完成特定功能的脚本文件或二进制文件，每个外部命令对应了系统中的一个文件。这些文件通常比较强大，包含的程序量也很大，在系统加载时并不随系统一起被加载到内存中，而是在需要时才将其调用内存。外部命令的实体通常并不包含在Shell中，但是其命令执行过程是由Shell程序控制的。Shell程序管理外部命令执行的路径查找、加载存放，并控制命令的执行。  
 可以通过type查看命令是内部命令还是外部命令，如:
 ````
type cd    输出：cd是shell内建
````
+ 重启Linux:
````
reboot  立即重启
shutdown -r now 立即重启
shutdown -r 10  过10分钟自动重启
shutdown -r 20:35  在时间20:35的时候重启
````
如果是通过shutdown命令设置重启的话，可以用shutdown -c命令取消重启。  
关机:
````
halt     立即关机
poweroff   立即关机
shutdown -h now  立刻关机
shutdown -h 10    10分钟后自动关机
````
如果是通过shutdown命令设置关机的话，可以用shutdown -c命令取消关机。
+ 查找命令位置:  
通过which命令查看:    
which命令按文件名查找。which会在用户设置的PATH目录中查询，所以也可以查询系统命令。  
如: which pwd，输出/bin/pwd。  
也可以一次性输出多个命令的位置，如which pwd echo nlzecho。  
whereis，按文件名查找，会列出所有位置。  
如: whereis nlz，输出: /home/niluzhang/shell/nlz.txt。
+ 隐藏文件:  
建立文件或者文件夹时，如果以.开头，那么得到的就是隐藏文件。  
通过命令将文件a隐藏：mv a .a 。  
显示所有文件，包括隐藏文件：ls -a 。
+ 工作目录、用户主目录、绝对路径、相对路径等概念:  
工作目录是用户当前所处的目录，可以用pwd查看工作目录的绝对路径；  
主目录是创建用户时给这个用户指定的目录，用符号~表示；  
绝对路径是从根开始到某个文件或目录的路径；  
相对路径是从用户工作目录开始到某个文件或目录的路径。
+ cat命令
cat命令将文件连接，然后输出到标准输出。  
一次显示整个文件到命令行:
````
cat case.log
````
从键盘创建文件:
````
cat > newfile
````
注意这个命令只能创建新文件，不能编辑已有的文件。按两次ctrl + D退出输入，终端重新出现Shell提示符。
将几个文件合并到一个文件:  
````
cat file1 file2 > file3
````
此外，cat命令可以通过-n或者--number选项在输出文件的时候显示行号；-b或者--number-nonblank，也是从1开始对输出行编号，但是不对空白行编号；-s或–squeeze-blank，当遇到有连续两行以上的空白行，就代换为一行的空白行。