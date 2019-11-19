package org.vitej.core.protocol.methods.response;

import org.vitej.core.protocol.methods.Response;
import org.vitej.core.utils.BytesUtils;

public class CallOffChainMethodResponse extends Response<String> {
    public byte[] getReturnData() {
        return BytesUtils.base64ToBytes(getResult());
    }
}
