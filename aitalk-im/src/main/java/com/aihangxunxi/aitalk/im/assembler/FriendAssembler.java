package com.aihangxunxi.aitalk.im.assembler;

import com.aihangxunxi.aitalk.im.protocol.buffers.*;
import com.aihangxunxi.aitalk.storage.model.Friend;
import com.aihangxunxi.aitalk.storage.model.Invitation;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenqingze107@163.com
 * @version 2.0
 */
@Component
public class FriendAssembler {

	public Message buildFriendAck(List<Friend> frientList) {
		List<FriendProto> friendProtos = new ArrayList<>();
		for (Friend friend : frientList) {
			FriendProto build = FriendProto.newBuilder().setId(friend.getId().toHexString())
					.setUserId(friend.getUserId()).setFriendObjectId(friend.getFriendObjectId().toHexString())
					.setFriendId(friend.getFriendId()).setFriendName(friend.getFriendName())
					.setFriendProfile(friend.getFriendProfile()).setAlias(friend.getAlias())
					.setIsBlocked(friend.getIsBlocked()).setIsMute(friend.getIsMute())
					.setIsStickOnTop(friend.getIsStickOnTop()).setStatus(friend.getStatus())
					.setCreatedAt(friend.getCreatedAt()).setUpdatedAt(friend.getUpdatedAt()).build();
			friendProtos.add(build);
		}

		Message build = Message.newBuilder().setOpCode(OpCode.FRIEND_ACK)
				.setFriendAck(FriendAck.newBuilder().addAllFriendProto(friendProtos).build()).build();
		return build;
	}

}
