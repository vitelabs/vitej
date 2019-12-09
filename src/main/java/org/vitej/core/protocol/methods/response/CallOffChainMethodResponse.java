package org.vitej.core.protocol.methods.response;

import org.vitej.core.utils.BytesUtils;

public class CallOffChainMethodResponse extends Response<String> {
    /**
     * Return encoded return value. Use decode methods to get decoded value
     *
     * @return Encoded return value
     */
    public byte[] getReturnData() {
        return BytesUtils.base64ToBytes(getResult());
    }
}
