package com.aihangxunxi.aitalk.im.config;

import com.aihangxunxi.aitalk.im.channel.ChannelConfiguration;
import com.aihangxunxi.aitalk.im.channel.ChannelManager;
import com.aihangxunxi.aitalk.im.channel.DefaultChannelManager;
import com.aihangxunxi.aitalk.im.config.condition.StandaloneCondition;
import com.github.benmanes.caffeine.cache.Cache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author chenqingze107@163.com
 * @version 2.0 2020/9/26 12:00 PM
 */
@Configuration
@Conditional(StandaloneCondition.class)
@Import(ChannelConfiguration.class)
public class StandaloneConfiguration {

	@Bean("channelManager")
	public ChannelManager defaultChannelManager(Cache localChannelCache) {
		return new DefaultChannelManager(localChannelCache);
	}

}
