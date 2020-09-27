package com.aihangxunxi.aitalk.im.handler;

import com.aihangxunxi.aitalk.im.assembler.InvitationAssembler;
import com.aihangxunxi.aitalk.im.channel.ChannelManager;
import com.aihangxunxi.aitalk.im.protocol.buffers.FriendInvitationAcceptRequest;
import com.aihangxunxi.aitalk.im.protocol.buffers.Message;
import com.aihangxunxi.aitalk.im.protocol.buffers.OpCode;
import com.aihangxunxi.aitalk.storage.constant.InviteStatus;
import com.aihangxunxi.aitalk.storage.constant.InviteType;
import com.aihangxunxi.aitalk.storage.model.Friend;
import com.aihangxunxi.aitalk.storage.model.Invitation;
import com.aihangxunxi.aitalk.storage.model.User;
import com.aihangxunxi.aitalk.storage.repository.FriendRepository;
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
public class InvitationAcceptHandler extends ChannelInboundHandlerAdapter {

	private static final Logger logger = LoggerFactory.getLogger(InvitationAcceptHandler.class);

	@Resource
	private ChannelManager channelManager;

	@Resource
	private UserRepository userRepository;

	@Resource
	private InvitationRepository invitationRepository;

	@Resource
	private FriendRepository friendRepository;

	@Resource
	private InvitationAssembler invitationAssembler;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof Message && ((Message) msg).getOpCode() == OpCode.FRIEND_INVITATION_ACCEPT_REQUEST) {
			FriendInvitationAcceptRequest fiar = ((Message) msg).getFriendInvitationAcceptRequest();

			String id = fiar.getId();
			Invitation invitation = invitationRepository.updateInviteStatus(id, InviteStatus.ACCEPTED.name());

			if (invitation != null) {
				Friend friend = new Friend();
				// todo:渲染friend 并保存(两条记录)
				// friendRepository.save(friend);

				Message message = invitationAssembler.friendInvitationAcceptAck(id, friend, ((Message) msg).getSeq());
				ctx.writeAndFlush(message);

				User user = userRepository.getUserById(Long.valueOf(invitation.getRequesterId()));
				Channel addresseeChannel = channelManager.findChannelByUid(user.getUid().toHexString());
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
