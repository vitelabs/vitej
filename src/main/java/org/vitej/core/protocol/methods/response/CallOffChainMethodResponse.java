package org.vitej.core.protocol.methods.response;

import org.vitej.core.utils.BytesUtils;

public class CallOffChainMethodResponse extends Response<String> {
    /**
     * 获取合约离线方法的返回值，可以用ABI反解析
     *
     * @return 合约离线方法的返回值
     */
    public byte[] getReturnData() {
        return BytesUtils.base64ToBytes(getResult());
    }
}
