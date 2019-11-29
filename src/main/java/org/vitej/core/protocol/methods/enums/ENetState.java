package org.vitej.core.protocol.methods.enums;

public enum ENetState {
    INIT(0),
    SYNCING(1),
    DONE(2),
    ERROR(3),
    CANCEL(4);

    ENetState(int value) {
        this.value = value;
    }

    private int value;

    public int getValue() {
        return value;
    }
}
