package com.aihangxunxi.aitalk.im.handler;

import com.aihangxunxi.aitalk.im.assembler.FansAssembler;
import com.aihangxunxi.aitalk.im.protocol.buffers.Message;
import com.aihangxunxi.aitalk.im.protocol.buffers.OpCode;
import com.aihangxunxi.aitalk.im.protocol.buffers.QueryFansAck;
import com.aihangxunxi.aitalk.storage.model.Fans;
import com.aihangxunxi.aitalk.storage.repository.FansRepository;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 获取粉丝
 */
@Component
@ChannelHandler.Sharable
public class QueryFansHandler extends ChannelInboundHandlerAdapter {

    @Resource
    private FansAssembler fansAssembler;
    @Resource
    private FansRepository fansRepository;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof Message && ((Message) msg).getOpCode() == OpCode.QUERY_USER_FANS_LIST_REQUEST) {
            String userId = ((Message) msg).getQueryFansRequest().getUserId();

            List<Fans> list = fansRepository.queryFans(Long.parseLong(userId));
            Message ack = Message.newBuilder()
                    .setOpCode(OpCode.QUERY_USER_FANS_LIST_ACK)
                    .setSeq(((Message) msg).getSeq())
                    .setQueryFansAck(QueryFansAck.newBuilder()
                            .addAllFans(fansAssembler.convertToMessage(list))
                            .build())
                    .build();
            ctx.writeAndFlush(ack);

        } else {
            ctx.fireChannelRead(msg);
        }
    }

}
