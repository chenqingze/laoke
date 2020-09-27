package com.aihangxunxi.aitalk.im.cluster;

import com.aihangxunxi.aitalk.im.channel.DefaultChannelManager;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author chenqingze107@163.com
 * @version 2.0 2020/9/25 2:49 PM
 */
public class ClusterChannelManager extends DefaultChannelManager {

	private RedisTemplate<String, Object> userNodeRedisTemplate;

	private RedisTemplate<String, Object> pubSubRedisTemplate;

	@Override

	public void addChannel(ChannelHandlerContext context) {
		this.addChannel(context.channel());
	}

	@Override
	public void addChannel(Channel channel) {
		super.addChannel(channel);

	}

	@Override
	public void removeChannel(ChannelHandlerContext context) {

	}

	@Override
	public void removeChannel(Channel channel) {

	}

	@Override
	public void removeChannelByUserId(String userId) {

	}

	@Override
	public Channel findChannelByUid(String userId) {
		return null;
	}

	@Override
	public Long getLocalChannelCacheSize() {
		return null;
	}

	@Override
	public void kickUser(ChannelHandlerContext context) {

	}

	@Override
	public void kickUser(Channel channel) {

	}

	@Override
	public void kickUser(String userId) {

	}

}
