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
public class Groups extends BaseModel {

	@JsonSerialize(using = ToStringSerializer.class)
	private ObjectId id;

	private String name;

	private String notice;

	@JsonSerialize(using = ToStringSerializer.class)
	private Long owner;

	private String groupNo;

	private GroupSetting groupSetting;

	private String header;

	private String pinyin;

	private int count;

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

	public Long getOwner() {
		return owner;
	}

	public void setOwner(Long owner) {
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

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
