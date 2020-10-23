package com.aihangxunxi.aitalk.im.handler;

import com.aihangxunxi.aitalk.im.protocol.buffers.Message;
import com.aihangxunxi.aitalk.im.protocol.buffers.OpCode;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
@ChannelHandler.Sharable
public class PullMucMemberHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof Message && ((Message) msg).getOpCode() == OpCode.PULL_MUC_MEMBER_REQUEST) {

		}
		else {
			ctx.fireChannelRead(msg);
		}
	}

}
