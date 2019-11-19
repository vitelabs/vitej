package org.vitej.core.protocol.methods;

import org.joda.time.DateTime;
import org.vitej.core.utils.BlockUtils;
import org.vitej.core.utils.BytesUtils;
import org.vitej.core.utils.NumericUtils;
import org.vitej.core.utils.TimeUtils;

import java.math.BigInteger;
import java.util.List;

public class AccountBlock {
    private Integer blockType;
    private String height;
    private String hash;
    private String previousHash;
    private String address;
    private String publicKey;
    private String producer;
    private String fromAddress;
    private String toAddress;
    private String sendBlockHash;
    private String tokenId;
    private String amount;
    private String fee;
    private String data;
    private String difficulty;
    private String nonce;
    private String signature;
    private String quotaByStake;
    private String totalQuota;
    private String vmLogHash;
    private List<AccountBlock> triggeredSendBlockList;
    private TokenInfo tokenInfo;
    private String confirmations;
    private String firstSnapshotHash;
    private String receiveBlockHeight;
    private String receiveBlockHash;
    private Long timestamp;

    public Integer getBlockType() {
        return blockType;
    }

    public void setBlockType(Integer blockType) {
        this.blockType = blockType;
    }

    public Boolean isSendBlock() {
        return BlockUtils.isSendBlock(blockType);
    }

    public Boolean isReceiveBlock() {
        return BlockUtils.isReceiveBlock(blockType);
    }

    public Long getHeight() {
        return NumericUtils.stringToLong(height);
    }

    public String getHeightRaw() {
        return height;
    }

    public void setHeight(String height) {
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

    public Hash getPreviousHash() {
        return Hash.stringToHash(previousHash);
    }

    public String getPreviousHashRaw() {
        return previousHash;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }

    public Address getAddress() {
        return Address.stringToAddress(address);
    }

    public String getAddressRaw() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public Address getProducer() {
        return Address.stringToAddress(producer);
    }

    public String getProducerRaw() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public Address getFromAddress() {
        return Address.stringToAddress(fromAddress);
    }

    public String getFromAddressRaw() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public Address getToAddress() {
        return Address.stringToAddress(toAddress);
    }

    public String getToAddressRaw() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public Hash getSendBlockHash() {
        return Hash.stringToHash(sendBlockHash);
    }

    public String getSendBlockHashRaw() {
        return sendBlockHash;
    }

    public void setSendBlockHash(String sendBlockHash) {
        this.sendBlockHash = sendBlockHash;
    }

    public TokenId getTokenId() {
        return TokenId.stringToTokenId(tokenId);
    }

    public String getTokenIdRaw() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public BigInteger getAmount() {
        return NumericUtils.stringToBigInteger(amount);
    }

    public String getAmountRaw() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public BigInteger getFee() {
        return NumericUtils.stringToBigInteger(fee);
    }

    public String getFeeRaw() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
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

    public Boolean isContractReceiveSuccess() {
        return BlockUtils.isContractReceiveSuccess(getAddress(), blockType, getData());
    }

    public BigInteger getDifficulty() {
        return NumericUtils.stringToBigInteger(difficulty);
    }

    public String getDifficultyRaw() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public byte[] getNonce() {
        return BytesUtils.base64ToBytes(nonce);
    }

    public String getNonceRaw() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
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

    public Long getQuotaByStake() {
        return NumericUtils.stringToLong(quotaByStake);
    }

    public String getQuotaByStakeRaw() {
        return quotaByStake;
    }

    public void setQuotaByStake(String quotaByStake) {
        this.quotaByStake = quotaByStake;
    }

    public Long getTotalQuota() {
        return NumericUtils.stringToLong(totalQuota);
    }

    public String getTotalQuotaRaw() {
        return totalQuota;
    }

    public void setTotalQuota(String totalQuota) {
        this.totalQuota = totalQuota;
    }

    public Hash getVmLogHash() {
        return Hash.stringToHash(vmLogHash);
    }

    public String getVmLogHasRaw() {
        return vmLogHash;
    }

    public void setVmLogHash(String vmLogHash) {
        this.vmLogHash = vmLogHash;
    }

    public List<AccountBlock> getTriggeredSendBlockList() {
        return triggeredSendBlockList;
    }

    public void setTriggeredSendBlockList(List<AccountBlock> triggeredSendBlockList) {
        this.triggeredSendBlockList = triggeredSendBlockList;
    }

    public TokenInfo getTokenInfo() {
        return tokenInfo;
    }

    public void setTokenInfo(TokenInfo tokenInfo) {
        this.tokenInfo = tokenInfo;
    }

    public Long getConfirmations() {
        return NumericUtils.stringToLong(confirmations);
    }

    public String getConfirmationsRaw() {
        return confirmations;
    }

    public void setConfirmations(String confirmations) {
        this.confirmations = confirmations;
    }

    public Hash getFirstSnapshotHash() {
        return Hash.stringToHash(firstSnapshotHash);
    }

    public String getFirstSnapshotHashRaw() {
        return firstSnapshotHash;
    }

    public void setFirstSnapshotHash(String firstSnapshotHash) {
        this.firstSnapshotHash = firstSnapshotHash;
    }

    public Long getReceiveBlockHeight() {
        return NumericUtils.stringToLong(receiveBlockHeight);
    }

    public String getReceiveBlockHeightRaw() {
        return receiveBlockHeight;
    }

    public void setReceiveBlockHeight(String receiveBlockHeight) {
        this.receiveBlockHeight = receiveBlockHeight;
    }

    public Hash getReceiveBlockHash() {
        return Hash.stringToHash(receiveBlockHash);
    }

    public String getReceiveBlockHashRaw() {
        return receiveBlockHash;
    }

    public void setReceiveBlockHash(String receiveBlockHash) {
        this.receiveBlockHash = receiveBlockHash;
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
}
