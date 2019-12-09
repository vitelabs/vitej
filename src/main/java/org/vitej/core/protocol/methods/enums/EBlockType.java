package org.vitej.core.protocol.methods.enums;

/**
 * Block type
 */

public enum EBlockType {
    /**
     * Create contract request
     */
    SEND_CREATE(1),
    /**
     * Transfer of call contract request
     */
    SEND_CALL(2),
    /**
     * Issue token request, only triggered by assert built-in contract
     */
    SEND_ISSUE(3),
    /**
     * Response
     */
    RECEIVE(4),
    /**
     * Contract response failed
     */
    RECEIVE_ERROR(5),
    /**
     * Contract refund request
     */
    SEND_REFUND(6),
    /**
     * Genesis response
     */
    RECEIVE_GENESIS(7);

    EBlockType(int value) {
        this.value = value;
    }

    private int value;

    public int getValue() {
        return value;
    }
}
