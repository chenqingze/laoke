aitalk 即时通讯

#启动参数
##### 开启log4j2 全局异步日志输出
-Dog4j2.contextSelector=org.apache.logging.log4j.core.async.AsyncLoggerContextSelector
##### 泄漏检测
-Dio.netty.leakDetectionLevel=ADVANCED
##### UTF-8字符集编码，防止输出乱码
-Dfile.encoding=utf-8 \
