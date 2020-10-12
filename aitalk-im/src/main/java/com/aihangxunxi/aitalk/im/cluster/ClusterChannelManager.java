package com.aihangxunxi.aitalk.im.cluster;

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

	private final RedisTemplate<String, Object> userNodeRedisTemplate;

	private final ClusterNodeStatusManager clusterNodeStatusManager;

	public ClusterChannelManager(Cache<String, Channel> localChannelCache,
			RedisTemplate<String, Object> userNodeRedisTemplate, ClusterNodeStatusManager clusterNodeStatusManager) {
		super(localChannelCache);
		this.userNodeRedisTemplate = userNodeRedisTemplate;
		this.clusterNodeStatusManager = clusterNodeStatusManager;
	}

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
