package com.aihangxunxi.aitalk.im.handler;

import com.aihangxunxi.aitalk.im.channel.ChannelConstant;
import com.aihangxunxi.aitalk.im.protocol.buffers.Message;
import com.aihangxunxi.aitalk.im.protocol.buffers.OpCode;
import com.aihangxunxi.aitalk.storage.repository.GroupMemberRepository;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 群已读更新
 */
@Component
@ChannelHandler.Sharable
public class MucReadNotifyHandler extends ChannelInboundHandlerAdapter {

	@Resource
	private GroupMemberRepository groupMemberRepository;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof Message && ((Message) msg).getOpCode() == OpCode.MUC_READ_NOTIFY_REQUEST) {
			String groupId = ((Message) msg).getMucReadNotifyRequest().getGroupId();
			String msgId = ((Message) msg).getMucReadNotifyRequest().getMsgId();
			String userObjectId = ctx.channel().attr(ChannelConstant.USER_ID_ATTRIBUTE_KEY).get();

			// 根据用户id和groupId更新人的
			groupMemberRepository.updateLastChat(groupId, userObjectId, msgId);

		}
		else {
			ctx.fireChannelRead(msg);
		}
	}

}
