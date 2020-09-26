package com.aihangxunxi.aitalk.im.cluster;

import com.aihangxunxi.aitalk.im.protocol.buffers.Message;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

/**
 * @author chenqingze107@163.com
 * @version 2.0 2020/9/26 2:31 PM
 */
public class RedisMessagePublisher implements MessagePublisher {

	@Resource
	private RedisTemplate<String, Object> redisTemplate;

	@Override
	public void publish(Message message) {
		redisTemplate.convertAndSend("msg", message);
	}

}
