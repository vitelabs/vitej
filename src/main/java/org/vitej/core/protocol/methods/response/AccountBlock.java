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
 * 用户账户块信息
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
     * 获取交易类型
     *
     * @return 交易类型，参考 {@link org.vitej.core.protocol.methods.enums.EBlockType}
     */
    public Integer getBlockType() {
        return blockType;
    }

    public void setBlockType(Integer blockType) {
        this.blockType = blockType;
    }

    /**
     * 获取是否请求交易
     *
     * @return true-请求交易，false-响应交易
     */
    public Boolean isSendBlock() {
        return BlockUtils.isSendBlock(blockType);
    }

    /**
     * 获取是否响应交易
     *
     * @return true-响应交易，false-请求交易
     */
    public Boolean isReceiveBlock() {
        return BlockUtils.isReceiveBlock(blockType);
    }

    /**
     * 获取账户块高度
     *
     * @return 账户块高度
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
     * 获取交易哈希
     *
     * @return 交易哈希
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
     * 获取账户链上上一笔交易的哈希
     * 账户链上第一笔交易的值为 `0000000000000000000000000000000000000000000000000000000000000000``
     *
     * @return 账户链上上一笔交易的哈希
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
     * 获取账户块所属的账户地址
     *
     * @return 账户块所属的账户地址
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
     * 获取账户公钥
     *
     * @return 账户公钥
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
     * 获取出块账户地址
     * 用户账户块的出块地址为用户账户地址，合约账户块的出块地址为委托共识组的出块节点地址
     *
     * @return 出块账户地址
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
     * 获取请求账户地址
     * 对于请求交易，address和fromAddress相同
     *
     * @return 请求账户地址
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
     * 获取响应账户地址
     * 对于响应交易，address和toAddress相同
     *
     * @return 响应账户地址
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
     * 获取请求交易hash
     * 交易类型为请求时值为0000000000000000000000000000000000000000000000000000000000000000
     * 交易类型为响应时值为对应请求的hash
     *
     * @return 请求交易hash
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
     * 获取代币id
     *
     * @return 代币 id
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
     * 获取转账金额
     *
     * @return 转账金额
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
     * 获取手续费
     * 目前只有创建合约、铸币等交易收取手续费
     *
     * @return 手续费
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
     * 备注
     * 请求交易可以填写备注
     * 用户响应交易为空
     * 合约响应交易为执行结果
     *
     * @return 备注
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
     * 判断这笔交易是不是合约响应交易，并且执行成功
     *
     * @return true-是合约响应交易并且执行成功，false-不是合约响应交易或者执行失败
     */
    public Boolean isContractReceiveSuccess() {
        return BlockUtils.isContractReceiveSuccess(getAddress(), blockType, getData());
    }

    /**
     * 获取PoW的难度
     *
     * @return PoW难度，为空时表示没有计算PoW
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
     * 获取PoW的nonce，和difficulty字段成对出现
     *
     * @return PoW的nonce，为空时表示没有计算PoW
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
     * 获取消耗的配额，不包含计算 PoW 获得的一次性配额
     *
     * @return 配额
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
     * 获取消耗的配额，包含计算 PoW 获得的一次性配额
     *
     * @return 配额
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
     * 获取合约响应交易的vmlog的哈希
     *
     * @return 合约响应交易的vmlog的哈希
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
     * 获取合约响应交易发起的请求交易列表
     *
     * @return 合约响应交易发起的请求交易列表
     */
    public List<AccountBlock> getTriggeredSendBlockList() {
        return triggeredSendBlockList;
    }

    public void setTriggeredSendBlockList(List<AccountBlock> triggeredSendBlockList) {
        this.triggeredSendBlockList = triggeredSendBlockList;
    }

    /**
     * 获取转账的代币信息
     *
     * @return 转账的代币信息
     */
    public TokenInfo getTokenInfo() {
        return tokenInfo;
    }

    public void setTokenInfo(TokenInfo tokenInfo) {
        this.tokenInfo = tokenInfo;
    }

    /**
     * 获取交易被快照块确认的次数
     *
     * @return 交易被快照块确认的次数
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
     * 获取快照这笔交易的快照块哈希
     *
     * @return 快照这笔交易的快照块哈希，值为空表示未被快照
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
     * 获取请求交易对应的响应交易的块高度
     * 响应交易本字段为空
     *
     * @return 请求交易对应的响应交易的块高度
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
     * 获取请求交易对应的响应交易哈希
     * 响应交易本字段为空
     *
     * @return 请求交易对应的响应交易哈希，值为空表示请求交易未被接收
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
     * 获取交易被快照的时间，单位秒
     *
     * @return 交易被快照的时间，值为空表示交易未被快照
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
