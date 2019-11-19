package org.vitej.core.protocol.methods.response;

import org.vitej.core.protocol.methods.Response;
import org.vitej.core.utils.NumericUtils;

public class SnapshotChainHeightResponse extends Response<String> {
    public Long getHeight() {
        return NumericUtils.stringToLong(getResult());
    }
}
