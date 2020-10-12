package com.aihangxunxi.aitalk.im.config;

import com.aihangxunxi.aitalk.storage.config.StorageConfiguration;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

import javax.net.ssl.SSLException;
import java.security.cert.CertificateException;
import java.util.concurrent.atomic.AtomicInteger;

@Configuration
@Import({ StorageConfiguration.class, StandaloneConfiguration.class, ClusterConfiguration.class })
@ComponentScan(value = { "com.aihangxunxi.aitalk.im" })
@PropertySource("classpath:config.properties")
public class ImServerConfiguration {

	private static final Logger logger = LoggerFactory.getLogger(ImServerConfiguration.class);

	// 是否启用SSL
	public final boolean ssl;

	// 端口
	public final int port;

	// 线程数配置
	public final int bossGroupThreads;

	public final int workerGroupThreads;

	public final int processorGroupThreads;

	public ImServerConfiguration(@Value("${server.ssl}") boolean ssl, @Value("${server.port}") int port,
			@Value("${server.bossGroupThreads}") int bossGroupThreads,
			@Value("${server.workerGroupThreads}") int workerGroupThreads,
			@Value("${server.processorGroupThreads}") int processorGroupThreads) {
		this.ssl = ssl;
		this.port = port;
		this.bossGroupThreads = bossGroupThreads;
		this.workerGroupThreads = workerGroupThreads;
		this.processorGroupThreads = processorGroupThreads;

		logger.info("配置信息{}", this.toString());
	}

	/**
	 * 连接计数器
	 * @return 原子计数器
	 */
	@Bean
	public AtomicInteger connectCounter() {
		return new AtomicInteger(0);
	}

	// 初始化EventLoopGroup，其将提供用以处理Channel 事件的EventLoop
	// 用于服务端接受客户的连接
	@Bean
	public EventLoopGroup bossGroup() {
		return new NioEventLoopGroup(bossGroupThreads);
	}

	// 用于处理SocketChannel的网络读写
	@Bean
	public EventLoopGroup workerGroup() {
		return new NioEventLoopGroup(workerGroupThreads);
	}

	// 用于异步处理非连接类的业务逻辑,比较耗时的业务
	@Bean
	public EventExecutorGroup processorGroup() {
		return new DefaultEventExecutorGroup(processorGroupThreads);
	}

	// ssl
	@Bean
	public SslContext sslCtx() throws CertificateException, SSLException {
		final SslContext sslCtx;
		// Configure SSL.
		if (ssl) {
			SelfSignedCertificate ssc = new SelfSignedCertificate();
			sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
		}
		else {
			sslCtx = null;
		}
		return sslCtx;
	}

	// @Bean
	// public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer()
	// {
	// return new PropertySourcesPlaceholderConfigurer();
	// }

	@Override
	public String toString() {
		return "ServerConfiguration{" + "ssl=" + ssl + ", port=" + port + ", bossGroupThreads=" + bossGroupThreads
				+ ", workerGroupThreads=" + workerGroupThreads + ", processorGroupThreads=" + processorGroupThreads
				+ '}';
	}

}
