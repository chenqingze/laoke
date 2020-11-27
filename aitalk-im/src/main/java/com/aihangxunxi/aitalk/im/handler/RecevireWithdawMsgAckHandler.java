package com.aihangxunxi.aitalk.im.handler;

import com.aihangxunxi.aitalk.im.channel.ChannelManager;
import com.aihangxunxi.aitalk.im.protocol.buffers.Message;
import com.aihangxunxi.aitalk.im.protocol.buffers.OpCode;
import com.aihangxunxi.aitalk.storage.repository.MsgHistRepository;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author guoyongsheng Data: 2020/11/27
 * @Version 3.0
 */
@Component
@ChannelHandler.Sharable
public class RecevireWithdawMsgAckHandler extends ChannelInboundHandlerAdapter {

	@Resource
	private ChannelManager channelManager;

	@Resource
	private MsgHistRepository msgHistRepository;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof Message && ((Message) msg).getOpCode() == OpCode.RECEIVE_WITHDRAW_MSG_ACK) {
			String msgId = ((Message) msg).getRecevieWithdrawMsgAck().getMsgId();
			// 将此条离线消息删除掉
			msgHistRepository.deleteOfflineMsgById(new ObjectId(msgId));
		}
		else {
			ctx.fireChannelRead(msg);
		}
	}

}
