package com.aihangxunxi.aitalk.im.handler;

import com.aihangxunxi.aitalk.im.channel.ChannelConstant;
import com.aihangxunxi.aitalk.im.channel.ChannelManager;
import io.netty.channel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.SocketAddress;

/**
 * @author chenqingze1987@gmail.com
 * @version 1.0
 * @since 2020/7/30
 */
@Component
@ChannelHandler.Sharable
public class ExceptionHandler extends ChannelDuplexHandler {

	private static final Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

	@Resource
	private ChannelManager channelManager;

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		// 清理session
		if (logger.isDebugEnabled()) {
			logger.debug("handler removed");
			logger.debug("channel 缓存大小:{}", channelManager.getLocalChannelCacheSize());
			logger.debug("用户多客户端session集合缓存 大小:{}", channelManager.getLocalChannelCacheSize());
		}
		Channel channel = ctx.channel();
		if (channel.hasAttr(ChannelConstant.IS_OLD_CHANNEL_ATTRIBUTE_KEY)
				&& !channel.attr(ChannelConstant.IS_OLD_CHANNEL_ATTRIBUTE_KEY).get()) {
			channelManager.removeChannel(ctx);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		if (logger.isDebugEnabled()) {
			logger.debug("handler removed");
			logger.debug("channel 缓存大小:{}", channelManager.getLocalChannelCacheSize());
			logger.debug("channel 状态:{}", ctx);
			logger.debug("用户多客户端session集合缓存 大小:{}", channelManager.getLocalChannelCacheSize());
		}
		// Uncaught exceptions from inbound handlers will propagate up to this handler
		ctx.close();
		logger.warn("发生异常，关闭channel。异常信息：{}", cause.getMessage());
		cause.printStackTrace();
	}

	@Override
	public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress,
			ChannelPromise promise) {
		ctx.connect(remoteAddress, localAddress, promise.addListener((ChannelFutureListener) future -> {
			if (!future.isSuccess()) {
				// Handle connect exception here...
				final Throwable cause = future.cause();
				logger.warn(">>>> connection failed");
				cause.printStackTrace();
			}
		}));
	}

	// @Override
	// public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
	// ctx.writeAndFlush(msg, promise.addListener((ChannelFutureListener) future -> {
	// // Handle write exception here...
	// if (!future.isSuccess()) {
	// final Throwable cause = future.cause();
	// logger.warn(">>>> write failed:{}", cause.getMessage());
	// logger.warn(">>>> write getStackTrace:{}", cause.getStackTrace());
	// cause.printStackTrace();
	// }
	// }));
	// }
	// if(!future.isSuccess())
	//
	// {
	// final Throwable cause = future.cause();
	// if (Exceptions.isConnectionClosedException(cause)) {
	// log.trace("Failed to write publish. Client not connected anymore");
	// statusFuture.set(PublishStatus.NOT_CONNECTED);
	//
	// } else if (cause instanceof EncoderException) {
	// Exceptions.rethrowError("Failed to write publish. Encoding Failure.", cause);
	// final Throwable rootCause = cause.getCause();
	// if (cause != rootCause) {
	// Exceptions.rethrowError("Failed to write publish. Encoding Failure, root cause:",
	// rootCause);
	// }
	// statusFuture.set(PublishStatus.FAILED);
	// } else {
	// Exceptions.rethrowError("Failed to write publish.", cause);
	// statusFuture.set(PublishStatus.FAILED);
	// }
	// }

}
