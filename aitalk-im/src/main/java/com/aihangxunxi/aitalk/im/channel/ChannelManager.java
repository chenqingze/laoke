package com.aihangxunxi.aitalk.im.channel;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

/**
 * 用户channel管理
 *
 * @author chenqingze107@163.com
 * @version 2.0
 */
public interface ChannelManager {

	/**
	 * 向缓存中增加一个channel
	 * @param context {@link ChannelHandlerContext}
	 */
	void addChannel(ChannelHandlerContext context);

	/**
	 * 向缓存中增加一个channel
	 * @param channel {@link Channel}
	 */
	void addChannel(Channel channel);

	/**
	 * 从缓存中移除一个channel
	 * @param context {@link ChannelHandlerContext}
	 */
	void removeChannel(ChannelHandlerContext context);

	/**
	 * 从缓存中移除一个channel
	 * @param channel {@link Channel}
	 */
	void removeChannel(Channel channel);

	/**
	 * 删除某用户的所有channel
	 * @param userId 用户id
	 */
	void removeChannelByUserId(String userId);

	/**
	 * 获取用户channel
	 * @param userId 用户id
	 * @return channel 通道
	 */
	Channel findChannelByUserId(String userId);

	/**
	 * channel 缓存大小
	 * @return channel 缓存大小
	 */
	Long getLocalChannelCacheSize();

	/**
	 * 剔除用户
	 * @param context {@link ChannelHandlerContext}
	 */
	void kickUser(ChannelHandlerContext context);

	/**
	 * 剔除用户
	 * @param channel {@link Channel}
	 */
	void kickUser(Channel channel);

	/**
	 * 剔除用户
	 * @param userId 用户id
	 */
	void kickUser(String userId);

}
