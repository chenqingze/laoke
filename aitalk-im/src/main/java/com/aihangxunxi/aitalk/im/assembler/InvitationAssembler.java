package com.aihangxunxi.aitalk.im.assembler;

import com.aihangxunxi.aitalk.im.protocol.buffers.FriendInvitationRequestAck;
import com.aihangxunxi.aitalk.im.protocol.buffers.FriendInvitationRequestRequest;
import com.aihangxunxi.aitalk.im.protocol.buffers.Message;
import com.aihangxunxi.aitalk.storage.model.Invitation;
import org.springframework.stereotype.Component;

/**
 * @author chenqingze107@163.com
 * @version 2.0
 */
@Component
public class InvitationAssembler {

	public Message friendInvitationRequestAck(Invitation invitation) {
		int res = invitation != null ? 1 : 0;
		return Message.newBuilder().setFriendInvitationRequestAck(FriendInvitationRequestAck.newBuilder()
				.setId(invitation.getId().toHexString()).setRequesterId(invitation.getRequesterId())
				.setRequesterAlias(invitation.getRequesterAlias())
				.setRequesterNickname(invitation.getRequesterNickname()).setAddresseeId(invitation.getAddresseeId())
				.setAddresseeAlias(invitation.getAddresseeAlias())
				.setAddresseeNickname(invitation.getAddresseeNickname()).setContent(invitation.getContent())
				.setInviteStatus(invitation.getInviteStatus().name()).setInviteType(invitation.getInviteType().name())
				.setCreatedAt(invitation.getCreatedAt()).setUpdatedAt(invitation.getUpdatedAt()).setRes(res).build())
				.build();
	}

}
