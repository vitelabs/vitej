package org.vitej.core.protocol.methods.response;

import org.vitej.core.protocol.methods.Hash;

public class LatestSnapshotHashResponse extends Response<String> {
    /**
     * 获取快照块hash
     *
     * @return 快照块hash
     */
    public Hash getHash() {
        return Hash.stringToHash(getResult());
    }
}
