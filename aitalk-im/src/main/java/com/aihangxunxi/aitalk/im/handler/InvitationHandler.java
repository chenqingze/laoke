package com.aihangxunxi.aitalk.im.handler;

import com.aihangxunxi.aitalk.im.protocol.buffers.Message;
import com.aihangxunxi.aitalk.im.protocol.buffers.OpCode;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class InvitationHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		 if(msg instanceof Message && ((Message) msg).getOpCode() == OpCode.FRIEND_INVITATION_REQUEST_REQUEST){

		 }
	}

}
