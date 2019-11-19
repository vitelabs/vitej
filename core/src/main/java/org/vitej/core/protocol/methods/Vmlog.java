package org.vitej.core.protocol.methods;

import org.vitej.core.utils.BytesUtils;

import java.util.ArrayList;
import java.util.List;

public class Vmlog {
    private List<String> topics;
    private String data;

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
