package com.aihangxunxi.aitalk.im.handler;

import com.aihangxunxi.aitalk.im.manager.GroupManager;
import com.aihangxunxi.aitalk.im.protocol.buffers.ChangeMucMuteAck;
import com.aihangxunxi.aitalk.im.protocol.buffers.Message;
import com.aihangxunxi.aitalk.im.protocol.buffers.OpCode;
import com.aihangxunxi.aitalk.storage.repository.GroupRepository;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 更改群禁言设置
 */
@Component
@ChannelHandler.Sharable
public class ChangeMucMuteHandler extends ChannelInboundHandlerAdapter {

	@Resource
	private GroupRepository groupRepository;

	@Resource
	private GroupManager groupManager;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof Message && ((Message) msg).getOpCode() == OpCode.UPDATE_GROUP_MUTE_SETTING_REQUEST) {
			// 更新群表为禁言
			String groupId = ((Message) msg).getChangeMucMuteRequest().getGroupId();
			boolean mute = ((Message) msg).getChangeMucMuteRequest().getMute();

			groupRepository.updateGroupMute(groupId, mute);

			groupManager.sendGroupMsg(groupId, (Message) msg);

			Message ack = Message.newBuilder().setOpCode(OpCode.UPDATE_GROUP_MUTE_SETTING_ACK)
					.setSeq(((Message) msg).getSeq()).setChangeMucMuteAck(ChangeMucMuteAck.newBuilder()
							.setGroupId(groupId).setMessage("已开启禁言").setMute(mute).setSuccess("ok").build())
					.build();

			ctx.writeAndFlush(ack);

		}
		else {
			ctx.fireChannelRead(msg);
		}
	}

}
