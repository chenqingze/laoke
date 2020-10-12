package com.aihangxunxi.aitalk.im.handler;

import com.aihangxunxi.aitalk.im.manager.GroupManager;
import com.aihangxunxi.aitalk.im.protocol.buffers.ChangeMucConfirmJoinAck;
import com.aihangxunxi.aitalk.im.protocol.buffers.Message;
import com.aihangxunxi.aitalk.im.protocol.buffers.OpCode;
import com.aihangxunxi.aitalk.storage.repository.GroupRepository;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 群邀请确认
 */
@Component
@ChannelHandler.Sharable
public class ChangeMucJoinConfirmHandler extends ChannelInboundHandlerAdapter {

	@Resource
	private GroupRepository groupRepository;

	@Resource
	private GroupManager groupManager;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof Message && ((Message) msg).getOpCode() == OpCode.UPDATE_GROUP_CONFIRM_SETTING_REQUEST) {
			String groupId = ((Message) msg).getChangeMucConfirmJoinRequest().getGroupId();
			boolean confirm = ((Message) msg).getChangeMucConfirmJoinRequest().getConfirm();
			groupRepository.updateGroupConfirmJoin(groupId, confirm);
			groupManager.sendGroupMsg(groupId, (Message) msg);

			Message ack = Message.newBuilder().setOpCode(OpCode.UPDATE_GROUP_CONFIRM_SETTING_ACK)
					.setSeq(((Message) msg).getSeq()).setChangeMucConfirmJoinAck(ChangeMucConfirmJoinAck.newBuilder()
							.setConfirm(confirm).setGroupId(groupId).setMessage("修改成功").setSuccess("ok").build())
					.build();
			ctx.writeAndFlush(ack);
		}
		else {
			ctx.fireChannelRead(msg);
		}
	}

}
