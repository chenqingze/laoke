package com.aihangxunxi.aitalk.storage.domain.msg.model;


import com.aihangxunxi.aitalk.storage.domain.msg.attachment.MsgAttachment;
import com.aihangxunxi.aitalk.storage.domain.msg.constant.MsgStatus;
import com.aihangxunxi.aitalk.storage.domain.msg.constant.MsgType;
import com.aihangxunxi.aitalk.storage.domain.msg.constant.SessionType;

import java.io.Serializable;
import java.util.Map;

/**
 * 最近联系人数据接口
 *
 * @author chenqingze107@163.com
 * @version 1.0
 * @since 2020/3/18
 */
public interface RecentContact extends Serializable {

    /**
     * 如果最近一条消息是扩展消息类型，获取消息的附件内容. 在最近消息列表，第三方app可据此自主定义显示的缩略语
     *
     * @return 附件内容
     */
    MsgAttachment getAttachment();

    /**
     * 获取最近联系人的ID（好友帐号，群ID等）
     *
     * @return 最近联系人帐号
     */
    String getContactId();

    /**
     * 获取最近一条消息的缩略内容。 对于文本消息，返回文本内容。
     * 对于其他消息，返回一个简单的说明内容。如需展示更详细，或其他需求，可根据getAttachment()生成。
     *
     * @return 缩略内容
     */
    String getContent();

    /**
     * 获取扩展字段
     *
     * @return 扩展字段Map
     */
    Map<String, Object> getExtension();

    /**
     * 获取与该联系人的最后一条消息的发送方的帐号
     *
     * @return 发送者帐号
     */
    String getFromAccount();

    /**
     * 获取与该联系人的最后一条消息的发送方的昵称
     *
     * @return 发送者昵称
     */
    String getFromNick();

    /**
     * 获取最近一条消息状态
     *
     * @return 最近一条消息的状态
     */
    MsgStatus getMsgStatus();

    /**
     * 获取最近一条消息的消息类型
     *
     * @return 消息类型
     */
    MsgType getMsgType();

    /**
     * 最近一条消息的消息ID @see IMMessage.getUuid() ()}
     *
     * @return 最近一条消息的UUID
     */
    String getRecentMessageId();

    /**
     * 获取会话类型
     *
     * @return 会话类型
     */
    SessionType getSessionType();

    /**
     * 获取标签
     *
     * @return 标签值
     */
    long getTag();

    /**
     * 获取最近一条消息的时间，单位为ms
     *
     * @return 时间
     */
    long getTime();

    /**
     * 获取该联系人的未读消息条数
     *
     * @return 未读数
     */
    int getUnreadCount();

    /**
     * 设置扩展字段
     *
     * @param extension 扩展字段Map
     */
    void setExtension(Map<String, Object> extension);

    /**
     * 设置最近一条消息的状态
     *
     * @param msgStatus 消息状态
     */
    void setMsgStatus(MsgStatus msgStatus);

    /**
     * 设置一个标签，用于做联系人置顶、最近会话列表排序等扩展用途。 SDK不关心tag的意义。第三方app需要事先规划好可能的用途。
     * 设置过后，需要调用MsgService.updateRecent(RecentContact)保存到数据库
     *
     * @param tag 标签值
     */
    void setTag(long tag);

}
