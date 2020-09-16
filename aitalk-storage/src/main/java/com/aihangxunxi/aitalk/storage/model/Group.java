package com.aihangxunxi.aitalk.storage.model;

import org.bson.types.ObjectId;

import java.util.List;

/**
 * 群组关系
 *
 * @author chenqingze107@163.com
 * @version 2.0
 */
public class Group extends BaseModel {

	private ObjectId id;

	private String name;

	private String notice;

	private ObjectId owner;

	private List<Member> members;

	class Member {

		private ObjectId id;

		private Long userId;

	}

}
