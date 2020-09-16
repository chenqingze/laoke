package com.aihangxunxi.aitalk.im.channel;

import com.github.benmanes.caffeine.cache.Cache;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 用户channel管理
 *
 * @author chenqingze107@163.com
 * @version 2.0
 */
@Component("channelManager")
public class DefaultChannelManager implements ChannelManager {

	@Resource
	private Cache<String, Channel> localChannelCache;

	@Override
	public void addChannel(ChannelHandlerContext context) {
		this.addChannel(context.channel());
	}

	@Override
	public void addChannel(Channel channel) {
		localChannelCache.put(channel.attr(ChannelConstant.USER_ID_ATTRIBUTE_KEY).get(), channel);
	}

	@Override
	public void removeChannel(ChannelHandlerContext context) {
		this.removeChannel(context.channel());
	}

	@Override
	public void removeChannel(Channel channel) {
		this.removeChannelByUserId(channel.attr(ChannelConstant.USER_ID_ATTRIBUTE_KEY).get());
	}

	@Override
	public void removeChannelByUserId(String userId) {
		localChannelCache.invalidate(userId);
	}

	@Override
	public Channel findChannelByUserId(String userId) {
		return localChannelCache.getIfPresent(userId);
	}

	@Override
	public Long getLocalChannelCacheSize() {
		return localChannelCache.estimatedSize();
	}

	@Override
	public void kickUser(ChannelHandlerContext context) {
		this.removeChannel(context);
		context.close();
	}

	@Override
	public void kickUser(Channel channel) {
		this.removeChannel(channel);
		channel.close();
	}

	@Override
	public void kickUser(String userId) {
		Channel channel = this.findChannelByUserId(userId);
		this.removeChannel(channel);
		channel.close();
	}

}
