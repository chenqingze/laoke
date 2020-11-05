package com.aihangxunxi.aitalk.im.handler;

import com.aihangxunxi.aitalk.im.channel.ChannelManager;
import com.aihangxunxi.aitalk.im.protocol.buffers.*;
import com.aihangxunxi.aitalk.storage.repository.MsgHistRepository;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 撤回咨询消息
 *
 * @Author guoyongsheng Data: 2020/11/4
 * @Version 3.0
 */
@Component
@ChannelHandler.Sharable
public class WithdrawConsultMsgHandler extends ChannelInboundHandlerAdapter {

	@Resource
	private ChannelManager channelManager;

	@Resource
	private MsgHistRepository msgHistRepository;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof Message && ((Message) msg).getOpCode() == OpCode.WITHDRAW_CONSULT_REQUEST) {
			String msgId = ((Message) msg).getWithdrawConsultRequest().getMsgId();
			// 接受者ID
			ObjectId receiverId = new ObjectId(((Message) msg).getWithdrawConsultRequest().getConversationId());
			msgHistRepository.withdrawConsultMsg(msgId);

			Message ack = Message.newBuilder().setSeq(((Message) msg).getSeq()).setOpCode(OpCode.WITHDRAW_CONSULT_ACK)
					.setWithdrawConsultAck(WithdrawConsultAck.newBuilder().setMsgId(msgId).setSuccess("ok").build())
					.build();
			ctx.writeAndFlush(ack);
			// 发送给被咨询者 咨询方向进行反转
			Channel addresseeChannel = channelManager.findChannelByUserId(receiverId.toHexString());
			if (addresseeChannel != null) {
				addresseeChannel.writeAndFlush(ack);
			}
			else {
				// todo:撤回的时候 如果对方不在线
			}
		}
		else {
			ctx.fireChannelRead(msg);
		}
	}

}
