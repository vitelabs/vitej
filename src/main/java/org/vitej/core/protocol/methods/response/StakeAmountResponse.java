package org.vitej.core.protocol.methods.response;

import org.vitej.core.utils.NumericUtils;

import java.math.BigInteger;

public class StakeAmountResponse extends Response<String> {
    /**
     * 获取抵押金额
     *
     * @return 抵押金额
     */
    public BigInteger getStakeAmount() {
        return NumericUtils.stringToBigInteger(getResult());
    }
}
