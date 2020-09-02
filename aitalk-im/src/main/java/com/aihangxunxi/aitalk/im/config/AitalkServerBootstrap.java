package com.aihangxunxi.aitalk.im.config;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoopGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLException;
import java.security.cert.CertificateException;

public abstract class AitalkServerBootstrap {

	private static final Logger logger = LoggerFactory.getLogger(AitalkServerBootstrap.class);

	protected ImServerConfiguration configuration;

	// 初始化EventLoopGroup，其将提供用以处理Channel 事件的EventLoop
	// 用于服务端接受客户的连接
	protected EventLoopGroup bossGroup;

	// 用于处理SocketChannel的网络读写
	protected EventLoopGroup workerGroup;

	protected EventExecutorGroup processorGroup;

	protected ServerBootstrap serverBootstrap;

	protected Channel serverChannel;

	/**
	 * 初始化启动参数参数
	 */
	abstract void init() throws CertificateException, SSLException;

	private void start() throws InterruptedException {
		logger.info(">>> server 开始启动.");
		ChannelFuture channelFuture = serverBootstrap.bind().sync();
		logger.info(">>> server:[{}] 已经启动", channelFuture.channel().localAddress());

		// 监听通道关闭事件
		// 应用程序会一直等待，直到channel关闭
		serverChannel = channelFuture.channel();
		ChannelFuture closeFuture = channelFuture.channel().closeFuture().sync();
		closeFuture.addListener((ChannelFutureListener) channelFuture1 -> {
			if (channelFuture1.isSuccess()) {
				logger.info(">>> server:{} 已经停止成功.", channelFuture1.channel().localAddress());
			}
			else {
				logger.error(">>> server 停止监听失败，失败原因:{}", channelFuture1.cause().getLocalizedMessage());
				// 打印对堆栈信息
				channelFuture1.cause().printStackTrace();
			}
		});
	}

	/**
	 * netty server 线程
	 */
	public void run() {
		try {
			// 初始化netty参数
			this.init();
			// 启动netty消息服务
			this.start();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			processorGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}

}
