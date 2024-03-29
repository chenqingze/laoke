package com.aihangxunxi.aitalk.im.config;

import com.aihangxunxi.aitalk.im.channel.AitalkChannel;
import com.aihangxunxi.aitalk.im.channel.ChannelManager;
import com.aihangxunxi.aitalk.im.channel.DefaultAitalkChannel;
import com.aihangxunxi.aitalk.im.channel.DefaultChannelManager;
import com.aihangxunxi.aitalk.im.config.condition.StandaloneCondition;
import com.aihangxunxi.aitalk.im.manager.GroupManager;
import com.github.benmanes.caffeine.cache.Cache;
import io.netty.channel.Channel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author chenqingze107@163.com
 * @version 2.0 2020/9/26 12:00 PM
 */
@Configuration
@Import(ChannelConfiguration.class)
@Conditional(StandaloneCondition.class)
public class StandaloneConfiguration {

	@Bean("channelManager")
	public ChannelManager defaultChannelManager(Cache<String, Channel> localChannelCache) {
		return new DefaultChannelManager(localChannelCache);
	}

	@Bean("aitalkChannel")
	public AitalkChannel defaultAitalkChannel(ChannelManager channelManager, GroupManager groupManager) {
		return new DefaultAitalkChannel(channelManager, groupManager);
	}

}
