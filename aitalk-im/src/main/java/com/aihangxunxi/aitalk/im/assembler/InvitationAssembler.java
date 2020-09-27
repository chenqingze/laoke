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

	public Message friendInvitationRequestAck(Invitation invitation, long seq) {
		int res = invitation != null ? 1 : 0;
		return Message.newBuilder().setOpCode(OpCode.FRIEND_INVITATION_REQUEST_ACK).setSeq(seq)
				.setFriendInvitationRequestAck(FriendInvitationRequestAck.newBuilder()
						.setInvitationProto(InvitationProto.newBuilder().setId(invitation.getId().toHexString())
								.setRequesterId(invitation.getRequesterId())
								.setRequesterAlias(invitation.getRequesterAlias())
								.setRequesterNickname(invitation.getRequesterNickname())
								.setRequesterProfile(invitation.getRequesterProfile())
								.setAddresseeId(Long.parseLong(invitation.getAddresseeId()))
								.setAddresseeAlias(invitation.getAddresseeAlias())
								.setAddresseeNickname(invitation.getAddresseeNickname())
								.setAddresseeProfile(invitation.getAddresseeProfile())
								.setContent(invitation.getContent())
								.setInviteStatus(invitation.getInviteStatus().name())
								.setInviteType(invitation.getInviteType().name())
								.setCreatedAt(invitation.getCreatedAt()).setUpdatedAt(invitation.getUpdatedAt())
								.build())
						.setRes(res).build())
				.build();
	}

	public Message friendInvitationAcceptAck(String id, Friend friend, long seq) {

		return Message.newBuilder().setOpCode(OpCode.FRIEND_INVITATION_ACCEPT_ACK).setSeq(seq)
				.setFriendInvitationAcceptAck(FriendInvitationAcceptAck.newBuilder().setId(id)
						// .setFriend(friend)
						.setRes(1).build())
				.build();
	}

}
