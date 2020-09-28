package com.aihangxunxi.aitalk.im.handler;

import com.aihangxunxi.aitalk.im.channel.ChannelManager;
import com.aihangxunxi.aitalk.im.manager.GroupManager;
import com.aihangxunxi.aitalk.im.protocol.buffers.AskForJoinGroupAck;
import com.aihangxunxi.aitalk.im.protocol.buffers.AskFroJoinGroupRequest;
import com.aihangxunxi.aitalk.im.protocol.buffers.Message;
import com.aihangxunxi.aitalk.im.protocol.buffers.OpCode;
import com.aihangxunxi.aitalk.storage.constant.InviteStatus;
import com.aihangxunxi.aitalk.storage.constant.InviteType;
import com.aihangxunxi.aitalk.storage.model.Groups;
import com.aihangxunxi.aitalk.storage.model.Invitation;
import com.aihangxunxi.aitalk.storage.repository.GroupMemberRepository;
import com.aihangxunxi.aitalk.storage.repository.GroupRepository;
import com.aihangxunxi.aitalk.storage.repository.InvitationRepository;
import com.aihangxunxi.aitalk.storage.repository.UserRepository;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * 申请加入群聊
 */
@Component
@ChannelHandler.Sharable
public class AskFroJoinGroupHandler extends ChannelInboundHandlerAdapter {

	@Resource
	private GroupManager groupManager;

	@Resource
	private GroupRepository groupRepository;

	@Resource
	private ChannelManager channelManager;

	@Resource
	private UserRepository userRepository;

	@Resource
	private InvitationRepository invitationRepository;

	@Resource
	private GroupMemberRepository groupMemberRepository;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof Message && ((Message) msg).getOpCode() == OpCode.ASK_FOR_JOIN_GROUP_REQUEST) {
			try {
				String groupId = ((Message) msg).getAskForJoinGroupRequest().getGroupId();
				String userid = ((Message) msg).getAskForJoinGroupRequest().getUserId();
				String content = ((Message) msg).getAskForJoinGroupRequest().getMessage();
				// 获取用户细心
				Map map = userRepository.queryUserById(Long.parseLong(userid));
				// 获取群信息
				Groups groups = groupRepository.queryGroupInfo(groupId);

				// 判断群是否满员 todo 从redis获取人数
				if (!groupMemberRepository.checkGroupIsFull(groupId)) {

					Message ack = Message.newBuilder().setOpCode(OpCode.ASK_FOR_JOIN_GROUP_REQUEST)
							.setSeq(((Message) msg).getSeq())
							.setAskForJoinGroupAck(AskForJoinGroupAck.newBuilder().setGroupId(groupId).setUserId(userid)
									.setSuccess("full").setMessage("该群已满员").build())
							.build();
					ctx.writeAndFlush(ack);
				}
				else {
					// 判断用户是否存在群中
					if (this.groupRepository.checkUserInGroup(groupId, Long.parseLong(userid))) {
						Message groupAck = Message.newBuilder().setSeq(((Message) msg).getSeq())
								.setOpCode(OpCode.ASK_FOR_JOIN_GROUP_ACK)
								.setAskForJoinGroupAck(AskForJoinGroupAck.newBuilder().setGroupId(groupId)
										.setUserId(userid).setSuccess("in").setMessage("您已在该群中").build())
								.build();

						ctx.writeAndFlush(groupAck);
					}
					else {
						// 判断群是否开启群邀请确认
						// Groups groups = groupRepository.queryGroupInfo(groupId);
						if (groups.getGroupSetting().isConfirmJoin()) {
							// 当前群开启了邀请确认且 当前人不在群中
							Message message = Message.newBuilder().setSeq(((Message) msg).getSeq())
									.setOpCode(OpCode.ASK_FOR_JOIN_GROUP_ACK)
									.setAskForJoinGroupAck(AskForJoinGroupAck.newBuilder().setMessage("请等待群主确认")
											.setSuccess("wait").setUserId(userid).setGroupId(groupId).build())
									.build();
							ctx.writeAndFlush(message);

							Long owner = groups.getOwner();
							// 获取群管理频道
							Channel channel = channelManager.findChannelByUid(String.valueOf(owner));
							Message requestMessage = Message.newBuilder()
									.setAskForJoinGroupRequest(AskFroJoinGroupRequest.newBuilder().setGroupId(groupId)
											.setMessage(map.get("nickname") + "申请加入群聊").setUserId(userid)
											.setSuccess("wait").build())
									.build();
							channel.writeAndFlush(requestMessage);

							// 保存至数据库
							Invitation invitation = new Invitation();
							invitation.setRequesterId(Long.parseLong(userid));
							invitation.setRequesterAlias(map.get("nickname").toString());
							invitation.setRequesterNickname(map.get("nickname").toString());
							invitation.setRequesterNickname(map.get("header").toString());
							invitation.setAddresseeId(groupId);
							invitation.setAddresseeAlias(groups.getName());
							invitation.setAddresseeNickname(groups.getName());
							invitation.setAddresseeProfile(groups.getHeader());
							invitation.setInviteStatus(InviteStatus.REQUESTED);
							invitation.setReadStatus("0");
							invitation.setCreatedAt(new Date().getTime());
							invitation.setContent(content);
							invitation.setInviteType(InviteType.INVITE_MEMBER);
							invitationRepository.saveGroupInvitation(invitation);
						}
						else {
							// 当前群没有开启邀请确认
							Channel channel = channelManager.findChannelByUid(userid);
							groupManager.addChannel(groupId, channel);

							// 保存至数据库
							groupMemberRepository.saveUserJoinGroup(groupId, Long.parseLong(userid),
									map.get("uId").toString(), map.get("header").toString(),
									map.get("alias").toString());
							// 发送群通知
							Message groupMsg = Message.newBuilder().setOpCode(OpCode.ASK_FOR_JOIN_GROUP_REQUEST)
									.setAskForJoinGroupRequest(AskFroJoinGroupRequest.newBuilder().setSuccess("ok")
											.setMessage(map.get("nickname") + "加入了群聊").setUserId(userid)
											.setGroupId(groupId).build())
									.build();

							groupManager.sendGroupMsg(groupId, groupMsg);
						}

					}
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
