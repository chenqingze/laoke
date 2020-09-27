package com.aihangxunxi.aitalk.im.handler;

import com.aihangxunxi.aitalk.im.protocol.buffers.Message;
import com.aihangxunxi.aitalk.im.protocol.buffers.OpCode;
import com.google.protobuf.ProtocolStringList;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.springframework.stereotype.Component;

/**
 * 邀请好友加入群聊
 */
@Component
@ChannelHandler.Sharable
public class InvitationUserJoinGroupHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof Message && ((Message) msg).getOpCode() == OpCode.INVITATION_USER_JOIN_GROUP_REQUEST) {
			// 将用户加入到群中
			String groupId = ((Message) msg).getInvitationJoinGroupRequest().getGroupId();
			ProtocolStringList users = ((Message) msg).getInvitationJoinGroupRequest().getUserList();
			String currentUser = ((Message) msg).getInvitationJoinGroupRequest().getCurrentUser();

			// 判断群成员上限 todo 从redis取

		}
		else {
			ctx.fireChannelRead(msg);
		}
	}

}
