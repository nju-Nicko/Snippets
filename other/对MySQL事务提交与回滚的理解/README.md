# 对MySQL事务提交和回滚的理解
我们都知道数据库事务具有原子性，如果其中一个语句不能完成，整个单元就会回滚。但是很多人对这句话的理解存在问题。  
我们来看下面的SQL：
````
CREATE TABLE demo (
  id INT NOT NULL,
  content VARCHAR(45) NULL,
  PRIMARY KEY (id));

start transaction;
insert into demo values(1, 'content1');
insert into demo values(1, 'content2');
commit;
````
上述SQL commit后，表里到底是0条记录还是一条(1, 'content1')的记录呢？  
答案是执行第二个insert的时候会直接报错，单独执行commit后会把第一个insert的(1, 'content1')记录提交入库。即，`第二个insert的失败并没有把第一个的insert给回滚掉。`  
所以对事务提交和回滚的正确理解如下：如果事务中所有SQL语句执行正确则需要自己手动提交commit；否则有任何一条执行错误，需要自己提交一条rollback，这时会回滚所有操作，而不是commit会给你自动判断和回滚。  
Java JDBC里典型的事务提交/出错回滚代码如下：
````
public class Test {
	public static void main(String[] args) {
		Statement statement = null;
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test", "root", "");
			statement = connection.createStatement();
			connection.setAutoCommit(false);//相当于jdbc中的set autocommit = 0;
			statement.addBatch("update account set money=money-100 where card_id= '1234567890'");
			//将给定的 SQL 命令添加到此 Statement 对象的当前命令列表中
			statement.addBatch("update account set money=money+100 where card_id= '0987654321'");
			//将给定的 SQL 命令添加到此 Statement 对象的当前命令列表中
			statement.executeBatch();//批量执行此列表中的命令
			connection.commit();//若无异常爆发提交事务
		} catch (Exception e) {
			try {
				if(connection !=null){
					System.out.println("回滚");//
					connection.rollback();//若sql语句执行过程中发生异常则rollback
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally {
			try {
				if (statement!=null) {
					statement.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (connection!=null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
````
详细可见: [JDBC实现事务回滚/提交](https://blog.csdn.net/Zzze0101/article/details/90178841 "JDBC实现事务回滚/提交")。