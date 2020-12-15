package com.aihangxunxi.aitalk.im.handler;

import com.aihangxunxi.aitalk.im.channel.ChannelManager;
import com.aihangxunxi.aitalk.im.protocol.buffers.Message;
import com.aihangxunxi.aitalk.im.protocol.buffers.OpCode;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.ref.Reference;

/**
 * @Author guoyongsheng Data: 2020/11/26
 * @Version 3.0
 */
public class HeartServiceHandler extends ChannelInboundHandlerAdapter {

	private static final Logger logger = LoggerFactory.getLogger(HeartServiceHandler.class);

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof Message && ((Message) msg).getOpCode() == OpCode.PING_REQUEST) {
			String sessionId = ((Message) msg).getPing().getSessionId();
			logger.info("=============sessionId>>" + sessionId);
			ReferenceCountUtil.release(msg);
			// ctx.pipeline().remove(this);
		}
		else {
			ctx.fireChannelRead(msg);
		}
	}

}
