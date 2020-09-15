package com.aihangxunxi.aitalk.storage.model;

import org.bson.types.ObjectId;

/**
 * 临时会话，咨询等等
 *
 * @author chenqingze107@163.com
 * @version 1.0
 */
public class TmpConversation extends BaseModel {

	private ObjectId id;

	private ObjectId requesterId;

	private ObjectId addresseeId;

	private Long createdAt;

	private Long updatedAt;

	private Long consultDirection;

}
