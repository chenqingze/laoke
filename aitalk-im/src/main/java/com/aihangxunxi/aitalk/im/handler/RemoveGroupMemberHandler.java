package com.aihangxunxi.aitalk.im.handler;

import com.aihangxunxi.aitalk.im.channel.ChannelManager;
import com.aihangxunxi.aitalk.im.manager.GroupManager;
import com.aihangxunxi.aitalk.im.protocol.buffers.Message;
import com.aihangxunxi.aitalk.im.protocol.buffers.OpCode;
import com.aihangxunxi.aitalk.im.protocol.buffers.RemoveGroupMemberAck;
import com.aihangxunxi.aitalk.im.protocol.buffers.RemoveGroupMemberRequest;
import com.aihangxunxi.aitalk.storage.repository.GroupMemberRepository;
import com.aihangxunxi.aitalk.storage.repository.UserRepository;
import com.google.protobuf.ProtocolStringList;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 移除群成员
 */
@Component
@ChannelHandler.Sharable
public class RemoveGroupMemberHandler extends ChannelInboundHandlerAdapter {

	@Resource
	private GroupMemberRepository groupMemberRepository;

	@Resource
	private UserRepository userRepository;

	@Resource
	private GroupManager groupManager;

	@Resource
	private ChannelManager channelManager;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof Message && ((Message) msg).getOpCode() == OpCode.DETACH_USER_FROM_GROUP_REQUEST) {
			try {
				String groupId = ((Message) msg).getRemoveGroupMemberRequest().getGroupId();
				ProtocolStringList users = ((Message) msg).getRemoveGroupMemberRequest().getUserList();
				String currentUser = ((Message) msg).getInvitationJoinGroupRequest().getCurrentUser();
				String content = "";
				for (int i = 0; i < users.size(); i++) {
					Map userMap = userRepository.queryUserById(Long.parseLong(users.get(i)));
					content += userMap.get("nickname") + (users.size() - 1 == i ? "" : "、");

					// 从数据库中移除
					groupMemberRepository.removeGroupMember(groupId, Long.parseLong(users.get(i)));
				}
				content += " 已被移除群聊";
				Message groupMsg = Message.newBuilder().setOpCode(OpCode.DETACH_USER_FROM_GROUP_REQUEST)
						.setSeq(((Message) msg).getSeq())
						.setRemoveGroupMemberRequest(RemoveGroupMemberRequest.newBuilder().setCurrentUser(currentUser)
								.setGroupId(groupId).addAllUser(users).setMessage(content).setSuccess("ok").build())
						.build();
				groupManager.sendGroupMsg(groupId, groupMsg);

				for (int i = 0; i < users.size(); i++) {
					Channel channel = channelManager.findChannelByUid(users.get(i));
					if (channel != null) {
						groupManager.removeChannel(groupId, channel);
					}
				}

				Message ack = Message.newBuilder().setSeq(((Message) msg).getSeq())
						.setOpCode(OpCode.DETACH_USER_FROM_GROUP_ACK).setRemoveGroupMemberAck(RemoveGroupMemberAck
								.newBuilder().setGroupId(groupId).setMessage("移除成功").setSuccess("ok").build())
						.build();
				ctx.writeAndFlush(ack);
			}
			finally {
				ReferenceCountUtil.release(msg);

			}
		}
		else {
			ctx.fireChannelRead(msg);
		}
	}

}
