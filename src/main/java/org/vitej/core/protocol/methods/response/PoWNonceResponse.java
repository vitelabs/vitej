package org.vitej.core.protocol.methods.response;

import org.vitej.core.utils.BytesUtils;

public class PoWNonceResponse extends Response<String> {
    /**
     * Return PoW nonce
     *
     * @return Nonce
     */
    public byte[] getNonce() {
        return BytesUtils.base64ToBytes(getResult());
    }
}
