package com.aihangxunxi.aitalk.im.assembler;

import com.aihangxunxi.aitalk.im.protocol.buffers.*;
import com.aihangxunxi.aitalk.storage.model.Group;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenqingze107@163.com
 * @version 2.0
 */
@Component
public class GroupAssembler {

	public Message QueryUserGroupsBuilder(Long userId, List<Group> list) {
		List<com.aihangxunxi.aitalk.im.protocol.buffers.Group> groups = new ArrayList<>();
		list.stream().forEach(group -> {
			com.aihangxunxi.aitalk.im.protocol.buffers.Group.newBuilder().setGroupNo(group.getGroupNo())
					.setId(group.getId().toString()).setName(group.getName()).setNotice(group.getNotice())
					.setOwner(group.getOwner().toString())
					.setSetting(buildGroupSetting(group.getGroupSetting().isMute(),group.getGroupSetting().isConfirmJoin()))
					.build();

		});

		return Message.newBuilder()
				.setQueryUserGroupAck(QueryUserGroupsAck.newBuilder().setUserId(userId).addAllGroups(groups).build())
				.build();

	}


	public GroupSetting buildGroupSetting(boolean isMute,boolean isConfirmJoin){
		return GroupSetting.newBuilder()
				.setIsConfirmJoin(isConfirmJoin)
				.setIsMute(isMute)
				.build();
	}

}
