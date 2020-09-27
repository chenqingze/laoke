package com.aihangxunxi.aitalk.im.config;

import com.aihangxunxi.aitalk.im.channel.ChannelManager;
import com.aihangxunxi.aitalk.im.cluster.ClusterChannelManager;
import com.aihangxunxi.aitalk.im.config.condition.ClusterCondition;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author chenqingze107@163.com
 * @version 2.0 2020/9/26 11:56 AM
 */
@Configuration
@Conditional(ClusterCondition.class)
public class ClusterConfiguration {

	private final String redisHost;

	private final int redisPort;

	private final int userNodeDb;

	private final int pubSubDb;

	public ClusterConfiguration(@Value("${server.redis.host}") String redisHost,
			@Value("${server.redis.port}") int redisPort, @Value("${server.redis.userNodeDb}") int userNodeDb,
			@Value("${server.redis.pubSubDb}") int pubSubDb) {
		this.redisHost = redisHost;
		this.redisPort = redisPort;
		this.userNodeDb = userNodeDb;
		this.pubSubDb = pubSubDb;
	}

	@Bean("channelManager")
	public ChannelManager clusterChannelManager() {
		return new ClusterChannelManager();
	}

	/**
	 * create redisTemplate with Jedis
	 */
	private RedisTemplate<String, Object> createTemplateWithJedis(String hostName, int port, int database) {
		RedisStandaloneConfiguration clientConfig = new RedisStandaloneConfiguration(hostName, port);
		clientConfig.setDatabase(database);
		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(clientConfig);
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		// 设置value为json序列化器
		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(
				Object.class);
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL,
				JsonTypeInfo.As.PROPERTY);
		jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
		redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
		// 设置key为string序列化器
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setConnectionFactory(jedisConnectionFactory);
		return redisTemplate;
	}

	/**
	 * Lettuce
	 */
	private RedisConnectionFactory createLettuceConnectionFactory(String hostName, int port, int database) {
		RedisStandaloneConfiguration clientConfig = new RedisStandaloneConfiguration(hostName, port);
		clientConfig.setDatabase(database);
		return new LettuceConnectionFactory(clientConfig);
	}

	/**
	 * create redisTemplate with Lettuce
	 */
	private RedisTemplate<String, Object> createRedisTemplateWithLettuce(String hostName, int port, int database) {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		// 设置value为json序列化器
		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(
				Object.class);
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL,
				JsonTypeInfo.As.PROPERTY);
		jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
		redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
		// 设置key为string序列化器
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setConnectionFactory(this.createLettuceConnectionFactory(hostName, port, database));
		return redisTemplate;
	}

	/**
	 * for 缓存user-node 关系到redis
	 * @return {@link RedisTemplate}
	 */
	@Bean
	public RedisTemplate<String, Object> userNodeRedisTemplate() {
		return this.createRedisTemplateWithLettuce(redisHost, redisPort, userNodeDb);
	}

}
