package com.aihangxunxi.aitalk.storage.model;

import org.bson.types.ObjectId;

/**
 * 好友关系
 *
 * @author chenqingze107@163.com
 * @version 2.0
 */
public class Friendship extends BaseModel {

	private ObjectId id;

	private ObjectId userId;

	private ObjectId friendId;

	private String alias;

	private Boolean isBlocked;

	private Boolean isMute;

	private Long createdAt;

	private Long updatedAt;

}
