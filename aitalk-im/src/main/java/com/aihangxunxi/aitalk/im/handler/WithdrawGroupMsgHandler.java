package com.aihangxunxi.aitalk.im.handler;

import com.aihangxunxi.aitalk.im.manager.GroupManager;
import com.aihangxunxi.aitalk.im.protocol.buffers.Message;
import com.aihangxunxi.aitalk.im.protocol.buffers.OpCode;
import com.aihangxunxi.aitalk.im.protocol.buffers.WithdrawMucAck;
import com.aihangxunxi.aitalk.storage.repository.MucHistRepository;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 撤回消息
 */
@Component
@ChannelHandler.Sharable
public class WithdrawGroupMsgHandler extends ChannelInboundHandlerAdapter {

	@Resource
	private MucHistRepository mucHistRepository;

	@Resource
	private GroupManager groupManager;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof Message && ((Message) msg).getOpCode() == OpCode.WITHDRAW_MUC_REQUEST) {
			String groupId = ((Message) msg).getWithdrawMucRequest().getGroupId();
			String msgId = ((Message) msg).getWithdrawMucRequest().getMsgId();
			mucHistRepository.withdrawMucMsg(msgId);

			groupManager.sendGroupMsg(groupId, (Message) msg);

			Message ack = Message.newBuilder().setSeq(((Message) msg).getSeq()).setOpCode(OpCode.WITHDRAW_MUC_ACK)
					.setWithdrawMucAck(WithdrawMucAck.newBuilder().setGroupId(groupId).setMessage("撤回成功")
							.setMsgId(msgId).setSuccess("ok").build())
					.build();
			ctx.writeAndFlush(ack);
		}
		else {
			ctx.fireChannelRead(msg);
		}
	}

}
