package com.aihangxunxi.aitalk.storage.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author chenqingze107@163.com
 * @version 2.0
 */

@Configuration
@PropertySource("classpath:storage.properties")
@ComponentScan("com.aihangxunxi.aitalk.storage")
public class StorageConfiguration {

	private final String connectionString;

	private final String databaseName;

	private final String redisHost;

	private final int redisPort;

	private final int authDB;

	public StorageConfiguration(@Value("${storage.mongodb.connectionString}") String connectionString,
			@Value("${storage.mongodb.databaseName}") String databaseName,
			@Value("${storage.redis.host}") String redisHost, @Value("${storage.redis.port:6379}") int redisPort,
			@Value("${storage.redis.authDB}") int authDB) {
		this.connectionString = connectionString;
		this.databaseName = databaseName;
		this.redisHost = redisHost;
		this.redisPort = redisPort;
		this.authDB = authDB;
	}

	@Bean
	public MongoClient mongoClient() {
		ConnectionString connectionStr = new ConnectionString(connectionString);
		CodecRegistry pojoCodecRegistry = CodecRegistries
				.fromProviders(PojoCodecProvider.builder().automatic(true).build());
		CodecRegistry codecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
				pojoCodecRegistry);

		MongoClientSettings clientSettings = MongoClientSettings.builder().applyConnectionString(connectionStr)
				.codecRegistry(codecRegistry).build();
		return MongoClients.create(clientSettings);
	}

	@Bean
	public MongoDatabase aitalkDb(MongoClient mongoClient) {
		return mongoClient.getDatabase(databaseName);
	}

	/**
	 * Jedis
	 */
	//
	// @Bean
	// public RedisConnectionFactory authJedisConnectionFactory() {
	// RedisStandaloneConfiguration clientConfig = new
	// RedisStandaloneConfiguration(redisHost, redisPort);
	// clientConfig.setDatabase(redisDB);
	// return new JedisConnectionFactory(clientConfig);
	// }

	/**
	 * Lettuce
	 */
	@Bean
	public RedisConnectionFactory authLettuceConnectionFactory() {
		RedisStandaloneConfiguration clientConfig = new RedisStandaloneConfiguration(redisHost, redisPort);
		clientConfig.setDatabase(authDB);
		return new LettuceConnectionFactory(clientConfig);
	}

	@Bean
	public RedisTemplate<String, Object> authRedisTemplate(RedisConnectionFactory authLettuceConnectionFactory) {
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
		redisTemplate.setConnectionFactory(authLettuceConnectionFactory);

		return redisTemplate;
	}

}
