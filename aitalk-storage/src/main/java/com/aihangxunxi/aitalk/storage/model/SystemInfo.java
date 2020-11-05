package com.aihangxunxi.aitalk.storage.model;

import org.bson.types.ObjectId;

/**
 * @Author: suguodong Date: 2020/11/4 16:42
 * @Version: 3.0
 */
public class SystemInfo {

	private ObjectId id;

	// 订单id或者预购单id
	private String orderId;

	// 接受人 userId
	private Long receiverId;

	// 标题
	private String title;

	// 内容
	private String content;

	// 图片地址 oss
	private String imagePath;

	// 类型
	private String type;

	// 创建时间
	private Long createdAt;

	// 修改时间
	private Long updatedAt;

	// 消息状态
	private String status;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Long getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(Long receiverId) {
		this.receiverId = receiverId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
