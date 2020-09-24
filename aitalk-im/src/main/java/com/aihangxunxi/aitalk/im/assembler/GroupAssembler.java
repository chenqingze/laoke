package com.aihangxunxi.aitalk.im.assembler;

import com.aihangxunxi.aitalk.im.protocol.buffers.*;
import com.aihangxunxi.aitalk.storage.model.Groups;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenqingze107@163.com
 * @version 2.0
 */
@Component
public class GroupAssembler {

	public Message QueryUserGroupsBuilder(Long userId, long sessionId, List<Groups> list) {
		List<Group> groups = new ArrayList<>();

		for (int i = 0; i < list.size(); i++) {
			Groups group = list.get(i);
			groups.add(Group.newBuilder().setGroupNo(group.getGroupNo()).setId(group.getId().toHexString())
					.setPinyin(group.getPinyin()).setName(group.getName()).setNotice(group.getNotice())
					.setOwner(group.getOwner().toString()).setHeader(group.getHeader())
					.setSetting(buildGroupSetting(group.getGroupSetting().isMute(),
							group.getGroupSetting().isConfirmJoin()))
					.build());
		}

		return Message.newBuilder().setOpCode(OpCode.QUERY_USER_GROUP_ACK).setSeq(sessionId)
				.setQueryUserGroupAck(QueryUserGroupsAck.newBuilder().setUserId(userId).addAllGroups(groups).build())
				.build();

	}

	public GroupSetting buildGroupSetting(boolean isMute, boolean isConfirmJoin) {
		return GroupSetting.newBuilder().setIsConfirmJoin(isConfirmJoin).setIsMute(isMute).build();
	}

}
