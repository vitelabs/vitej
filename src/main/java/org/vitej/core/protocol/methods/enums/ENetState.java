package org.vitej.core.protocol.methods.enums;

/**
 * 网络同步状态
 */
public enum ENetState {
    /**
     * 初始化
     */
    INIT(0),
    /**
     * 同步中
     */
    SYNCING(1),
    /**
     * 同步完成
     */
    DONE(2),
    /**
     * 同步错误
     */
    ERROR(3),
    /**
     * 同步取消
     */
    CANCEL(4);

    ENetState(int value) {
        this.value = value;
    }

    private int value;

    public int getValue() {
        return value;
    }
}
