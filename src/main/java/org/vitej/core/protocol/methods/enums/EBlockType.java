package org.vitej.core.protocol.methods.enums;

/**
 * 账户块类型
 */
public enum EBlockType {
    /**
     * 创建合约请求交易
     */
    SEND_CREATE(1),
    /**
     * 转账或者调用合约请求交易
     */
    SEND_CALL(2),
    /**
     * 增发代币请求交易，只能由铸币合约发起
     */
    SEND_ISSUE(3),
    /**
     * 响应交易
     */
    RECEIVE(4),
    /**
     * 合约响应交易失败，配额不足导致
     */
    RECEIVE_ERROR(5),
    /**
     * 合约退款请求交易
     */
    SEND_REFUND(6),
    /**
     * 创世块，属于响应交易
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
