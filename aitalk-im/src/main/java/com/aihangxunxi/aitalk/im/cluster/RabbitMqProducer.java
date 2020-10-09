package com.aihangxunxi.aitalk.im.cluster;

import com.aihangxunxi.aitalk.im.protocol.buffers.Message;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

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
		// todo:测试完删掉:start
		try {
			String hostName = InetAddress.getLocalHost().getHostName();
			producerChannel.queueDeclare(hostName, false, false, false, null);
			producerChannel.queueBind(hostName, ClusterConstant.EXCHANGE_NAME, hostName);
			while (true) {
				Thread.sleep(6000);
				producerChannel.basicPublish(ClusterConstant.EXCHANGE_NAME, hostName, null,
						"i wanna go home !".getBytes(StandardCharsets.UTF_8));
			}
		}
		catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		// todo:测试完删掉:end
	}

	@Override
	public void send(String nodeHostname, Message message) throws IOException {
		producerChannel.queueDeclare(nodeHostname, false, false, false, null);
		producerChannel.queueBind(nodeHostname, ClusterConstant.EXCHANGE_NAME, nodeHostname);
		producerChannel.basicPublish(ClusterConstant.EXCHANGE_NAME, nodeHostname, null, message.toByteArray());
	}

}
