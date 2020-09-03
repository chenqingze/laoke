package com.aihangxunxi.aitalk.storage.model;

import com.aihangxunxi.aitalk.storage.constant.InviteType;
import org.bson.types.ObjectId;

/**
 * 邀请消息
 *
 * @author chenqingze107@163.com
 * @version 1.0
 */
public class Invitation {
    private ObjectId id;
    private ObjectId requesterId;
    private String requesterAlias;
    private ObjectId addresseeId;
    private String addresseeAlias;
    private String status;
    private Long createdAt;
    private Long updatedAt;
    private String content;
    private InviteType inviteType;
}
