package com.aihangxunxi.aitalk.im.manager;

import com.aihangxunxi.aitalk.im.protocol.buffers.Message;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class GroupManager {
    private final Object lock = new Object();

    private final ConcurrentHashMap<String, ChannelGroup> groupMap = new ConcurrentHashMap<>();

    public void addChannel(String groupId, Channel ch) {

        ChannelGroup channelGroup = null;
        synchronized (lock) {
            if (!groupMap.containsKey(groupId)) {
                channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
                groupMap.put(groupId, channelGroup);
            }
            else {
                channelGroup = groupMap.get(groupId);
            }
        }
        channelGroup.add(ch);
    }

    /**
     * 在群组中移除一个用户
     * @param groupId
     * @param ch
     */
    public void removeChannel(String groupId, Channel ch) {
        ChannelGroup channelGroup = groupMap.get(groupId);
        if (channelGroup != null) {
            channelGroup.remove(ch);
        }
    }

    /**
     * 从所有群聊中移除ch
     * @param ch
     */
    public void removeGroupAll(Channel ch) {
        Iterator<Map.Entry<String, ChannelGroup>> it = groupMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, ChannelGroup> entry = it.next();
            ChannelGroup channelGroup = entry.getValue();
            channelGroup.remove(ch);
        }
    }

    /**
     * 发送群推送
     * @param groupId
     * @param msg
     */
    public void sendGroupMsg(String groupId, Message msg) {
        ChannelGroup channelGroup = groupMap.get(groupId);
        if (channelGroup != null) {
            channelGroup.writeAndFlush(msg);
        }
    }
}
