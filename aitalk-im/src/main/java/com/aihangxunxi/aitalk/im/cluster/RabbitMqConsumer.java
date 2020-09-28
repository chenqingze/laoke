package com.aihangxunxi.aitalk.im.cluster;

import com.rabbitmq.client.Channel;

/**
 * AMQP P2P 消费者
 *
 * @author chenqingze107@163.com
 * @version 2.0 2020/9/27 10:36 AM
 */
public class RabbitMqConsumer implements Consumer {

	private final Channel consumerChannel;

	public RabbitMqConsumer(Channel consumerChannel) {
		this.consumerChannel = consumerChannel;
	}

	// 接收数据
	public void receive() {

	}

}
