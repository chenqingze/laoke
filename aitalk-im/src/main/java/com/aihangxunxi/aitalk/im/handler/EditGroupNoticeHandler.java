package com.aihangxunxi.aitalk.im.handler;

import com.aihangxunxi.aitalk.im.manager.GroupManager;
import com.aihangxunxi.aitalk.im.protocol.buffers.ChangeGroupNoticeAck;
import com.aihangxunxi.aitalk.im.protocol.buffers.Message;
import com.aihangxunxi.aitalk.im.protocol.buffers.OpCode;
import com.aihangxunxi.aitalk.storage.repository.GroupRepository;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 更新群公告
 */
@Component
@ChannelHandler.Sharable
public class EditGroupNoticeHandler extends ChannelInboundHandlerAdapter {

	@Resource
	private GroupManager groupManager;

	@Resource
	private GroupRepository groupRepository;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof Message && ((Message) msg).getOpCode() == OpCode.EDIT_GROUP_NOTICE_REQUEST) {
			String groupId = ((Message) msg).getChangeGroupNoticeRequest().getGroupId();
			String notice = ((Message) msg).getChangeGroupNoticeRequest().getNotice();
			if (groupRepository.updateGroupNotice(groupId, notice)) {
				Message ack = Message.newBuilder().setSeq(((Message) msg).getSeq())
						.setOpCode(OpCode.EDIT_GROUP_NOTICE_ACK)
						.setChangeGroupNoticeAck(ChangeGroupNoticeAck.newBuilder().setGroupId(groupId).setNotice(notice)
								.setSuccess("ok").setMessage("修改成功").build())
						.build();
				ctx.writeAndFlush(ack);
				groupManager.sendGroupMsg(groupId, (Message) msg);
			}
			else {
				Message ack = Message.newBuilder().setSeq(((Message) msg).getSeq())
						.setOpCode(OpCode.EDIT_GROUP_NOTICE_ACK)
						.setChangeGroupNoticeAck(ChangeGroupNoticeAck.newBuilder().setGroupId(groupId).setNotice(notice)
								.setSuccess("no").setMessage("修改失败，请稍后再试").build())
						.build();
				ctx.writeAndFlush(ack);
			}

		}
		else {
			ctx.fireChannelRead(msg);
		}
	}

}
