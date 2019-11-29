package org.vitej.core.protocol.methods;

import org.joda.time.DateTime;
import org.vitej.core.utils.BytesUtils;
import org.vitej.core.utils.TimeUtils;

import java.util.HashMap;
import java.util.Map;

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

    public Hash getHash() {
        return Hash.stringToHash(hash);
    }

    public String getHashRaw() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Hash getPreviousHash() {
        return Hash.stringToHash(previousHash);
    }

    public String getPreviousHashRaw() {
        return previousHash;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }

    public Long getHeight() {
        return height;
    }

    public void setHeight(Long height) {
        this.height = height;
    }

    public Address getProducer() {
        return Address.stringToAddress(producer);
    }

    public String getProducerRaw() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public byte[] getPublicKey() {
        return BytesUtils.base64ToBytes(publicKey);
    }

    public String getPublicKeyRaw() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public byte[] getSignature() {
        return BytesUtils.base64ToBytes(signature);
    }

    public String getSignatureRaw() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public Long getSeed() {
        return seed;
    }

    public void setSeed(Long seed) {
        this.seed = seed;
    }

    public Hash getNextSeedHash() {
        return Hash.stringToHash(nextSeedHash);
    }

    public String getNextSeedHashRaw() {
        return nextSeedHash;
    }

    public void setNextSeedHash(String nextSeedHash) {
        this.nextSeedHash = nextSeedHash;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

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

    public DateTime getTimestamp() {
        return TimeUtils.longToDateTime(timestamp);
    }

    public Long getTimestampRaw() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public static class HashHeight {
        private Long height;
        private String hash;

        public Long getHeight() {
            return height;
        }

        public void setHeight(Long height) {
            this.height = height;
        }

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
