package com.aihangxunxi.aitalk.im.handler;

import com.aihangxunxi.aitalk.im.protocol.buffers.Message;
import com.aihangxunxi.aitalk.im.protocol.buffers.OpCode;
import com.aihangxunxi.aitalk.im.protocol.buffers.RefuseUserJoinMucAck;
import com.aihangxunxi.aitalk.storage.repository.InvitationRepository;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 拒绝加入
 */
@Component
@ChannelHandler.Sharable
public class RefuseUserJoinMucHandler extends ChannelInboundHandlerAdapter {

	@Resource
	private InvitationRepository invitationRepository;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof Message && ((Message) msg).getOpCode() == OpCode.REFUSE_USER_JOIN_MUC_REQUEST) {
			String groupId = ((Message) msg).getRefuseUserJoinMucRequest().getGroupId();
			String userId = ((Message) msg).getRefuseUserJoinMucRequest().getUserId();

			if (invitationRepository.declinedGroupInvitation(groupId, Long.parseLong(userId))) {
				Message ack = Message.newBuilder().setSeq(((Message) msg).getSeq())
						.setOpCode(OpCode.REFUSE_USER_JOIN_MUC_ACK)
						.setRefuseUserJoinMucAck(RefuseUserJoinMucAck.newBuilder().setGroupId(groupId).setUserId(userId)
								.setSuccess("ok").setMessage("已拒绝此用户加入群").build())
						.build();
				ctx.writeAndFlush(ack);
			}
			else {
				Message ack = Message.newBuilder().setSeq(((Message) msg).getSeq())
						.setOpCode(OpCode.REFUSE_USER_JOIN_MUC_ACK)
						.setRefuseUserJoinMucAck(RefuseUserJoinMucAck.newBuilder().setGroupId(groupId).setUserId(userId)
								.setSuccess("no").setMessage("拒绝失败，请稍后再试").build())
						.build();
				ctx.writeAndFlush(ack);
			}

		}
		else {
			ctx.fireChannelRead(msg);
		}
	}

}
