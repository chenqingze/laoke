package com.aihangxunxi.aitalk.im.cluster;

import com.aihangxunxi.aitalk.im.protocol.buffers.Message;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * AMQP P2P 生产者
 *
 * @author chenqingze107@163.com
 * @version 2.0 2020/9/27 10:37 AM
 */
public class RabbitMQProducer implements Producer {

	private static final Logger logger = LoggerFactory.getLogger(RabbitMQProducer.class);

	private final ConnectionFactory rabbitConnectionFactory;

	private final Map<String, Channel> rabbitChannelMap;

	public RabbitMQProducer(ConnectionFactory rabbitConnectionFactory, Map<String, Channel> rabbitChannelMap) {
		this.rabbitConnectionFactory = rabbitConnectionFactory;
		this.rabbitChannelMap = rabbitChannelMap;
	}

	@Override
	public void send(String uidHexStr, Message message) throws IOException {
		Channel channel = openChannel(uidHexStr);
		if (channel != null) {
			channel.basicPublish(ClusterConstant.EXCHANGE_NAME, uidHexStr, null, message.toByteArray());
		}
	}

	/**
	 * 获取channel 如果channel不存在，动态创建
	 * @param routingKey
	 * @return
	 */
	private Channel openChannel(String routingKey) {

		// channel存在直接使用
		if (this.rabbitChannelMap.containsKey(routingKey)) {
			Channel channel = this.rabbitChannelMap.get(routingKey);
			if (channel.isOpen()) {
				return channel;
			}
			else {
				this.rabbitChannelMap.remove(routingKey);
				return null;
			}
		}
		else {// channel不存在则创建
			try (Connection connection = this.rabbitConnectionFactory.newConnection();
					Channel channel = connection.createChannel()) {
				channel.exchangeDeclare(ClusterConstant.EXCHANGE_NAME, BuiltinExchangeType.DIRECT, false, true, null);
				String queueName = channel.queueDeclare().getQueue();
				// channel.queueDeclare(queueName, true, false, false, null);
				channel.queueBind(queueName, ClusterConstant.EXCHANGE_NAME, routingKey);
				// 将创建的channel缓存起来便于后面使用
				this.rabbitChannelMap.put(routingKey, channel);
				return channel;
			}
			catch (IOException | TimeoutException e) {
				logger.error("rabbit 创建channel异常", e);
				return null;
			}
		}
	}

}
