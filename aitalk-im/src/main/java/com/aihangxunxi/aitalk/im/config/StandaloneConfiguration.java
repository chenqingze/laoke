package com.aihangxunxi.aitalk.im.config;

import com.aihangxunxi.aitalk.im.channel.ChannelManager;
import com.aihangxunxi.aitalk.im.channel.DefaultChannelManager;
import com.aihangxunxi.aitalk.im.config.condition.StandaloneCondition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;

/**
 * @author chenqingze107@163.com
 * @version 2.0 2020/9/26 12:00 PM
 */
@Conditional(StandaloneCondition.class)
public class StandaloneConfiguration {

	@Bean("channelManager")
	public ChannelManager defaultChannelManager() {
		return new DefaultChannelManager();
	}

}
