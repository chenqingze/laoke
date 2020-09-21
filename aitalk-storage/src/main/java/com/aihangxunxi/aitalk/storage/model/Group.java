package com.aihangxunxi.aitalk.storage.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * 群组关系
 *
 * @author chenqingze107@163.com
 * @version 2.0
 */
public class Group extends BaseModel {

	@JsonSerialize(using = ToStringSerializer.class)
	private ObjectId id;

	private String name;

	private String notice;

	@JsonSerialize(using = ToStringSerializer.class)
	private ObjectId owner;

	private String groupNo;

	private GroupSetting groupSetting;

	public class GroupSetting{
		private boolean isMute;
		private boolean isConfirmJoin;

		public boolean isMute() {
			return isMute;
		}

		public void setMute(boolean mute) {
			isMute = mute;
		}

		public boolean isConfirmJoin() {
			return isConfirmJoin;
		}

		public void setConfirmJoin(boolean confirmJoin) {
			isConfirmJoin = confirmJoin;
		}
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public ObjectId getOwner() {
		return owner;
	}

	public void setOwner(ObjectId owner) {
		this.owner = owner;
	}

	public String getGroupNo() {
		return groupNo;
	}

	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}

	public GroupSetting getGroupSetting() {
		return groupSetting;
	}

	public void setGroupSetting(GroupSetting groupSetting) {
		this.groupSetting = groupSetting;
	}
}
