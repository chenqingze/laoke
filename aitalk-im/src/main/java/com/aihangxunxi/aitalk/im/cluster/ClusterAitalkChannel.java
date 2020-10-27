package com.aihangxunxi.aitalk.im.cluster;

import com.aihangxunxi.aitalk.im.channel.AitalkChannel;
import com.aihangxunxi.aitalk.im.channel.ChannelManager;
import com.aihangxunxi.aitalk.im.protocol.buffers.Message;
import io.netty.channel.Channel;

import java.io.IOException;

public class ClusterAitalkChannel implements AitalkChannel {

    private final ChannelManager channelManager;

    private final Producer producer;

    public ClusterAitalkChannel(ChannelManager channelManager, Producer producer) {
        this.channelManager = channelManager;
        this.producer = producer;
    }

    /**
     * 转发消息给目标用户，若目标用户不在线则直接存入数据库
     *
     * @param userId
     * @param message
     */
    @Override
    public void sendMsg(String userId, Message message) {
        Channel channel = this.channelManager.findChannelByUserId(userId);
        if (channel != null && channel.isWritable()) {
            channel.writeAndFlush(message);
        } else {
            String redisImNode = this.channelManager.findNodeByUserId(userId);
            if (redisImNode != null && !redisImNode.isEmpty()) {
                try {
                    producer.send(redisImNode, message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //todo:存入数据库

    }

    @Override
    public void sendMucMsg(String mucId, Message message) {
        // todo:将群消息存入数据库群消息表

        // todo:获取群成员,然后检查群成员在线状 然后发送，最后更新在线成员群消息last_msg_id
    }

}
