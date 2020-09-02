package com.aihangxunxi.aitalk.storage.domain.msg.constant;

/**
 * @author chenqingze107@163.com
 * @version 1.0
 * @since 2020/3/19
 */
public enum SessionType {

    NONE(-1), P2P(0), TEAM(1), SUPER_TEAM(5), SYSTEM(10001), CHATROOM(10002), P2P_STORE(6);

    private final int value;

    SessionType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static SessionType typeOfValue(int value) {

        for (SessionType t : values()) {
            if (value == t.value)
                return t;
        }
        return P2P;
    }

    public static SessionType codeOf(int code) {
        for (SessionType em : values()) {
            if (em.getValue() == code) {
                return em;
            }
        }
        throw new RuntimeException("没有找到对应的枚举");
    }

}
