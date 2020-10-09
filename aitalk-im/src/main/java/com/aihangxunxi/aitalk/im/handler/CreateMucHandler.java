package com.aihangxunxi.aitalk.im.handler;

import com.aihangxunxi.aitalk.im.channel.ChannelManager;
import com.aihangxunxi.aitalk.im.manager.GroupManager;
import com.aihangxunxi.aitalk.im.protocol.buffers.CreateGroupAck;
import com.aihangxunxi.aitalk.im.protocol.buffers.CreateGroupRequest;
import com.aihangxunxi.aitalk.im.protocol.buffers.Message;
import com.aihangxunxi.aitalk.im.protocol.buffers.OpCode;
import com.aihangxunxi.aitalk.storage.repository.GroupMemberRepository;
import com.aihangxunxi.aitalk.storage.repository.GroupRepository;
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
 * 创建多人聊天
 */
@Component
@ChannelHandler.Sharable
public class CreateMucHandler extends ChannelInboundHandlerAdapter {

	@Resource
	private GroupRepository groupRepository;

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
		if (msg instanceof Message && ((Message) msg).getOpCode() == OpCode.CREATE_GROUP_REQUEST) {
			try {
				// 判断当前已经创建了几个群
				String owner = ((Message) msg).getCreateGroupRequest().getOwner();
				if (groupRepository.checkUserOwnGroup(owner)) {
					String header = ((Message) msg).getCreateGroupRequest().getHeader();
					String name = ((Message) msg).getCreateGroupRequest().getName();
					ProtocolStringList users = ((Message) msg).getCreateGroupRequest().getUsersList();
					// 创建群
					String groupId = groupRepository.createMuc(owner, name, header);

					Channel channel = channelManager.findChannelByUid(owner);
					// 获取群主相关信息
					Map ownerMap = userRepository.queryUserById(Long.parseLong(owner));
					groupMemberRepository.saveUserJoinGroup(groupId, Long.parseLong(owner),
							ownerMap.get("uId").toString(), ownerMap.get("header").toString(),
							ownerMap.get("nickname").toString());

					if (channel != null) {
						groupManager.addChannel(groupId, channel);

					}
					// 保存群用户
					for (int i = 0; i < users.size(); i++) {
						Map map = userRepository.queryUserById(Long.parseLong(users.get(i)));
						// 保存群成员
						groupMemberRepository.saveUserJoinGroup(groupId, Long.parseLong(users.get(i)),
								map.get("uId").toString(), map.get("header").toString(),
								map.get("nickname").toString());
						Channel userChannel = channelManager.findChannelByUid(users.get(i));
						if (userChannel != null) {
							groupManager.addChannel(groupId, userChannel);

						}
					}

					Message msgRequest = Message.newBuilder().setOpCode(OpCode.CREATE_GROUP_REQUEST)
							.setSeq(((Message) msg).getSeq())
							.setCreateGroupRequest(CreateGroupRequest.newBuilder().setGroupId(groupId).setHeader(header)
									.setName(name).setOwner(owner).addAllUsers(users).build())
							.build();
					groupManager.sendGroupMsg(groupId, msgRequest);

					Message message = Message.newBuilder().setOpCode(OpCode.CREATE_GROUP_ACK)
							.setSeq(((Message) msg).getSeq())
							.setCreateGroupAck(CreateGroupAck.newBuilder().setMessage("创建成功").setSuccess("ok")
									.setHeader(header).setName(name).setGroupId(groupId).setOwner(owner).build())
							.build();
					ctx.writeAndFlush(message);
				}
				else {
					Message message = Message.newBuilder().setOpCode(OpCode.CREATE_GROUP_ACK)
							.setSeq(((Message) msg).getSeq())
							.setCreateGroupAck(
									CreateGroupAck.newBuilder().setSuccess("full").setMessage("您创建的群数量已达上限").build())
							.build();
					ctx.writeAndFlush(message);
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
