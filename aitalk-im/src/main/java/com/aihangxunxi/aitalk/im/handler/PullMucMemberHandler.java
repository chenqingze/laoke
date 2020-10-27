package com.aihangxunxi.aitalk.im.handler;

import com.aihangxunxi.aitalk.im.assembler.GroupMemberAssembler;
import com.aihangxunxi.aitalk.im.protocol.buffers.Message;
import com.aihangxunxi.aitalk.im.protocol.buffers.OpCode;
import com.aihangxunxi.aitalk.storage.model.GroupMember;
import com.aihangxunxi.aitalk.storage.repository.GroupMemberRepository;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 拉取群成员
 */
@Component
@ChannelHandler.Sharable
public class PullMucMemberHandler extends ChannelInboundHandlerAdapter {

	@Resource
	private GroupMemberRepository groupMemberRepository;

	@Resource
	private GroupMemberAssembler groupMemberAssembler;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof Message && ((Message) msg).getOpCode() == OpCode.PULL_MUC_MEMBER_REQUEST) {
			String userId = ((Message) msg).getPullMucMemberRequest().getUserId();
			List<GroupMember> list = groupMemberRepository.queryGroupMembersList(Long.parseLong(userId));

			Message ack = groupMemberAssembler.buildGroupMemberList(list, userId);
			ctx.writeAndFlush(ack);
		}
		else {
			ctx.fireChannelRead(msg);
		}
	}

}
