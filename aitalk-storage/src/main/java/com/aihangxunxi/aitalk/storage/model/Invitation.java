package com.aihangxunxi.aitalk.storage.model;

import com.aihangxunxi.aitalk.storage.constant.InviteStatus;
import com.aihangxunxi.aitalk.storage.constant.InviteType;
import org.bson.types.ObjectId;

/**
 * @author chenqingze107@163.com
 * @version 1.0
 */
public class Invitation {

	private ObjectId id;

	private Long requesterId;

	private String requesterAlias;

	private Long addresseeId;

	private String addresseeAlias;

	private String status;

	private Long createdAt;

	private Long updatedAt;

	private String content;

	private InviteStatus inviteStatus;// 前端需要是否已读状态字段

	private InviteType inviteType;

}
