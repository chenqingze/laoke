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
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
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

	public SystemInfoConsumer(@Value("${server.rabbitMq.host}")String host,
							  @Value("${server.rabbitMq.port}")String port,@Value("${server.rabbitMq.userName}")String userName,
							  @Value("${server.rabbitMq.password}")String password) {

		try {
			// 创建连接
			ConnectionFactory factory = new ConnectionFactory();
			// todo: 正确的地址
			factory.setHost(host);
			factory.setPort(Integer.parseInt(port));
			factory.setUsername(userName);
			factory.setPassword(password);
			// factory.setUri("amqp://guest:guest@192.168.30.153:5672");
			Connection connection = factory.newConnection();
			// 创建channel
			Channel rabbitMqChannel = connection.createChannel();
			// 声明交换机
			rabbitMqChannel.exchangeDeclare(EXCHANGE_NAME, "topic", true);
			// 声明队列
			/*
			 * 参数1:队列名称 参数2:是否定义持久化队列(true 消息会持久化保存到服务器上) 参数3:是否独占本连接 参数4:是否在不使用的时候队列自动删除
			 * 参数5:其他参数
			 */
			rabbitMqChannel.queueDeclare(QUEUE_NAME, true, false, false, null);
			// 队列绑定到交换机上
			// rabbitMqChannel.queueBind(QUEUE_NAME,EXCHANGE_NAME,TOPIC);
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
			systemInfo.setStatus("no");
			User user = userRepository.getUserByUserId(systemInfo.getReceiverId());
			systemInfo.setMsgId(new ObjectId());
			if(user == null) {
				logger.info("---------------根据userId没有获取到用户信息--------------userId是>>>>>>{}", map.get("receiverId"));
				return;
			}
			systemInfo.setUserId(user.getId().toHexString());
			systemInfoRepository.saveSystemInfo(systemInfo);
			io.netty.channel.Channel channel = channelManager.findChannelByUserId(user.getId().toHexString());
			if (channel != null) {
				SendSystemInfoRequest sendSystemInfoRequest = msgAssembler.buildSendSystemInfoRequest(systemInfo);
				Message message = Message.newBuilder().setOpCode(OpCode.SYSTEM_INFO_NOTIFY)
						.setSendSystemInfoRequest(sendSystemInfoRequest).build();
				channel.writeAndFlush(message);
			}
			// 暂时保存至离线消息库
			systemInfoRepository.saveOfflineSystemInfo(systemInfo);
		};
	}
}
