package com.aihangxunxi.aitalk.im.cluster;

import com.rabbitmq.client.DeliverCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 集群消息路由 集群节点消息转发及接受处理 todo:1、服务间信息转发处理， 2、维护rabbitMq队列状态，
 *
 * @author chenqingze107@163.com
 * @version 2.0 2020/10/12 10:59 AM
 */
public class ClusterMessageRouter {

	private static final Logger logger = LoggerFactory.getLogger(ClusterMessageRouter.class);

	private RabbitMqProducer rabbitMqProducer;

	private RabbitMqConsumer rabbitMqConsumer;

	public ClusterMessageRouter(RabbitMqProducer rabbitMqProducer, RabbitMqConsumer rabbitMqConsumer) {
		this.rabbitMqProducer = rabbitMqProducer;
		this.rabbitMqConsumer = rabbitMqConsumer;
	}

	// 发送消息到相关集群节点
	public void send() {
		// todo:rabbitMqProducer.send();
	}

	// 接受集群节点转发过来的消息
	public void receive() {
		System.out.println("接受消息开始了哇，哈哈哈哈哈哈！");
		// todo:rabbitMqConsumer.receive();
		DeliverCallback deliverCallback = (consumerTag, delivery) -> {
			String message = new String(delivery.getBody(), "UTF-8");
			logger.info(" [x] Received {}:'{}'", delivery.getEnvelope().getRoutingKey(), message);
		};
		try {
			rabbitMqConsumer.receive(deliverCallback);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

}
