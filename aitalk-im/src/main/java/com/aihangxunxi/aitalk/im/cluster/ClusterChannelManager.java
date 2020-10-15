package com.aihangxunxi.aitalk.im.cluster;

import com.aihangxunxi.aitalk.im.channel.ChannelConstant;
import com.aihangxunxi.aitalk.im.channel.DefaultChannelManager;
import com.github.benmanes.caffeine.cache.Cache;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 集群channel状态管理 todo:完善集群相关状态如下： 3、维护用户-主机关系到redis，及时缓存和清理 4、etcd同步im node节点服务状态，并负载均衡
 * 5、合理设置redis 用户数据超时时间并通过心跳机制剔除不在线的用户 6、群消息发送考虑废弃channelManager
 *
 * @author chenqingze107@163.com
 * @version 2.0 2020/9/25 2:49 PM
 */
public class ClusterChannelManager extends DefaultChannelManager {

	private final RedisTemplate<String, Object> userStatusRedisTemplate;

	public ClusterChannelManager(Cache<String, Channel> localChannelCache,
			RedisTemplate<String, Object> userStatusRedisTemplate) {
		super(localChannelCache);
		this.userStatusRedisTemplate = userStatusRedisTemplate;
	}

	@Override
	public void addChannel(ChannelHandlerContext context) {
		addChannel(context.channel());
	}

	@Override
	public void addChannel(Channel channel) {
		// 保存用户状态到redis 共享存储
		super.addChannel(channel);
		// 保存用户状态到redis 共享存储
		// todo: 按需保存更多信息到redis
		userStatusRedisTemplate.opsForValue().set(
				ClusterConstant.REDIS_USER_ID_PREFIX + channel.attr(ChannelConstant.USER_ID_ATTRIBUTE_KEY).get(),
				"本机节点，与etcd规则相同");
	}

	@Override
	public void removeChannel(ChannelHandlerContext context) {
		removeChannel(context.channel());
	}

	@Override
	public void removeChannel(Channel channel) {
		removeChannelByUserId(channel.attr(ChannelConstant.USER_ID_ATTRIBUTE_KEY).get());
	}

	@Override
	public void removeChannelByUserId(String userId) {
		super.removeChannelByUserId(userId);
		// 从redis 共享存储删除用户状态
		userStatusRedisTemplate.delete(ClusterConstant.REDIS_USER_ID_PREFIX + userId);
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
