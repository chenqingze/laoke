package com.aihangxunxi.aitalk.im.session;

/**
 * @deprecated
 */
public enum PlatformType {

    IOS(0),

    ANDROID(1),

    WEB(2);

    private final int value;

    PlatformType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static PlatformType getOfValue(int value) {

        for (PlatformType t : values()) {
            if (value == t.value)
                return t;
        }
        throw new RuntimeException("没有找到对应的枚举");
    }

}
