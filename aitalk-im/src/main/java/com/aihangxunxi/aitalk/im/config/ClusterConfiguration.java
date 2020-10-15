package com.aihangxunxi.aitalk.im.config;

import com.aihangxunxi.aitalk.im.channel.ChannelManager;
import com.aihangxunxi.aitalk.im.cluster.*;
import com.aihangxunxi.aitalk.im.config.condition.ClusterCondition;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.github.benmanes.caffeine.cache.Cache;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * @author chenqingze107@163.com
 * @version 2.0 2020/9/26 11:56 AM
 */
@Configuration
@Import(ChannelConfiguration.class)
@Conditional(ClusterCondition.class)
public class ClusterConfiguration {

	private final String redisHost;

	private final int redisPort;

	private final int userNodeDb;

	private final String rabbitMqUri;

	// private final String rabbitMqHost;

	// private final int rabbitMqPort;

	// private final String rabbitMqUserName;

	// private final String rabbitMqPassword;

	private final String[] endpoints;

	public ClusterConfiguration(@Value("${server.redis.host}") String redisHost,
			@Value("${server.redis.port}") int redisPort, @Value("${server.redis.userNodeDb}") int userNodeDb,
			@Value("${server.rabbitMq.uri}") String rabbitMqUri,
			// @Value("${server.rabbitMq.host}") String rabbitMqHost,
			// @Value("${server.rabbitMq.port}") int rabbitMqPort,
			// @Value("${server.rabbitMq.userName}") String rabbitMqUserName,
			// @Value("${server.rabbitMq.password}") String rabbitMqPassword,
			@Value("${server.etcd.endpoints}") String... endpoints) {
		this.redisHost = redisHost;
		this.redisPort = redisPort;
		this.userNodeDb = userNodeDb;
		this.rabbitMqUri = rabbitMqUri;

		// this.rabbitMqHost = rabbitMqHost;
		// this.rabbitMqPort = rabbitMqPort;
		// this.rabbitMqUserName = rabbitMqUserName;
		// this.rabbitMqPassword = rabbitMqPassword;
		this.endpoints = endpoints;
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
	public RedisTemplate<String, Object> userStatusRedisTemplate(RedisConnectionFactory lettuceConnectionFactory) {
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

	/**
	 * 节点状态保活
	 * @param localChannelCache
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 * @throws UnknownHostException
	 */
	@Bean(initMethod = "registerNode", destroyMethod = "UnregisterNode")
	public ClusterNodeStatusManager keepAlive(Cache<String, io.netty.channel.Channel> localChannelCache)
			throws InterruptedException, ExecutionException, UnknownHostException {
		return new ClusterNodeStatusManager(localChannelCache, endpoints);
	}

	/**
	 * 集群channel状态管理
	 * @param localChannelCache
	 * @param userStatusRedisTemplate
	 * @return
	 */
	@Bean("channelManager")
	public ChannelManager clusterChannelManager(Cache<String, io.netty.channel.Channel> localChannelCache,
			RedisTemplate<String, Object> userStatusRedisTemplate) {
		return new ClusterChannelManager(localChannelCache, userStatusRedisTemplate);
	}

	/**
	 * 与rabbitMq建立连接，并指定spring容器销毁时的销毁方法
	 * @return
	 */
	@Bean(destroyMethod = "close")
	public Connection rabbitConnection() {
		ConnectionFactory factory = new ConnectionFactory();
		// factory.setVirtualHost("/");
		// factory.setHost(this.rabbitMqHost);
		// factory.setPort(this.rabbitMqPort);
		// "guest"/"guest" by default, limited to localhost connections
		// factory.setUsername(this.rabbitMqUserName);
		// factory.setPassword(this.rabbitMqPassword);
		try {
			// Alternatively, URIs may be used:
			factory.setUri(this.rabbitMqUri);
			return factory.newConnection();
		}
		catch (IOException | TimeoutException | URISyntaxException | NoSuchAlgorithmException
				| KeyManagementException e) {
			e.printStackTrace();
			return null;// todo:自定义异常封装，直接抛出自定义异常
		}

	}

	/**
	 * 集群消息接受channel
	 * @param rabbitConnection
	 * @return
	 */
	@Bean
	public Channel consumerChannel(Connection rabbitConnection) {
		try {
			Channel channel = rabbitConnection.createChannel(1);
			String queueName = InetAddress.getLocalHost().getHostName();
			channel.queueDeclare(queueName, false, false, false, null);
			return channel;
		}
		catch (IOException e) {
			e.printStackTrace();
			return null;// todo:自定义异常封装，直接抛出自定义异常
		}
	}

	/**
	 * 集群消息接受消费者
	 * @param consumerChannel
	 * @return
	 */
	@Bean
	public RabbitMqConsumer rabbitMqConsumer(Channel consumerChannel) {
		return new RabbitMqConsumer(consumerChannel);
	}

	/**
	 * 集群消息转发生产者channel
	 * @param rabbitConnection
	 * @return
	 */
	@Bean
	public Channel producerChannel(Connection rabbitConnection) {
		try {
			Channel channel = rabbitConnection.createChannel(2);
			channel.exchangeDeclare(ClusterConstant.EXCHANGE_NAME, BuiltinExchangeType.DIRECT, false, false, null);
			return channel;
		}
		catch (IOException e) {
			e.printStackTrace();
			return null;// todo:自定义异常封装，直接抛出自定义异常
		}
	}

	/**
	 * 集群消息转发生产者
	 * @param producerChannel
	 * @return
	 */
	@Bean
	public RabbitMqProducer rabbitMqProducer(Channel producerChannel) {
		return new RabbitMqProducer(producerChannel);
	}

	/**
	 * 集群消息接受及转发处理器
	 * @param rabbitMqProducer
	 * @param rabbitMqConsumer
	 * @return
	 */
	@Bean(initMethod = "receive")
	public ClusterMessageRouter clusterMessageProcessor(RabbitMqProducer rabbitMqProducer,
			RabbitMqConsumer rabbitMqConsumer) {
		return new ClusterMessageRouter(rabbitMqProducer, rabbitMqConsumer);
	}

}
