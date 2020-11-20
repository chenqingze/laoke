package com.aihangxunxi.aitalk.im.handler;

import com.aihangxunxi.aitalk.im.channel.ChannelConstant;
import com.aihangxunxi.aitalk.im.protocol.buffers.Message;
import com.aihangxunxi.aitalk.im.protocol.buffers.OpCode;
import com.aihangxunxi.aitalk.storage.repository.MsgHistRepository;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author guoyongsheng Data: 2020/11/20
 * @Version 3.0
 */
@Component
@ChannelHandler.Sharable
public class ConsultReadAckHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(P2PChatHandler.class);

    @Resource
    private MsgHistRepository msgHistRepository;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof Message && ((Message) msg).getOpCode() == OpCode.CONSULT_MSG_READ_ACK) {
            String msgId = ((Message) msg).getMsgReadAck().getMsgId();
            String userObjectId = ctx.channel().attr(ChannelConstant.USER_ID_ATTRIBUTE_KEY).get();
            // 此条消息改为已读，offlenMsg中删除
            msgHistRepository.deleteOfflineMsgById(new ObjectId(msgId));
        }
        else {
            ctx.fireChannelRead(msg);
        }
    }
}
