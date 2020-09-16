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
public interface ChannelManager {

    /**
     * 向缓存中增加一个channel
     *
     * @param context
     */
    void addChannel(ChannelHandlerContext context);

    /**
     * 从缓存中移除一个channel
     *
     * @param context
     */
    void removeChannel(ChannelHandlerContext context);

    /**
     * 删除某用户的所有channel
     *
     * @param userId
     */
    void removeChannelByUserId(String userId);


    /**
     * 获取用户channel
     *
     * @param userId
     * @return
     */
    List<Channel> findChannelByUserId(String userId);

    /**
     * 剔除用户
     *
     * @param userId
     */
    void kickUser(String userId);
}
