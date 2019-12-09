package org.vitej.core.protocol.methods.response;

import org.vitej.core.utils.NumericUtils;

public class SnapshotChainHeightResponse extends Response<String> {
    /**
     * Return snapshot block height
     *
     * @return Snapshot block height
     */
    public Long getHeight() {
        return NumericUtils.stringToLong(getResult());
    }
}
