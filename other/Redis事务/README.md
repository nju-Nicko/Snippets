# Redis事务代码模板
```
watch key...         //监视key...
[unwatch]            //放在watch之后，multi之前，取消对所有key的监视
multi                //开始一个新的redis事务
[discard]            //放在multi之后，exec之前，取消对所有key的监视，并清空所有已入队的命令
exec                 //提交事务
```