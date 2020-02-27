在catinlin.sh的最上面加上：  
`JAVA_OPTS=" -XX:+PrintGCTimeStamps -XX:+PrintGCDetails -Xloggc:/lnmp/tomcat8/gc.$$.log"`