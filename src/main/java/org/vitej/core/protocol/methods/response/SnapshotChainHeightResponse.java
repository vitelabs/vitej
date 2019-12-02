package org.vitej.core.protocol.methods.response;

import org.vitej.core.utils.NumericUtils;

public class SnapshotChainHeightResponse extends Response<String> {
    /**
     * 获取最新的快照块高度
     *
     * @return 最新的快照块高度
     */
    public Long getHeight() {
        return NumericUtils.stringToLong(getResult());
    }
}
