package com.aihangxunxi.aitalk.im.consumer;

import com.aihangxunxi.aitalk.im.assembler.MsgAssembler;
import com.aihangxunxi.aitalk.im.channel.ChannelManager;
import com.aihangxunxi.aitalk.im.protocol.buffers.Message;
import com.aihangxunxi.aitalk.im.protocol.buffers.OpCode;
import com.aihangxunxi.aitalk.im.protocol.buffers.SendSystemInfoRequest;
import com.aihangxunxi.aitalk.im.utils.ObjectUtil;
import com.aihangxunxi.aitalk.storage.model.SystemInfo;
import com.aihangxunxi.aitalk.storage.model.User;
import com.aihangxunxi.aitalk.storage.repository.SystemInfoRepository;
import com.aihangxunxi.aitalk.storage.repository.UserRepository;
import com.github.benmanes.caffeine.cache.Cache;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @Author: suguodong Date: 2020/11/4 17:48
 * @Version: 3.0
 */
@Component
public class SystemInfoConsumer {

	private static final Logger logger = LoggerFactory.getLogger(SystemInfoConsumer.class);

	private final static String EXCHANGE_NAME = "SYSTEM_INFO";

	private final static String TOPIC = "SYSTEM_INFO";

	private final static String QUEUE_NAME = "SYSTEM_INFO";

	@Resource
	private UserRepository userRepository;

	@Resource
	private MsgAssembler msgAssembler;

	@Resource
	private SystemInfoRepository systemInfoRepository;

	@Resource
	private ChannelManager channelManager;

	public SystemInfoConsumer() {

		try {
			// 创建连接
			System.out.println("----------消费--------------------------启动的时候就走了");
			ConnectionFactory factory = new ConnectionFactory();
			// todo: 正确的地址
			factory.setHost("192.168.100.242");
			factory.setPort(8101);
			factory.setUsername("ahy");
			factory.setPassword("ahy");
			// factory.setUri("amqp://guest:guest@192.168.30.153:5672");
			Connection connection = factory.newConnection();
			// 创建channel
			Channel rabbitMqChannel = connection.createChannel();
			rabbitMqChannel.exchangeDeclare(EXCHANGE_NAME, "topic", true);
			// 绑定队列,并回调处理收到的消息
			rabbitMqChannel.basicConsume(QUEUE_NAME, true, systemInfoCallback(), consumerTag -> {
			});
		}
		catch (IOException | TimeoutException e) {
			e.printStackTrace();
		}

	}

	// 回调 todo:处理接收到的数据
	private DeliverCallback systemInfoCallback() {
		return (consumerTag, delivery) -> {
			// todo:获取/接受消息
			System.out.println("消费消息-------------" + delivery.getBody().toString());
			Map map = (Map) ObjectUtil.getObjectFromBytes(delivery.getBody());
			SystemInfo systemInfo = new SystemInfo();
			systemInfo.setOrderId(map.get("orderId").toString());
			systemInfo.setReceiverId((Long) (map.get("receiverId")));
			systemInfo.setTitle((String) map.get("title"));
			systemInfo.setContent((String) map.get("content"));
			systemInfo.setImagePath((String) map.get("imagePath"));
			systemInfo.setType((String) map.get("type"));
			long time = System.currentTimeMillis();
			long time2 = System.currentTimeMillis();
			systemInfo.setCreatedAt(time);
			systemInfo.setUpdatedAt(time2);
			systemInfo.setStatus("true");
			User user = userRepository.getUserByUserId(systemInfo.getReceiverId());
			// systemInfoRepository.saveSystemInfo(systemInfo);
			systemInfo.setId(new ObjectId());
			io.netty.channel.Channel channel = channelManager.findChannelByUserId(user.getId().toHexString());
			if (channel != null) {
				SendSystemInfoRequest sendSystemInfoRequest = msgAssembler.buildSendSystemInfoRequest(systemInfo);
				Message message = Message.newBuilder().setOpCode(OpCode.SYSTEM_INFO_NOTIFY)
						.setSendSystemInfoRequest(sendSystemInfoRequest).build();
				channel.writeAndFlush(message);
			}
			else {
				// systemInfoRepository.saveOfflineSystemInfo(systemInfo);
				System.out.println("反反复复付付付付付付付付付付付付付付付付付付付付付");
			}
		};
	}

}
