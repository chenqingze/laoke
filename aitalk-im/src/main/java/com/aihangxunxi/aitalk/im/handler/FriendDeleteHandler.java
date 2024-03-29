package com.aihangxunxi.aitalk.im.handler;

import com.aihangxunxi.aitalk.im.assembler.InvitationAssembler;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.Instant;

@Component
@ChannelHandler.Sharable
public class FriendDeleteHandler extends ChannelInboundHandlerAdapter {

	private static final Logger logger = LoggerFactory.getLogger(FriendDeleteHandler.class);

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
		// if (msg instanceof Message && ((Message) msg).getOpCode() ==
		// OpCode.FRIEND_DELETE_REQUEST) {
		/*
		 * InvitationRequestRequest firr = ((Message) msg).getInvitationRequestRequest();
		 *
		 * // todo:获取当前用户信息 Long requesterId = 123l; String requesterNickname = "希克斯";
		 * String requesterProfile =
		 * "https://tupian.qqw21.com/article/UploadPic/2020-7/202073022592523433.jpg";
		 *
		 * // todo:获取 addressee 用户信息 String addresseeNickname = "jijiDown"; String
		 * addresseeProfile =
		 * "https://tupian.qqw21.com/article/UploadPic/2020-9/202092422443332504.jpg";
		 *
		 * long currentTimeMillis = Instant.now().getEpochSecond();
		 *
		 * Invitation invitation = new Invitation();
		 * invitation.setRequesterId(requesterId); invitation.setRequesterAlias("");
		 * invitation.setRequesterNickname(requesterNickname);
		 * invitation.setRequesterProfile(requesterProfile);
		 * invitation.setAddresseeId(String.valueOf(firr.getAddresseeId()));
		 * invitation.setAddresseeAlias(firr.getAddresseeAlias());
		 * invitation.setAddresseeNickname(addresseeNickname);
		 * invitation.setAddresseeProfile(addresseeProfile);
		 * invitation.setContent(firr.getContent());
		 * invitation.setInviteStatus(InviteStatus.REQUESTED);
		 * invitation.setInviteType(InviteType.INVITE_FRIEND);
		 * invitation.setCreatedAt(currentTimeMillis);
		 * invitation.setUpdatedAt(currentTimeMillis);
		 *
		 * boolean save = invitationRepository.save(invitation); if (save) { Message
		 * message = invitationAssembler.friendInvitationRequestAck(invitation, ((Message)
		 * msg).getSeq()); ctx.writeAndFlush(message);
		 *
		 * User user =
		 * userRepository.getUserById(Long.parseLong(invitation.getAddresseeId()));
		 *
		 * Channel addresseeChannel =
		 * channelManager.findChannelByUserId(user.getUid().toHexString()); if
		 * (addresseeChannel != null) { addresseeChannel.writeAndFlush(message); }
		 *
		 * } else { ctx.fireChannelRead(msg); }
		 */
		// }
		// else {
		// ctx.fireChannelRead(msg);
		// }
	}

}
