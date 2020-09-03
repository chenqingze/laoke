package com.aihangxunxi.aitalk.storage.domain.msg.model;

/**
 * 全员广播通知
 *
 * @author chenqingze107@163.com
 * @version 1.0

 */
public class BroadcastMessage {

	private long id;// 广播id

	private String fromAccount;// 广播发送者账号

	private long time;// 广播消息时间戳

	private String content;// 广播消息内容

	public BroadcastMessage() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFromAccount() {
		return fromAccount;
	}

	public void setFromAccount(String fromAccount) {
		this.fromAccount = fromAccount;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
