package com.aihangxunxi.aitalk.im.consumer;

import com.aihangxunxi.aitalk.im.assembler.MsgAssembler;
import com.aihangxunxi.aitalk.im.channel.ChannelManager;
import com.aihangxunxi.aitalk.im.protocol.buffers.Message;
import com.aihangxunxi.aitalk.im.protocol.buffers.OpCode;
import com.aihangxunxi.aitalk.im.protocol.buffers.SendSystemInfoRequest;
import com.aihangxunxi.aitalk.im.utils.ObjectUtil;
import com.aihangxunxi.aitalk.storage.model.SystemInfo;
import com.aihangxunxi.aitalk.storage.repository.SystemInfoRepository;
import com.github.benmanes.caffeine.cache.Cache;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author: suguodong
 * Date:  2020/11/4 17:48
 * @Version: 3.0
 */
@Component
public class SystemInfoConsumer {
    private static final Logger logger = LoggerFactory.getLogger(SystemInfoConsumer.class);

    private final static String EXCHANGE_NAME = "SYSTEMINFO_EXCHANGER";
    private final static String TOPIC = "SYSTEMINFO";
    private final static String QUEUE_NAME = "SYSTEMINFO";

    @Resource
    private MsgAssembler msgAssembler;

    @Resource
    private SystemInfoRepository systemInfoRepository;

    @Resource
    private ChannelManager channelManager;

    public SystemInfoConsumer() {

        try {
            // 创建连接
            ConnectionFactory factory = new ConnectionFactory();
            // todo: 正确的地址
            factory.setHost("localhost");
            Connection connection = factory.newConnection();
            // 创建channel
            Channel rabbitMqChannel = connection.createChannel();
            rabbitMqChannel.exchangeDeclare(EXCHANGE_NAME, TOPIC);
            // 绑定队列,并回调处理收到的消息
            rabbitMqChannel.basicConsume(QUEUE_NAME, true, systemInfoCallback(), consumerTag -> {
            });
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }


    }

    // 回调  todo:处理接收到的数据
    private DeliverCallback systemInfoCallback(){
        return (consumerTag, delivery) -> {
//             todo:获取/接受消息
            SystemInfo systemInfo = (SystemInfo) ObjectUtil.getObjectFromBytes(delivery.getBody());
            systemInfoRepository.saveSystemInfo(systemInfo);
            io.netty.channel.Channel channel = channelManager.findChannelByUserId(systemInfo.getReceiverId().toString());
            if (channel != null) {
                SendSystemInfoRequest sendSystemInfoRequest = msgAssembler.buildSendSystemInfoRequest(systemInfo);
                Message message = Message.newBuilder().setOpCode(OpCode.MSG_READ_NOTIFY)
                        .setSendSystemInfoRequest(sendSystemInfoRequest).build();
                channel.writeAndFlush(message);
            } else {
                systemInfoRepository.saveOfflineSystemInfo(systemInfo);
            }
//            // todo:构建要发送的信息内容
//            Message message= Message.newBuilder().build();
//            // todo:发送消息的channel并发送消息
//            // eg: userId demo
//            String userId = "ffsfdsfdsfsddf";
//            io.netty.channel.Channel nettyChannel=channelManager.findChannelByUserId(userId);
//            nettyChannel.writeAndFlush(message);

        };
    }
}
