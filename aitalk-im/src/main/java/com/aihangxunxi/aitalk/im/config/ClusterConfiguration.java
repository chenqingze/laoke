package com.aihangxunxi.aitalk.im.config;

import com.aihangxunxi.aitalk.im.channel.ChannelManager;
import com.aihangxunxi.aitalk.im.cluster.ClusterChannelManager;
import com.aihangxunxi.aitalk.im.cluster.RabbitMQProducer;
import com.aihangxunxi.aitalk.im.config.condition.ClusterCondition;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @author chenqingze107@163.com
 * @version 2.0 2020/9/26 11:56 AM
 */
@Configuration
@Conditional(ClusterCondition.class)
@PropertySource("classpath:config.properties")
public class ClusterConfiguration {

	private final String redisHost;

	private final int redisPort;

	private final int userNodeDb;

	private final String rabbitMqHost;

	private final int rabbitMqPort;

	private final String rabbitMqUserName;

	private final String rabbitMqPassword;

	public ClusterConfiguration(@Value("${server.redis.host}") String redisHost,
			@Value("${server.redis.port}") int redisPort, @Value("${server.redis.userNodeDb}") int userNodeDb,
			@Value("${server.rabbitMq.host}") String rabbitMqHost, @Value("${server.rabbitMq.port}") int rabbitMqPort,
			@Value("${server.rabbitMq.userName}") String rabbitMqUserName,
			@Value("${server.rabbitMq.password}") String rabbitMqPassword

	) {
		this.redisHost = redisHost;
		this.redisPort = redisPort;
		this.userNodeDb = userNodeDb;

		this.rabbitMqHost = rabbitMqHost;
		this.rabbitMqPort = rabbitMqPort;
		this.rabbitMqUserName = rabbitMqUserName;
		this.rabbitMqPassword = rabbitMqPassword;
	}

	@Bean("channelManager")
	public ChannelManager clusterChannelManager() {
		return new ClusterChannelManager();
	}

	/**
	 * Lettuce
	 */
	@Bean
	public RedisConnectionFactory lettuceConnectionFactory() {
		RedisStandaloneConfiguration clientConfig = new RedisStandaloneConfiguration(redisHost, redisPort);
		clientConfig.setDatabase(userNodeDb);
		return new LettuceConnectionFactory(clientConfig);
	}

	/**
	 * 查询或缓存user-node 关系到redis
	 * @return {@link RedisTemplate}
	 */
	@Bean
	public RedisTemplate<String, Object> userNodeRedisTemplate(RedisConnectionFactory lettuceConnectionFactory) {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
		// 设置key为string序列化器
		redisTemplate.setKeySerializer(stringRedisSerializer);
		// 设置value为json序列化器
		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(
				Object.class);
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL,
				JsonTypeInfo.As.PROPERTY);
		jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
		redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
		redisTemplate.setConnectionFactory(lettuceConnectionFactory);

		return redisTemplate;
	}

	@Bean
	public ConnectionFactory rabbitConnectionFactory() {
		ConnectionFactory factory = new ConnectionFactory();

		// factory.setVirtualHost("/");
		factory.setHost(this.rabbitMqHost);
		factory.setPort(this.rabbitMqPort);
		// "guest"/"guest" by default, limited to localhost connections
		factory.setUsername(this.rabbitMqUserName);
		factory.setPassword(this.rabbitMqPassword);

		// Alternatively, URIs may be used:
		// ConnectionFactory factory = new ConnectionFactory();
		// factory.setUri("amqp://userName:password@hostName:portNumber/virtualHost");
		// Connection conn = factory.newConnection();

		return factory;
	}

	@Bean
	public Connection rabbitConnection(ConnectionFactory connectionFactory) throws IOException, TimeoutException {
		// Alternatively, URIs may be used:
		// ConnectionFactory factory = new ConnectionFactory();
		// factory.setUri("amqp://userName:password@hostName:portNumber/virtualHost");
		// Connection conn = factory.newConnection();
		return connectionFactory.newConnection();
	}

	@Bean
	public Map<String, Channel> rabbitChannelMap() {
		return new HashMap<>();
	}

	@Bean
	public RabbitMQProducer rabbitMQProducer(Connection rabbitConnection, Map<String, Channel> rabbitChannelMap) {
		return new RabbitMQProducer(rabbitConnection, rabbitChannelMap);
	}

}
