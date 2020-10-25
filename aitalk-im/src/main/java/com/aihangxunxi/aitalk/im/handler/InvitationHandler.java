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
public class InvitationHandler extends ChannelInboundHandlerAdapter {

	private static final Logger logger = LoggerFactory.getLogger(InvitationHandler.class);

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
		if (msg instanceof Message && ((Message) msg).getOpCode() == OpCode.FRIEND_INVITATION_REQUEST_REQUEST) {
			FriendInvitationRequestRequest firr = ((Message) msg).getFriendInvitationRequestRequest();

			// todo:获取当前用户信息
			Long requesterId = 90001l;
			String requesterNickname = "希克斯";

			// todo:获取 addressee 用户信息
			String addresseeNickname = "jijiDown";

			long currentTimeMillis = Instant.now().getEpochSecond();

			Invitation invitation = new Invitation();
			invitation.setRequesterId(requesterId);
			invitation.setRequesterAlias("");
			invitation.setRequesterNickname(requesterNickname);
			invitation.setAddresseeId(firr.getAddresseeId());
			invitation.setAddresseeAlias(firr.getAddresseeAlias());
			invitation.setAddresseeNickname(addresseeNickname);
			invitation.setContent(firr.getContent());
			invitation.setInviteStatus(InviteStatus.REQUESTED);
			invitation.setInviteType(InviteType.INVITE_MEMBER);
			invitation.setCreatedAt(currentTimeMillis);
			invitation.setUpdatedAt(currentTimeMillis);

			boolean save = invitationRepository.save(invitation);
			if (save) {
				Message message = invitationAssembler.friendInvitationRequestAck(invitation, ((Message) msg).getSeq());
				ctx.writeAndFlush(message);

				User user = userRepository.getUserById(invitation.getAddresseeId());
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
