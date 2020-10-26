package com.aihangxunxi.aitalk.im.handler;

import com.aihangxunxi.aitalk.im.assembler.InvitationAssembler;
import com.aihangxunxi.aitalk.im.channel.ChannelConstant;
import com.aihangxunxi.aitalk.im.channel.ChannelManager;
import com.aihangxunxi.aitalk.im.protocol.buffers.InvitationRequestRequest;
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
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.Instant;

@Component
@ChannelHandler.Sharable
public class InvitationRequestHandler extends ChannelInboundHandlerAdapter {

	private static final Logger logger = LoggerFactory.getLogger(InvitationRequestHandler.class);

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
		if (msg instanceof Message && ((Message) msg).getOpCode() == OpCode.INVITATION_REQUEST_REQUEST) {
			InvitationRequestRequest firr = ((Message) msg).getInvitationRequestRequest();

			String userObjectId = ctx.channel().attr(ChannelConstant.USER_ID_ATTRIBUTE_KEY).get();

			User user = userRepository.getUserById(new ObjectId(userObjectId));

			User addressee = userRepository.getUserByUserId(firr.getAddresseeId());

			// Invitation addresseeInvitation =
			// invitationRepository.getInvitation(user.getUserId(),addressee.getUserId());

			// invitationRepository.deleteInvitation(user.getUserId(),addressee.getUserId());

			long currentTimeMillis = Instant.now().getEpochSecond();

			Invitation invitation = new Invitation();
			invitation.setRequesterId(user.getUserId());
			invitation.setRequesterAlias("");
			invitation.setRequesterNickname(user.getNickname());
			invitation.setRequesterProfile(user.getHeader());
			invitation.setAddresseeId(String.valueOf(firr.getAddresseeId()));
			invitation.setAddresseeAlias(firr.getAddresseeAlias());
			invitation.setAddresseeNickname(addressee.getNickname());
			invitation.setAddresseeProfile(addressee.getHeader());
			invitation.setContent(firr.getContent());
			invitation.setInviteStatus(InviteStatus.REQUESTED);
			invitation.setInviteType(InviteType.INVITE_FRIEND);
			invitation.setCreatedAt(currentTimeMillis);
			invitation.setUpdatedAt(currentTimeMillis);

			boolean save = invitationRepository.save(invitation);
			if (save) {
				Message message = invitationAssembler.buildInvitationRequestAck(invitation, ((Message) msg).getSeq());
				ctx.writeAndFlush(message);

				Channel addresseeChannel = channelManager.findChannelByUserId(addressee.getId().toHexString());
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
