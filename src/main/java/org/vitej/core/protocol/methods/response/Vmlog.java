package org.vitej.core.protocol.methods.response;

import org.vitej.core.protocol.methods.Hash;
import org.vitej.core.utils.BytesUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Event logs generated in contract response blocks
 */
public class Vmlog {
    private List<String> topics;
    private String data;

    /**
     * Return event signature and indexed field. The signature can be generated from ABI
     *
     * @return Event signature and indexed field
     */
    public List<Hash> getTopics() {
        List<Hash> list = new ArrayList<>(topics.size());
        topics.forEach(s -> {
            list.add(new Hash(s));
        });
        return list;
    }

    public List<String> getTopicsRaw() {
        return topics;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }

    /**
     * Return non-indexed field of event, can be decoded based on ABI
     *
     * @return Non-indexed field of event
     */
    public byte[] getData() {
        return BytesUtils.base64ToBytes(data);
    }

    public String getDataRaw() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
