package com.aihangxunxi.aitalk.im.handler;

import com.aihangxunxi.aitalk.im.assembler.InvitationAssembler;
import com.aihangxunxi.aitalk.im.channel.ChannelManager;
import com.aihangxunxi.aitalk.im.protocol.buffers.*;
import com.aihangxunxi.aitalk.storage.constant.InviteStatus;
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
		if (msg instanceof Message && ((Message) msg).getOpCode() == OpCode.INVITATION_DECLINED_REQUEST) {
			InvitationDeclinedRequest fidr = ((Message) msg).getInvitationDeclinedRequest();

			String id = fidr.getId();
			Invitation invitation = invitationRepository.updateInviteStatus(id, InviteStatus.DECLINED.name());

			if (invitation != null) {

				Message message = invitationAssembler.buildInvitationDeclinedAck(id, ((Message) msg).getSeq());
				ctx.writeAndFlush(message);

				User user = userRepository.getUserByUserId(Long.valueOf(invitation.getRequesterId()));
				Channel addresseeChannel = channelManager.findChannelByUid(user.getId().toHexString());
				if (addresseeChannel != null) {
					addresseeChannel.writeAndFlush(message);
				}

			}
			else {
				ctx.fireChannelRead(msg);
			}
		}
		else {
			ctx.fireChannelRead(msg);
		}
	}

}
