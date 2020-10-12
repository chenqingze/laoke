package com.aihangxunxi.aitalk.im.cluster;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetAddress;

/**
 * AMQP P2P 消费者
 *
 * @author chenqingze107@163.com
 * @version 2.0 2020/9/27 10:36 AM
 */
public class RabbitMqConsumer implements Consumer {

	private static final Logger logger = LoggerFactory.getLogger(RabbitMqConsumer.class);

	private final Channel consumerChannel;

	public RabbitMqConsumer(Channel consumerChannel) {
		this.consumerChannel = consumerChannel;

		// todo:测试完删掉:start
		// DeliverCallback deliverCallback = (consumerTag, delivery) -> {
		// String message = new String(delivery.getBody(), "UTF-8");
		// logger.debug(" [x] Received {}:'{}'", delivery.getEnvelope().getRoutingKey(),
		// message);
		// };
		// try {
		// this.receive(deliverCallback);
		// }
		// catch (IOException e) {
		// e.printStackTrace();
		// }
		// todo:测试完删掉:end

	}

	// 接收数据
	@Override
	public void receive(DeliverCallback deliverCallback) throws IOException {
		// 回调举例：DeliverCallback demo
		// DeliverCallback deliverCallback = (consumerTag, delivery) -> {
		// Message message = Message.parseFrom(delivery.getBody());
		// logger.debug("Received message from {} ,message opCode is :'{}'",
		// delivery.getEnvelope().getRoutingKey(), message.getOpCode());
		// // todo:发送到客户端
		// };
		String queueName = InetAddress.getLocalHost().getHostName();
		this.consumerChannel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
		});
	}

}
