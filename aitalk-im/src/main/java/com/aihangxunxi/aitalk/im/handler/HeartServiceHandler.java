package com.aihangxunxi.aitalk.im.handler;

import com.aihangxunxi.aitalk.im.protocol.buffers.Message;
import com.aihangxunxi.aitalk.im.protocol.buffers.OpCode;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.springframework.stereotype.Component;

/**
 * @Author guoyongsheng Data: 2020/11/26
 * @Version 3.0
 */
@Component
@ChannelHandler.Sharable
public class HeartServiceHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof Message && ((Message) msg).getOpCode() == OpCode.PING_REQUEST) {
			ctx.pipeline().remove(this);
		}else {
			ctx.fireChannelRead(msg);
		}
	}

}
