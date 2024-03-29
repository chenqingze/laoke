package com.aihangxunxi.aitalk.im.handler;

import com.aihangxunxi.aitalk.im.assembler.GroupAssembler;
import com.aihangxunxi.aitalk.im.protocol.buffers.Message;
import com.aihangxunxi.aitalk.im.protocol.buffers.OpCode;
import com.aihangxunxi.aitalk.storage.repository.GroupRepository;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 初始化群聊
 */
@Component
@ChannelHandler.Sharable
public final class QueryUserGroupsHandler extends ChannelInboundHandlerAdapter {

	private static final Logger logger = LoggerFactory.getLogger(QueryUserGroupsHandler.class);

	@Resource
	private GroupRepository groupRepository;

	@Resource
	private GroupAssembler groupAssembler;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

		if (msg instanceof Message && ((Message) msg).getOpCode() == OpCode.QUERY_USER_GROUP_REQUEST) {
			try {
				String userId = ((Message) msg).getQueryUserGroupRequest().getUserId();
				Message queryUserGroupsAck = groupAssembler.QueryUserGroupsBuilder(userId, ((Message) msg).getSeq(),
						groupRepository.queryUserGroups(Long.parseLong(userId)));
				ctx.writeAndFlush(queryUserGroupsAck);
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
