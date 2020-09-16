package com.aihangxunxi.aitalk.im.channel;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

import java.util.List;

/**
 * 用户channel管理
 *
 * @author chenqingze107@163.com
 * @version 2.0
 */
public class DefaultChannelManager implements ChannelManager {
    @Override
    public void addChannel(ChannelHandlerContext context) {

    }

    @Override
    public void removeChannel(ChannelHandlerContext context) {

    }

    @Override
    public void removeChannelByUserId(String userId) {

    }

    @Override
    public List<Channel> findChannelByUserId(String userId) {
        return null;
    }

    @Override
    public void kickUser(String userId) {

    }
}
