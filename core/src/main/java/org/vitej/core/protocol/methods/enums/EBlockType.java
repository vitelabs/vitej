package org.vitej.core.protocol.methods.enums;

public enum EBlockType {
    SEND_CREATE(1),
    SEND_CALL(2),
    SEND_ISSUE(3),
    RECEIVE(4),
    RECEIVE_ERROR(5),
    SEND_REFUND(6),
    RECEIVE_GENESIS(7);

    EBlockType(int value) {
        this.value = value;
    }

    private int value;

    public int getValue() {
        return value;
    }
}
