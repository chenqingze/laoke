package com.aihangxunxi.aitalk.storage.model;

import com.aihangxunxi.aitalk.storage.constant.InviteStatus;
import com.aihangxunxi.aitalk.storage.constant.InviteType;
import org.bson.types.ObjectId;

/**
 * @author chenqingze107@163.com
 * @version 2.0
 */
public class Invitation extends BaseModel {

	private ObjectId id;

	private Long requesterId;

	private String requesterAlias;

	private String requesterNickname;

	private Long addresseeId;

	private String addresseeAlias;

	private String addresseeNickname;

	private String content;

	private InviteStatus inviteStatus;// 前端需要是否已读状态字段

	private InviteType inviteType;

	private Long createdAt;

	private Long updatedAt;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public Long getRequesterId() {
		return requesterId;
	}

	public void setRequesterId(Long requesterId) {
		this.requesterId = requesterId;
	}

	public String getRequesterAlias() {
		return requesterAlias;
	}

	public void setRequesterAlias(String requesterAlias) {
		this.requesterAlias = requesterAlias;
	}

	public String getRequesterNickname() {
		return requesterNickname;
	}

	public void setRequesterNickname(String requesterNickname) {
		this.requesterNickname = requesterNickname;
	}

	public Long getAddresseeId() {
		return addresseeId;
	}

	public void setAddresseeId(Long addresseeId) {
		this.addresseeId = addresseeId;
	}

	public String getAddresseeAlias() {
		return addresseeAlias;
	}

	public void setAddresseeAlias(String addresseeAlias) {
		this.addresseeAlias = addresseeAlias;
	}

	public String getAddresseeNickname() {
		return addresseeNickname;
	}

	public void setAddresseeNickname(String addresseeNickname) {
		this.addresseeNickname = addresseeNickname;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public InviteStatus getInviteStatus() {
		return inviteStatus;
	}

	public void setInviteStatus(InviteStatus inviteStatus) {
		this.inviteStatus = inviteStatus;
	}

	public InviteType getInviteType() {
		return inviteType;
	}

	public void setInviteType(InviteType inviteType) {
		this.inviteType = inviteType;
	}

	public Long getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Long createdAt) {
		this.createdAt = createdAt;
	}

	public Long getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Long updatedAt) {
		this.updatedAt = updatedAt;
	}

}
