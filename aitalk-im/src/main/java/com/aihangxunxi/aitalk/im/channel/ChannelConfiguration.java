package com.aihangxunxi.aitalk.im.channel;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.netty.channel.Channel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @author chenqingze107@163.com
 * @version 2.0
 */
@Configuration
public class ChannelConfiguration {

	@Bean
	public Cache<String, Channel> localChannelCache() {
		return Caffeine.newBuilder().expireAfterWrite(600000, TimeUnit.SECONDS).build();

	}

}
