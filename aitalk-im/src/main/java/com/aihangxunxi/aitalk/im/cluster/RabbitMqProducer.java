package com.aihangxunxi.aitalk.im.cluster;

import com.aihangxunxi.aitalk.im.protocol.buffers.Message;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * AMQP P2P 生产者
 *
 * @author chenqingze107@163.com
 * @version 2.0 2020/9/27 10:37 AM
 */
public class RabbitMqProducer implements Producer {

	private static final Logger logger = LoggerFactory.getLogger(RabbitMqProducer.class);

	private final Channel producerChannel;

	public RabbitMqProducer(Channel producerChannel) {
		this.producerChannel = producerChannel;
		try {
			this.send("node1", Message.newBuilder().build());
			this.send("node1", Message.newBuilder().build());
			this.send("node2", Message.newBuilder().build());
			this.send("node2", Message.newBuilder().build());
			this.send("node2", Message.newBuilder().build());
			this.send("node2", Message.newBuilder().build());
			this.send("node2", Message.newBuilder().build());
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void send(String nodeHostname, Message message) throws IOException {
		Channel channel = openChannel(nodeHostname);
		if (channel != null) {
			channel.basicPublish(ClusterConstant.EXCHANGE_NAME, nodeHostname, null, message.toByteArray());
		}
	}

	/**
	 * 获取channel 如果channel不存在，动态创建
	 * @param nodeHostname nodeHostname
	 * @return {@link Channel}
	 */
	private Channel openChannel(String nodeHostname) throws IOException {
		producerChannel.exchangeDeclare(ClusterConstant.EXCHANGE_NAME, BuiltinExchangeType.DIRECT, false, true, null);
		producerChannel.queueDeclare(nodeHostname, true, true, false, null);
		producerChannel.queueBind(nodeHostname, ClusterConstant.EXCHANGE_NAME, nodeHostname);
		return producerChannel;
	}

}
