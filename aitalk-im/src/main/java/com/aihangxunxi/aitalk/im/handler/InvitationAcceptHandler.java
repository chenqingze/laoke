package com.aihangxunxi.aitalk.im.handler;

import com.aihangxunxi.aitalk.im.assembler.InvitationAssembler;
import com.aihangxunxi.aitalk.im.channel.ChannelManager;
import com.aihangxunxi.aitalk.im.protocol.buffers.InvitationAcceptRequest;
import com.aihangxunxi.aitalk.im.protocol.buffers.Message;
import com.aihangxunxi.aitalk.im.protocol.buffers.OpCode;
import com.aihangxunxi.aitalk.storage.constant.InviteStatus;
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
		if (msg instanceof Message && ((Message) msg).getOpCode() == OpCode.INVITATION_ACCEPT_REQUEST) {
			InvitationAcceptRequest fiar = ((Message) msg).getInvitationAcceptRequest();

			String id = fiar.getId();
			Invitation invitation = invitationRepository.updateInviteStatus(id, InviteStatus.ACCEPTED.name());

			if (invitation != null) {
				Friend friend = new Friend();
				friend.setUserId(Long.valueOf(invitation.getAddresseeId()));

				User user = userRepository.getUserByUserId(invitation.getRequesterId());

				friend.setFriendObjectId(user.getId());

				friend.setFriendId(invitation.getRequesterId());
				friend.setFriendName(invitation.getRequesterNickname());
				friend.setFriendProfile(invitation.getRequesterProfile());
				friend.setAlias(invitation.getRequesterAlias());
				friend.setIsBlocked(0);
				friend.setIsMute(0);
				friend.setIsStickOnTop(0);
				friend.setStatus("effective");
				long currentTimeMillis = Instant.now().getEpochSecond();
				friend.setCreatedAt(currentTimeMillis);
				friend.setUpdatedAt(currentTimeMillis);
				boolean save = friendRepository.save(friend);

				Message message = null;
				if (save) {
					message = invitationAssembler.buildInvitationAcceptAck(id, friend, ((Message) msg).getSeq());
					ctx.writeAndFlush(message);
				}

				Channel addresseeChannel = channelManager.findChannelByUid(user.getId().toHexString());
				friend = new Friend();
				friend.setUserId(invitation.getRequesterId());

				user = userRepository.getUserByUserId(Long.valueOf(invitation.getAddresseeId()));

				friend.setFriendObjectId(user.getId());

				friend.setFriendId(Long.valueOf(invitation.getAddresseeId()));
				friend.setFriendName(invitation.getAddresseeNickname());
				friend.setFriendProfile(invitation.getAddresseeProfile());
				friend.setAlias(invitation.getAddresseeAlias());
				friend.setIsBlocked(0);
				friend.setIsMute(0);
				friend.setIsStickOnTop(0);
				friend.setStatus("effective");
				currentTimeMillis = Instant.now().getEpochSecond();
				friend.setCreatedAt(currentTimeMillis);
				friend.setUpdatedAt(currentTimeMillis);
				save = friendRepository.save(friend);
				if (addresseeChannel != null && save) {
					message = invitationAssembler.buildInvitationAcceptAck(id, friend, ((Message) msg).getSeq());
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
