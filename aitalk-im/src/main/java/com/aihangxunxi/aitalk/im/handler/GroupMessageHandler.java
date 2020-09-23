package com.aihangxunxi.aitalk.im.handler;

import com.aihangxunxi.aitalk.im.manager.GroupManager;
import com.aihangxunxi.aitalk.im.protocol.buffers.Message;
import com.aihangxunxi.aitalk.im.protocol.buffers.MsgAck;
import com.aihangxunxi.aitalk.im.protocol.buffers.OpCode;
import com.aihangxunxi.aitalk.storage.constant.ConversationType;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 群聊
 */
@Component
@ChannelHandler.Sharable
public class GroupMessageHandler extends ChannelInboundHandlerAdapter {

    @Resource
    private GroupManager groupManager;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof Message && ((Message) msg).getOpCode() == OpCode.MSG_REQUEST) {
            if (ConversationType.MUC.ordinal() == ((Message) msg).getMsgRequest().getConversationType()) {
                groupManager.sendGroupMsg(((Message) msg).getMsgRequest().getConversationId(), (Message) msg);
                MsgAck msgAck = MsgAck.newBuilder()
                        .setMsgId("")
                        .build();


            } else {
                ctx.fireChannelRead(msg);
            }
        } else {
            ctx.fireChannelRead(msg);
        }
    }
}
