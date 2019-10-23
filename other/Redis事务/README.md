# Redis事务代码模板
```
watch key...       // 监视key...
[unwatch]          // 放在watch之后，multi之前，取消对所有key的监视
multi              // 开始一个新的redis事务
[discard]          // 放在multi之后，exec之前，取消对所有key的监视，并清空所有已入队的命令，包括multi
exec               // 提交事务
```
# WATCH命令描述
>WATCH命令可以监控一个或多个键，一旦其中有一个键被修改(或删除)，之后的事务就不会执行。监控一直持续到EXEC命令(事务中的命令是在EXEC之后才执行的，所以在MULTI命令后可以修改WATCH监控的键值)。
>