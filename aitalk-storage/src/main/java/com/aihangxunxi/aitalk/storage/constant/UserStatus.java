package com.aihangxunxi.aitalk.storage.constant;

/**
 * 用户状态
 *
 * @author chenqingze107@163.com
 * @version 2.0
 */
public enum UserStatus {

    EFFECTIVE, CANCEL, FREEZE;

    public static UserStatus codeOf(int code) {
        for (UserStatus userStatus : values()) {
            if (userStatus.ordinal() == code) {
                return userStatus;
            }
        }
        throw new RuntimeException("没找到对应的枚举");
    }

}
