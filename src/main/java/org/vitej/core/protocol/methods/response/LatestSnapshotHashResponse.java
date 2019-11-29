package org.vitej.core.protocol.methods.response;

import org.vitej.core.protocol.methods.Hash;
import org.vitej.core.protocol.methods.Response;

public class LatestSnapshotHashResponse extends Response<String> {
    public Hash getHash() {
        return Hash.stringToHash(getResult());
    }
}
