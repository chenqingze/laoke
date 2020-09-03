package com.aihangxunxi.aitalk.storage.model;

import com.aihangxunxi.aitalk.storage.constant.DeviceIdiom;
import com.aihangxunxi.aitalk.storage.constant.DevicePlatform;
import com.aihangxunxi.aitalk.storage.constant.Gender;
import com.sun.corba.se.impl.ior.OldJIDLObjectKeyTemplate;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * @author chenqingze107@163.com
 * @version 1.0
 */
public class User {

	private ObjectId uid;

	private Long userId;

	private Gender gender;

	private String nickname;

	private String profile;

	private String phone;

	private List<UserGroup> userGroups;

	private List<ObjectId> followings;

	private String deviceCode;

	private DeviceIdiom deviceIdiom;

	private DevicePlatform devicePlatform;

	public User() {
	}

	private class UserGroup {

		private ObjectId id;

		private String alias;

		private ObjectId lastAckMsgId;

		private Long lastAckMsgTime;

	}

}
