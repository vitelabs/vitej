package org.vitej.core.protocol.methods.response;

import org.vitej.core.protocol.methods.Hash;

public class LatestSnapshotHashResponse extends Response<String> {
    /**
     * Return snapshot block hash
     *
     * @return Snapshot block hash
     */
    public Hash getHash() {
        return Hash.stringToHash(getResult());
    }
}
