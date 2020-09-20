aitalk 即时通讯
# maven 方式启动
```shell script
mvn exec:java -Dexec.mainClass="com.aihangxunxi.aitalk.im.AitalkImApp"
```
# jar 包方式启动
```shell script
java -jar aital-im.jar 
```
# 启动参数
##### 开启log4j2 全局异步日志输出
```shell script
-Dog4j2.contextSelector=org.apache.logging.log4j.core.async.AsyncLoggerContextSelector
```
##### 泄漏检测
```shell script
-Dio.netty.leakDetectionLevel=ADVANCED
```
##### UTF-8字符集编码，防止输出乱码
```shell script
-Dfile.encoding=utf-8 
```