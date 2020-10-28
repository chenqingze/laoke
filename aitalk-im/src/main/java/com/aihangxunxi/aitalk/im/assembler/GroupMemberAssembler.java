package com.aihangxunxi.aitalk.im.assembler;

import com.aihangxunxi.aitalk.im.protocol.buffers.Message;
import com.aihangxunxi.aitalk.im.protocol.buffers.MucMember;
import com.aihangxunxi.aitalk.im.protocol.buffers.OpCode;
import com.aihangxunxi.aitalk.im.protocol.buffers.PullMucMemberAck;
import com.aihangxunxi.aitalk.storage.model.Friend;
import com.aihangxunxi.aitalk.storage.model.GroupMember;
import com.aihangxunxi.aitalk.storage.model.User;
import com.aihangxunxi.aitalk.storage.repository.FriendRepository;
import com.aihangxunxi.aitalk.storage.repository.UserRepository;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 获取群成员
 */
@Component
public class GroupMemberAssembler {

	@Resource
	private UserRepository userRepository;

	@Resource
	private FriendRepository friendRepository;

	public Message buildGroupMemberList(List<GroupMember> list, String userId) {

		List<MucMember> mucMembers = new ArrayList<>();

		list.stream().forEach(groupMember -> {
			User user = userRepository.getUserByUserId(groupMember.getUserId());
			Friend friend = friendRepository.queryFriend(Long.parseLong(userId), groupMember.getUserId());
			MucMember mucMember = MucMember.newBuilder()
					.setAlias(friend != null ? friend.getFriendName() : user.getNickname())
					.setGroupId(groupMember.getGroupId().toHexString()).setFollow(friend != null)
					.setNickname(user.getNickname()).setProfile(user.getHeader())
					.setMemberId(user.getId().toHexString()).setGroupId(groupMember.getGroupId().toHexString())
					.setUserId(groupMember.getUserId().toString()).build();
			mucMembers.add(mucMember);
		});

		return Message
				.newBuilder().setOpCode(OpCode.PULL_MUC_MEMBER_ACK).setPullMucMemberAck(PullMucMemberAck.newBuilder()
						.setUserId(userId).setSuccess("ok").addAllMember(mucMembers).setMessage("初始化成功").build())
				.build();

	}

}
