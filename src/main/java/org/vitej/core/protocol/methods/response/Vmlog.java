package org.vitej.core.protocol.methods.response;

import org.vitej.core.protocol.methods.Hash;
import org.vitej.core.utils.BytesUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 智能合约执行过程中产生的event，也叫vmlog
 */
public class Vmlog {
    private List<String> topics;
    private String data;

    /**
     * 获取event签名和索引字段，其中签名可以用ABI定义生成
     *
     * @return event签名和索引字段
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
     * 获取event的非索引字段，可以用ABI定义反解析
     *
     * @return event的非索引字段
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
