package org.vitej.core.protocol.methods.response;

import org.vitej.core.utils.BytesUtils;

public class PoWNonceResponse extends Response<String> {
    /**
     * 获取PoW难度对应的nonce
     *
     * @return nonce
     */
    public byte[] getNonce() {
        return BytesUtils.base64ToBytes(getResult());
    }
}
