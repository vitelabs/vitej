package org.vitej.core.protocol.methods.response;

import org.joda.time.DateTime;
import org.vitej.core.protocol.methods.Address;
import org.vitej.core.protocol.methods.Hash;
import org.vitej.core.protocol.methods.TokenId;
import org.vitej.core.utils.BlockUtils;
import org.vitej.core.utils.BytesUtils;
import org.vitej.core.utils.NumericUtils;
import org.vitej.core.utils.TimeUtils;

import java.math.BigInteger;
import java.util.List;

/**
 * Account block info
 */
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

    /**
     * Return block type
     *
     * @return Block type, {@link org.vitej.core.protocol.methods.enums.EBlockType}
     */
    public Integer getBlockType() {
        return blockType;
    }

    public void setBlockType(Integer blockType) {
        this.blockType = blockType;
    }

    /**
     * Return is send block
     *
     * @return True - send block，false - receive block
     */
    public Boolean isSendBlock() {
        return BlockUtils.isSendBlock(blockType);
    }

    /**
     * Return is receive block
     *
     * @return True - receive block，false - send block
     */
    public Boolean isReceiveBlock() {
        return BlockUtils.isReceiveBlock(blockType);
    }

    /**
     * Return block height
     *
     * @return Block height
     */
    public Long getHeight() {
        return NumericUtils.stringToLong(height);
    }

    public String getHeightRaw() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    /**
     * Return transaction hash
     *
     * @return Transaction hash
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
     * Return the hash of previous transaction. For the first transaction of account,
     * 0000000000000000000000000000000000000000000000000000000000000000 is filled
     *
     * @return The hash of previous transaction
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
     * Return account address
     *
     * @return Account address
     */
    public Address getAddress() {
        return Address.stringToAddress(address);
    }

    public String getAddressRaw() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Return public key
     *
     * @return Public key
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
     * Return block producer's address. For user account, producer is account address. For contract
     * account, producer is the supernode of delegate consensus group
     *
     * @return Block producer's address
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
     * Return the address of the account the transaction was sent from
     *
     * @return The address of the account the transaction was sent from
     */
    public Address getFromAddress() {
        return Address.stringToAddress(fromAddress);
    }

    public String getFromAddressRaw() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    /**
     * Return the address of the account the transaction is sent to
     *
     * @return The address of the account the transaction is sent to
     */
    public Address getToAddress() {
        return Address.stringToAddress(toAddress);
    }

    public String getToAddressRaw() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    /**
     * Return the hash of corresponding request transaction. For response transaction only.
     * 0000000000000000000000000000000000000000000000000000000000000000 is filled in for request
     *
     * @return The hash of corresponding request transaction
     */
    public Hash getSendBlockHash() {
        return Hash.stringToHash(sendBlockHash);
    }

    public String getSendBlockHashRaw() {
        return sendBlockHash;
    }

    public void setSendBlockHash(String sendBlockHash) {
        this.sendBlockHash = sendBlockHash;
    }

    /**
     * Return token id
     *
     * @return Token id
     */
    public TokenId getTokenId() {
        return TokenId.stringToTokenId(tokenId);
    }

    public String getTokenIdRaw() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    /**
     * Return transfer amount
     *
     * @return Transfer amount
     */
    public BigInteger getAmount() {
        return NumericUtils.stringToBigInteger(amount);
    }

    public String getAmountRaw() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    /**
     * Return fee
     *
     * @return Fee
     */
    public BigInteger getFee() {
        return NumericUtils.stringToBigInteger(fee);
    }

    public String getFeeRaw() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    /**
     * Optional data the transaction may carry
     *
     * @return Data
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

    /**
     * Chcek whether this block is a contract receive block and is executed successfully
     *
     * @return True - this block is a contract receive block and is executed successfully，
     * false - otherwise
     */
    public Boolean isContractReceiveSuccess() {
        return BlockUtils.isContractReceiveSuccess(getAddress(), blockType, getData());
    }

    /**
     * Return PoW difficulty
     *
     * @return PoW difficulty
     */
    public BigInteger getDifficulty() {
        return NumericUtils.stringToBigInteger(difficulty);
    }

    public String getDifficultyRaw() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * Return PoW nonce
     *
     * @return PoW nonce
     */
    public byte[] getNonce() {
        return BytesUtils.base64ToBytes(nonce);
    }

    public String getNonceRaw() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    /**
     * Return signature
     *
     * @return Signature
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
     * Return quota consumed by the transaction, excluding quota obtained through PoW
     *
     * @return Quota consumed by the transaction, excluding quota obtained through PoW
     */
    public Long getQuotaByStake() {
        return NumericUtils.stringToLong(quotaByStake);
    }

    public String getQuotaByStakeRaw() {
        return quotaByStake;
    }

    public void setQuotaByStake(String quotaByStake) {
        this.quotaByStake = quotaByStake;
    }

    /**
     * Return quota consumed by the transaction. PoW quota included.
     *
     * @return Quota consumed by the transaction. PoW quota included.
     */
    public Long getTotalQuota() {
        return NumericUtils.stringToLong(totalQuota);
    }

    public String getTotalQuotaRaw() {
        return totalQuota;
    }

    public void setTotalQuota(String totalQuota) {
        this.totalQuota = totalQuota;
    }

    /**
     * Return the hash of Vmlog generated by smart contract response
     *
     * @return The hash of Vmlog generated by smart contract response
     */
    public Hash getVmLogHash() {
        return Hash.stringToHash(vmLogHash);
    }

    public String getVmLogHasRaw() {
        return vmLogHash;
    }

    public void setVmLogHash(String vmLogHash) {
        this.vmLogHash = vmLogHash;
    }

    /**
     * Return a list of request transactions sent from within the block. RS block only表
     *
     * @return A list of request transactions sent from within the block
     */
    public List<AccountBlock> getTriggeredSendBlockList() {
        return triggeredSendBlockList;
    }

    public void setTriggeredSendBlockList(List<AccountBlock> triggeredSendBlockList) {
        this.triggeredSendBlockList = triggeredSendBlockList;
    }

    /**
     * Return token info
     *
     * @return Token info
     */
    public TokenInfo getTokenInfo() {
        return tokenInfo;
    }

    public void setTokenInfo(TokenInfo tokenInfo) {
        this.tokenInfo = tokenInfo;
    }

    /**
     * Return confirmation number
     *
     * @return Confirmation number
     */
    public Long getConfirmations() {
        return NumericUtils.stringToLong(confirmations);
    }

    public String getConfirmationsRaw() {
        return confirmations;
    }

    public void setConfirmations(String confirmations) {
        this.confirmations = confirmations;
    }

    /**
     * Return the hash of snapshot block by which the transaction is snapshotted
     *
     * @return The hash of snapshot block by which the transaction is snapshotted
     */
    public Hash getFirstSnapshotHash() {
        return Hash.stringToHash(firstSnapshotHash);
    }

    public String getFirstSnapshotHashRaw() {
        return firstSnapshotHash;
    }

    public void setFirstSnapshotHash(String firstSnapshotHash) {
        this.firstSnapshotHash = firstSnapshotHash;
    }

    /**
     * Return the height of the corresponding response transaction. Request only
     *
     * @return The height of the corresponding response transaction
     */
    public Long getReceiveBlockHeight() {
        return NumericUtils.stringToLong(receiveBlockHeight);
    }

    public String getReceiveBlockHeightRaw() {
        return receiveBlockHeight;
    }

    public void setReceiveBlockHeight(String receiveBlockHeight) {
        this.receiveBlockHeight = receiveBlockHeight;
    }

    /**
     * Return the hash of the corresponding response transaction. Request only
     *
     * @return The hash of the corresponding response transaction
     */
    public Hash getReceiveBlockHash() {
        return Hash.stringToHash(receiveBlockHash);
    }

    public String getReceiveBlockHashRaw() {
        return receiveBlockHash;
    }

    public void setReceiveBlockHash(String receiveBlockHash) {
        this.receiveBlockHash = receiveBlockHash;
    }

    /**
     * Return the timestamp (in second) when the transaction is snapshotted
     *
     * @return The timestamp (in second) when the transaction is snapshotted
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
}
