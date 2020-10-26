package com.aihangxunxi.aitalk.im.handler;

import com.aihangxunxi.aitalk.im.channel.ChannelManager;
import com.aihangxunxi.aitalk.im.manager.GroupManager;
import com.aihangxunxi.aitalk.im.protocol.buffers.AccessUserJoinMucAck;
import com.aihangxunxi.aitalk.im.protocol.buffers.AccessUserJoinMucRequest;
import com.aihangxunxi.aitalk.im.protocol.buffers.Message;
import com.aihangxunxi.aitalk.im.protocol.buffers.OpCode;
import com.aihangxunxi.aitalk.storage.repository.GroupMemberRepository;
import com.aihangxunxi.aitalk.storage.repository.GroupRepository;
import com.aihangxunxi.aitalk.storage.repository.InvitationRepository;
import com.aihangxunxi.aitalk.storage.repository.UserRepository;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 同意加入群
 */
@Component
@ChannelHandler.Sharable
public class AccessUserJoinMucHandler extends ChannelInboundHandlerAdapter {

	@Resource
	private GroupRepository groupRepository;

	@Resource
	private GroupManager groupManager;

	@Resource
	private ChannelManager channelManager;

	@Resource
	private UserRepository userRepository;

	@Resource
	private GroupMemberRepository groupMemberRepository;

	@Resource
	private InvitationRepository invitationRepository;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof Message && ((Message) msg).getOpCode() == OpCode.ACCESS_USER_JOIN_GROUP_REQUEST) {
			String groupId = ((Message) msg).getAccessUserJoinMucRequest().getGroupId();
			String userId = ((Message) msg).getAccessUserJoinMucRequest().getUserId();
			// 判断是否已经加入群
			if (groupRepository.checkUserInGroup(groupId, Long.parseLong(userId))) {
				// 更新为已加入
				invitationRepository.updateGroupInvitationStatus(groupId, Long.parseLong(userId));

				Message ack = Message.newBuilder().setSeq(((Message) msg).getSeq())
						.setOpCode(OpCode.ACCESS_USER_JOIN_GROUP_ACK)
						.setAccessUserJoinMucAck(AccessUserJoinMucAck.newBuilder().setGroupId(groupId).setUserId(userId)
								.setSuccess("joined").setMessage("该用户已加入群聊").build())
						.build();
				ctx.writeAndFlush(ack);

			}
			else {
				// 判断群和人是否有效
				if (groupRepository.queryGroupIsOk(groupId)) {
					Channel channel = channelManager.findChannelByUserId(userId);
					if (channel != null) {
						groupManager.addChannel(groupId, channel);
					}
					Map map = userRepository.queryUserById(Long.parseLong(userId));

					String name = map.get("nickname").toString();
					// 保存群成员
					groupMemberRepository.saveUserJoinGroup(groupId, Long.parseLong(userId), map.get("uId").toString(),
							map.get("header").toString(), map.get("nickname").toString());

					Message groupMsg = Message.newBuilder().setSeq(((Message) msg).getSeq())
							.setOpCode(OpCode.ACCESS_USER_JOIN_GROUP_REQUEST)
							.setAccessUserJoinMucRequest(AccessUserJoinMucRequest.newBuilder().setGroupId(groupId)
									.setUserId(userId).setSuccess("ok").setMessage(name + "加入了群聊").build())
							.build();
					groupManager.sendGroupMsg(groupId, groupMsg);

					// 变更邀请状态为已加入
					invitationRepository.updateGroupInvitationStatus(groupId, Long.parseLong(userId));

					// 发送回执
					Message ack = Message.newBuilder().setOpCode(OpCode.ACCESS_USER_JOIN_GROUP_ACK)
							.setSeq(((Message) msg).getSeq())
							.setAccessUserJoinMucAck(AccessUserJoinMucAck.newBuilder().setGroupId(groupId)
									.setUserId(userId).setSuccess("ok").setMessage("已同意用户加入群").build())
							.build();
					ctx.writeAndFlush(ack);
				}
				else {
					invitationRepository.ignoredGroupInvitation(groupId, Long.parseLong(userId));

					Message ack = Message.newBuilder().setSeq(((Message) msg).getSeq())
							.setOpCode(OpCode.ACCESS_USER_JOIN_GROUP_ACK)
							.setAccessUserJoinMucAck(AccessUserJoinMucAck.newBuilder().setGroupId(groupId)
									.setUserId(userId).setSuccess("no").setMessage("该群已解散").build())
							.build();
					ctx.writeAndFlush(ack);
				}
			}

		}
		else {
			ctx.fireChannelRead(msg);
		}
	}

}
