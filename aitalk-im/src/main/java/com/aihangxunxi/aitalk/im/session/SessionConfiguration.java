package com.aihangxunxi.aitalk.im.session;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Session 管理配置类
 *
 * @author chenqingze107@163.com
 * @version 1.0
 */

@Configuration
public class SessionConfiguration {

	/**
	 * todo:确定缓存策略,使用Cache、LoadingCache、AsyncCache还是Asynchronously session 缓存
	 * @return
	 */
	@Bean
	public Cache<String, Session> localSessionCache() {
		return Caffeine.newBuilder().expireAfterWrite(6000, TimeUnit.SECONDS).build();

	}

	/**
	 * todo:确定缓存策略,使用Cache、LoadingCache、AsyncCache还是Asynchronously 用户session集合缓存
	 * @return
	 */
	@Bean
	public Cache<Long, Set<String>> localUserSessionSetCache() {
		return Caffeine.newBuilder().expireAfterWrite(6000, TimeUnit.SECONDS).build();
	}

}
