package com.aihangxunxi.aitalk.storage.constant;

/**
 * 消息状态
 *
 * @author chenqingze107@163.com
 * @version 1.0
 */
public enum MsgStatus {

    DRAFT, // 草稿
    SENDING, // 正在发送中
    SUCCESS, // 发送成功
    FAIL, // 发送失败
    READ, // 消息已读 发送消息时表示对方已看过该消息 接收消息时表示自己已读过，一般仅用于音频消息
    UNREAD// 未读状态

}
