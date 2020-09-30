package com.aihangxunxi.aitalk.im.handler;

import com.aihangxunxi.aitalk.im.channel.ChannelManager;
import com.aihangxunxi.aitalk.im.manager.GroupManager;
import com.aihangxunxi.aitalk.im.protocol.buffers.InvitationJoinGroupAck;
import com.aihangxunxi.aitalk.im.protocol.buffers.InvitationJoinGroupRequest;
import com.aihangxunxi.aitalk.im.protocol.buffers.Message;
import com.aihangxunxi.aitalk.im.protocol.buffers.OpCode;
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
 * 邀请好友加入群聊
 */
@Component
@ChannelHandler.Sharable
public class InvitationUserJoinGroupHandler extends ChannelInboundHandlerAdapter {

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
		if (msg instanceof Message && ((Message) msg).getOpCode() == OpCode.INVITATION_USER_JOIN_GROUP_REQUEST) {
			try {

				// 将用户加入到群中
				String groupId = ((Message) msg).getInvitationJoinGroupRequest().getGroupId();
				ProtocolStringList users = ((Message) msg).getInvitationJoinGroupRequest().getUserList();
				String currentUser = ((Message) msg).getInvitationJoinGroupRequest().getCurrentUser();

				// 根据当前用户id获取用户名
				Map map = userRepository.queryUserById(Long.parseLong(currentUser));

				// 判断群成员上限 todo 从redis取
				if (!groupMemberRepository.checkGroupIsFull(groupId)) {
					Message message = Message.newBuilder().setOpCode(OpCode.INVITATION_USER_JOIN_GROUP_ACK)
							.setSeq(((Message) msg).getSeq()).setInvitationJoinGroupAck(InvitationJoinGroupAck
									.newBuilder().setGroupId(groupId).setMessage("该群已满").setSuccess("full").build())
							.build();
					ctx.writeAndFlush(message);

				}
				else {
					// 该群没满 这时候要开始判断各种条件了
					String content = map.get("nickname") + " 邀请 ";

					for (int i = 0; i < users.size(); i++) {
						String user = users.get(i);
						Map userMap = userRepository.queryUserById(Long.parseLong(user));
						groupMemberRepository.saveUserJoinGroup(groupId, Long.parseLong(user),
								userMap.get("uId").toString(), userMap.get("header").toString(),
								userMap.get("nickname").toString());
						Channel channel = channelManager.findChannelByUid(user);
						if (channel != null) {
							groupManager.addChannel(groupId, channel);

						}
						content += userMap.get("nickname") + (users.size() - 1 == i ? "" : "、");
					}
					content += " 加入群聊";

					Message groupMsg = Message.newBuilder().setSeq(((Message) msg).getSeq())
							.setOpCode(((Message) msg).getOpCode())
							.setInvitationJoinGroupRequest(
									InvitationJoinGroupRequest.newBuilder().setSuccess("success").setMessage(content)
											.setCurrentUser(currentUser).addAllUser(users).setGroupId(groupId).build())
							.build();
					// 发送群通知 xxx加入了群聊
					groupManager.sendGroupMsg(groupId, groupMsg);

				}
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
