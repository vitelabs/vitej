package org.vitej.core.protocol.methods.response;

import org.vitej.core.utils.NumericUtils;

import java.math.BigInteger;

public class StakeAmountResponse extends Response<String> {
    /**
     * Return stake amount
     *
     * @return Stake amount
     */
    public BigInteger getStakeAmount() {
        return NumericUtils.stringToBigInteger(getResult());
    }
}
