package com.aihangxunxi.aitalk.im.handler;

import com.aihangxunxi.aitalk.im.assembler.MsgAssembler;
import com.aihangxunxi.aitalk.im.protocol.buffers.Message;
import com.aihangxunxi.aitalk.im.protocol.buffers.OpCode;
import com.aihangxunxi.aitalk.storage.model.GroupMember;
import com.aihangxunxi.aitalk.storage.model.MucHist;
import com.aihangxunxi.aitalk.storage.repository.GroupMemberRepository;
import com.aihangxunxi.aitalk.storage.repository.MucHistRepository;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 拉取群聊天记录
 */
@Component
@ChannelHandler.Sharable
public class InitMucHistHandler extends ChannelInboundHandlerAdapter {

	@Resource
	private GroupMemberRepository groupMemberRepository;

	@Resource
	private MucHistRepository mucHistRepository;

	@Resource
	private MsgAssembler msgAssembler;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof Message && ((Message) msg).getOpCode() == OpCode.PULL_MUC_HIST_REQUEST) {

			String userId = ((Message) msg).getPullMucHistRequest().getUserId();
			// 根据用户id获取最后一条已读的消息id
			List<GroupMember> list = groupMemberRepository.queryLastChatId(Long.parseLong(userId));
			List<MucHist> mucHists = new ArrayList<>();
			list.stream().forEach(groupMember -> {
				mucHists.addAll(
						mucHistRepository.queryMucHist(groupMember.getLastAckMsgTime(), groupMember.getGroupId()));
			});

			Message ack = msgAssembler.buildInitMucHist(mucHists, ((Message) msg).getSeq(), userId);
			ctx.writeAndFlush(ack);

		}
		else {
			ctx.fireChannelRead(msg);
		}
	}

}
