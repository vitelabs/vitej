package org.vitej.core.protocol.methods.response;

import org.joda.time.DateTime;
import org.vitej.core.protocol.methods.Address;
import org.vitej.core.protocol.methods.Hash;
import org.vitej.core.utils.BytesUtils;
import org.vitej.core.utils.TimeUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 快照块信息
 */
public class SnapshotBlock {
    private String hash;
    private String previousHash;
    private Long height;
    private String producer;
    private String publicKey;
    private String signature;
    private Long seed;
    private String nextSeedHash;
    private Integer version;
    private Map<String, HashHeight> snapshotData;
    private Long timestamp;

    /**
     * 获取快照块hash
     *
     * @return 快照块hash
     */
    public Hash getHash() {
        return Hash.stringToHash(hash);
    }

    public String getHashRaw() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    /**
     * 获取快照链上上一个快照块的hash
     *
     * @return 快照链上上一个快照块的hash
     */
    public Hash getPreviousHash() {
        return Hash.stringToHash(previousHash);
    }

    public String getPreviousHashRaw() {
        return previousHash;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }

    /**
     * 获取快照块高度
     *
     * @return 快照块高度
     */
    public Long getHeight() {
        return height;
    }

    public void setHeight(Long height) {
        this.height = height;
    }

    /**
     * 获取出块地址
     *
     * @return 出块地址
     */
    public Address getProducer() {
        return Address.stringToAddress(producer);
    }

    public String getProducerRaw() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    /**
     * 获取打包快照块的超级节点的公钥
     *
     * @return 打包快照块的超级节点的公钥
     */
    public byte[] getPublicKey() {
        return BytesUtils.base64ToBytes(publicKey);
    }

    public String getPublicKeyRaw() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    /**
     * 获取签名
     *
     * @return 签名
     */
    public byte[] getSignature() {
        return BytesUtils.base64ToBytes(signature);
    }

    public String getSignatureRaw() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    /**
     * 获取出块节点上一轮生成的随机数
     *
     * @return 出块节点上一轮生成的随机数
     */
    public Long getSeed() {
        return seed;
    }

    public void setSeed(Long seed) {
        this.seed = seed;
    }

    /**
     * 获取出块节点本轮生成的随机数的hash
     *
     * @return 出块节点本轮生成的随机数的hash
     */
    public Hash getNextSeedHash() {
        return Hash.stringToHash(nextSeedHash);
    }

    public String getNextSeedHashRaw() {
        return nextSeedHash;
    }

    public void setNextSeedHash(String nextSeedHash) {
        this.nextSeedHash = nextSeedHash;
    }

    /**
     * 获取出块节点硬分叉版本号
     *
     * @return 出块节点硬分叉版本号
     */
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    /**
     * 获取快照的账户块高度和hash
     *
     * @return 快照的账户块高度和hash
     */
    public Map<Address, HashHeight> getSnapshotData() {
        Map<Address, HashHeight> map = new HashMap<>(snapshotData.size());
        snapshotData.forEach((key, value) -> {
            map.put(new Address(key), value);
        });
        return map;
    }

    public Map<String, HashHeight> getSnapshotDataRaw() {
        return snapshotData;
    }

    public void setSnapshotData(Map<String, HashHeight> snapshotData) {
        this.snapshotData = snapshotData;
    }

    /**
     * 获取出块时间
     *
     * @return 出块时间
     */
    public DateTime getTimestamp() {
        return TimeUtils.longToDateTime(timestamp);
    }

    public Long getTimestampRaw() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * hash和高度
     */
    public static class HashHeight {
        private Long height;
        private String hash;

        /**
         * 获取块高度
         *
         * @return 块高度
         */
        public Long getHeight() {
            return height;
        }

        public void setHeight(Long height) {
            this.height = height;
        }

        /**
         * 获取hash
         *
         * @return hash
         */
        public Hash getHash() {
            return Hash.stringToHash(hash);
        }

        public String getHashRaw() {
            return hash;
        }

        public void setHash(String hash) {
            this.hash = hash;
        }
    }
}
