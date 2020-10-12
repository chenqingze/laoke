package com.aihangxunxi.aitalk.im.bootstrap;

import com.aihangxunxi.aitalk.im.config.ImServerConfiguration;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import io.netty.util.concurrent.EventExecutorGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.net.ssl.SSLException;
import java.net.InetSocketAddress;
import java.security.cert.CertificateException;

@Component
public class WebSocketServerBootstrap extends AitalkServerBootstrap {

	private static final Logger logger = LoggerFactory.getLogger(WebSocketServerBootstrap.class);

	private final WebSocketChannelInitializer webSocketChannelInitializer;

	public WebSocketServerBootstrap(ImServerConfiguration configuration, EventLoopGroup bossGroup,
			EventLoopGroup workerGroup, EventExecutorGroup processorGroup,
			WebSocketChannelInitializer webSocketChannelInitializer) {
		this.serverBootstrap = new ServerBootstrap();
		this.configuration = configuration;
		this.bossGroup = bossGroup;
		this.workerGroup = workerGroup;
		this.processorGroup = processorGroup;
		this.webSocketChannelInitializer = webSocketChannelInitializer;
	}

	/**
	 * 初始化netty server 配置
	 */
	@Override
	public void init() throws CertificateException, SSLException {
		logger.info(">>> websocket server 初始化配置.");

		// Configure SSL.
		final SslContext sslCtx;
		if (configuration.ssl) {
			SelfSignedCertificate ssc = new SelfSignedCertificate();
			sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
		}
		else {
			sslCtx = null;
		}

		// reactor模式线程.设置ServerBootstrap 要用的EventLoopGroup 。这些EventLoopGroup
		// 将用于ServerChannel 和被接受的子Channel 的I/O处理
		serverBootstrap.group(bossGroup, workerGroup);
		// 设置将要被实例化的ServerChannel类,这里通过nio方式来接收连接和处理连接
		serverBootstrap.channel(NioServerSocketChannel.class);
		// 增加由ServerChannel处理的channelHandler
		serverBootstrap.handler(new LoggingHandler(LogLevel.INFO));
		// 增加由ServerChannel的子Channel处理的channelHandler
		serverBootstrap.childHandler(webSocketChannelInitializer);
		// 设置ServerChannel选项
		// serverBootstrap.option(ChannelOption.ALLOCATOR,
		// PooledByteBufAllocator.DEFAULT);
		// 设置子Channel选项
		// serverBootstrap.childOption(ChannelOption.AUTO_READ, true);
		// serverBootstrap.childOption(ChannelOption.ALLOCATOR,
		// PooledByteBufAllocator.DEFAULT);
		// 设置监听端口
		serverBootstrap.localAddress(new InetSocketAddress(configuration.port));

	}

	/**
	 * netty server 启动线
	 */
	@PostConstruct
	private void startup() {
		super.run();
	}

	/**
	 * 关闭netty server
	 */
	@PreDestroy
	public void shutdown() {
		logger.info(">>> websocket server 开始关闭");
		super.serverChannel.close();
	}

}
