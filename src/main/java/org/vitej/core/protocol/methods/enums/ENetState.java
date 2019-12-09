package org.vitej.core.protocol.methods.enums;

/**
 * Node sync status
 */
public enum ENetState {
    /**
     * Initialized
     */
    INIT(0),
    /**
     * Syncing
     */
    SYNCING(1),
    /**
     * Sync finished
     */
    DONE(2),
    /**
     * Sync failed
     */
    ERROR(3),
    /**
     * Sync cancelled
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
