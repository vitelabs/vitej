package org.vitej.core.protocol.methods.response;

import org.vitej.core.protocol.methods.Response;
import org.vitej.core.utils.NumericUtils;

import java.math.BigInteger;

public class StakeAmountResponse extends Response<String> {
    public BigInteger getStakeAmount() {
        return NumericUtils.stringToBigInteger(getResult());
    }
}
