package com.aihangxunxi.aitalk.im.assembler;

import com.aihangxunxi.aitalk.im.protocol.buffers.*;
import com.aihangxunxi.aitalk.storage.model.Friend;
import com.aihangxunxi.aitalk.storage.model.Invitation;
import org.springframework.stereotype.Component;

/**
 * @author chenqingze107@163.com
 * @version 2.0
 */
@Component
public class InvitationAssembler {

	public Message buildInvitationRequestAck(Invitation invitation, long seq) {
		int res = invitation != null ? 1 : 0;

		return Message.newBuilder().setOpCode(OpCode.INVITATION_REQUEST_ACK).setSeq(seq)
				.setInvitationRequestAck(InvitationRequestAck.newBuilder().setInvitationProto(InvitationProto
						.newBuilder().setId(invitation.getId().toHexString())
						.setRequesterId(invitation.getRequesterId()).setRequesterAlias(invitation.getRequesterAlias())
						.setRequesterNickname(invitation.getRequesterNickname())
						.setRequesterProfile(invitation.getRequesterProfile())
						.setAddresseeId(Long.parseLong(invitation.getAddresseeId()))
						.setAddresseeAlias(invitation.getAddresseeAlias())
						.setAddresseeNickname(invitation.getAddresseeNickname())
						.setAddresseeProfile(invitation.getAddresseeProfile()).setContent(invitation.getContent())
						.setInviteStatus(InviteStatus.forNumber(invitation.getInviteStatus().ordinal()))
						.setInviteType(invitation.getInviteType().name()).setCreatedAt(invitation.getCreatedAt())
						.setUpdatedAt(invitation.getUpdatedAt()).build()).setRes(res).build())
				.build();
	}

	public Message buildInvitationAcceptAck(String id, Friend friend, long seq) {

		return Message.newBuilder().setOpCode(OpCode.INVITATION_ACCEPT_ACK).setSeq(seq)
				.setInvitationAcceptAck(InvitationAcceptAck.newBuilder().setId(id)
						.setFriendProto(FriendProto.newBuilder().setId(friend.getId().toHexString())
								.setUserId(friend.getUserId()).setFriendId(friend.getFriendId())
								.setFriendName(friend.getFriendName()).setFriendProfile(friend.getFriendProfile())
								.setAlias(friend.getAlias()).setIsBlocked(friend.getIsBlocked())
								.setIsMute(friend.getIsMute()).setIsStickOnTop(friend.getIsStickOnTop())
								.setStatus(friend.getStatus()).setCreatedAt(friend.getCreatedAt())
								.setUpdatedAt(friend.getUpdatedAt()).build())
						.setRes(1).build())
				.build();
	}

	public Message buildInvitationDeclinedAck(String id, long seq) {
		return Message.newBuilder().setOpCode(OpCode.INVITATION_DECLINED_ACK).setSeq(seq)
				.setInvitationDeclinedAck(InvitationDeclinedAck.newBuilder().setId(id).setRes(1).build()).build();
	}

}
