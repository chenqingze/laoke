package com.aihangxunxi.aitalk.im.handler;

import com.aihangxunxi.aitalk.im.assembler.InvitationAssembler;
import com.aihangxunxi.aitalk.im.channel.ChannelManager;
import com.aihangxunxi.aitalk.im.protocol.buffers.FriendInvitationRequestRequest;
import com.aihangxunxi.aitalk.im.protocol.buffers.Message;
import com.aihangxunxi.aitalk.im.protocol.buffers.OpCode;
import com.aihangxunxi.aitalk.storage.constant.InviteStatus;
import com.aihangxunxi.aitalk.storage.constant.InviteType;
import com.aihangxunxi.aitalk.storage.model.Invitation;
import com.aihangxunxi.aitalk.storage.model.User;
import com.aihangxunxi.aitalk.storage.repository.InvitationRepository;
import com.aihangxunxi.aitalk.storage.repository.UserRepository;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.Instant;

@Component
@ChannelHandler.Sharable
public class InvitationDeclinedHandler extends ChannelInboundHandlerAdapter {

	private static final Logger logger = LoggerFactory.getLogger(InvitationDeclinedHandler.class);

	@Resource
	private ChannelManager channelManager;

	@Resource
	private UserRepository userRepository;

	@Resource
	private InvitationRepository invitationRepository;

	@Resource
	private InvitationAssembler invitationAssembler;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof Message && ((Message) msg).getOpCode() == OpCode.FRIEND_INVITATION_DECLINED_REQUEST) {

		}
		else {
			ctx.fireChannelRead(msg);
		}
	}

}
