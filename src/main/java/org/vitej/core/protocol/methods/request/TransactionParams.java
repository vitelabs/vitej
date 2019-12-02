package org.vitej.core.protocol.methods.request;

import org.vitej.core.constants.CommonConstants;
import org.vitej.core.protocol.methods.Address;
import org.vitej.core.protocol.methods.Hash;
import org.vitej.core.protocol.methods.TokenId;
import org.vitej.core.protocol.methods.enums.EBlockType;
import org.vitej.core.utils.BytesUtils;

import java.math.BigInteger;

/**
 * 交易参数
 */
public class TransactionParams {
    /**
     * 交易类型，参考 {@link org.vitej.core.protocol.methods.enums.EBlockType}
     * 不填默认为SEND_CALL
     */
    private Integer blockType;
    /**
     * 账户块高度
     * 不填默认取账户链上上一个块的高度+1
     */
    private Long height;
    /**
     * 账户链上上一笔交易的哈希
     * 不填默认取账户链上上一个块的hash
     */
    private Hash previousHash;
    /**
     * 账户块所属的账户地址
     * 不填默认取keypair对应的地址
     */
    private Address address;
    /**
     * 响应账户地址
     * 创建合约时自动生成，其他请求交易类型必填
     * 响应交易不填默认根据sendBlockHash填充
     */
    private Address toAddress;
    /**
     * 交易类型为请求时无需填写,
     * 交易类型为响应时值为对应请求的哈希
     */
    private Hash sendBlockHash;
    /**
     * 代币id
     * 不填默认使用VITE的代币id
     */
    private TokenId tokenId;
    /**
     * 转账金额
     * 不填默认为0
     */
    private BigInteger amount;
    /**
     * 手续费
     * 目前只有创建合约和铸币交易需要填这个字段，默认为0
     */
    private BigInteger fee;
    /**
     * 备注
     * 不填默认为空
     */
    private byte[] data;
    /**
     * PoW的难度
     * 无需填写，如果autoPoW为true则自动填充此字段
     */
    private BigInteger difficulty;
    /**
     * PoW的nonce
     * 无需填写，如果autoPoW为true则自动填充此字段
     */
    private byte[] nonce;
    /**
     * 交易哈希
     * 无需填写，自动计算
     */
    private Hash hash;
    /**
     * 签名
     * 无需填写，自动计算
     */
    private byte[] signature;
    /**
     * 账户公钥
     * 无需填写，自动计算
     */
    private byte[] publicKey;

    public TransactionParams() {
    }

    public TransactionParams(Hash sendBlockHash) {
        this.blockType = EBlockType.RECEIVE.getValue();
        this.sendBlockHash = sendBlockHash;
    }

    public TransactionParams(Address toAddress, TokenId tokenId, BigInteger amount, byte[] data) {
        this.blockType = EBlockType.SEND_CALL.getValue();
        this.toAddress = toAddress;
        this.tokenId = tokenId;
        this.amount = amount;
        this.data = data;
    }

    public TransactionParams(TokenId tokenId, BigInteger amount, byte[] data) {
        this.blockType = EBlockType.SEND_CREATE.getValue();
        this.tokenId = tokenId;
        this.amount = amount;
        this.fee = CommonConstants.CREATE_CONTRACT_FEE;
        this.data = data;
    }

    public Integer getBlockType() {
        return blockType;
    }

    public TransactionParams setBlockType(Integer blockType) {
        this.blockType = blockType;
        return this;
    }

    public String getHeight() {
        return height == null ? null : height.toString();
    }

    public Long getHeightRaw() {
        return height;
    }

    public TransactionParams setHeight(Long height) {
        this.height = height;
        return this;
    }

    public String getPreviousHash() {
        return previousHash == null ? null : previousHash.toString();
    }

    public Hash getPreviousHashRaw() {
        return previousHash;
    }

    public TransactionParams setPreviousHash(Hash previousHash) {
        this.previousHash = previousHash;
        return this;
    }

    public String getAddress() {
        return address == null ? null : address.toString();
    }

    public Address getAddressRaw() {
        return address;
    }

    public TransactionParams setAddress(Address address) {
        this.address = address;
        return this;
    }

    public String getToAddress() {
        return toAddress == null ? null : toAddress.toString();
    }

    public Address getToAddressRaw() {
        return toAddress;
    }

    public TransactionParams setToAddress(Address toAddress) {
        this.toAddress = toAddress;
        return this;
    }

    public String getSendBlockHash() {
        return sendBlockHash == null ? null : sendBlockHash.toString();
    }

    public Hash getSendBlockHashRaw() {
        return sendBlockHash;
    }

    public TransactionParams setSendBlockHash(Hash sendBlockHash) {
        this.sendBlockHash = sendBlockHash;
        return this;
    }

    public String getTokenId() {
        return tokenId == null ? null : tokenId.toString();
    }

    public TokenId getTokenIdRaw() {
        return tokenId;
    }

    public TransactionParams setTokenId(TokenId tokenId) {
        this.tokenId = tokenId;
        return this;
    }

    public String getAmount() {
        return amount == null ? null : amount.toString();
    }

    public BigInteger getAmountRaw() {
        return amount;
    }

    public TransactionParams setAmount(BigInteger amount) {
        this.amount = amount;
        return this;
    }

    public String getFee() {
        return fee == null ? null : fee.toString();
    }

    public BigInteger getFeeRaw() {
        return fee;
    }

    public TransactionParams setFee(BigInteger fee) {
        this.fee = fee;
        return this;
    }

    public String getData() {
        return data == null ? null : BytesUtils.bytesToBase64(data);
    }

    public byte[] getDataRaw() {
        return data;
    }

    public TransactionParams setData(byte[] data) {
        this.data = data;
        return this;
    }

    public String getDifficulty() {
        return difficulty == null ? "" : difficulty.toString();
    }

    public BigInteger getDifficultyRaw() {
        return difficulty;
    }

    public TransactionParams setDifficulty(BigInteger difficulty) {
        this.difficulty = difficulty;
        return this;
    }

    public String getNonce() {
        return nonce == null ? null : BytesUtils.bytesToBase64(nonce);
    }

    public byte[] getNonceRaw() {
        return nonce;
    }

    public TransactionParams setNonce(byte[] nonce) {
        this.nonce = nonce;
        return this;
    }

    public String getHash() {
        return hash == null ? null : hash.toString();
    }

    public Hash getHashRaw() {
        return hash;
    }

    public TransactionParams setHash(Hash hash) {
        this.hash = hash;
        return this;
    }

    public String getSignature() {
        return signature == null ? null : BytesUtils.bytesToBase64(signature);
    }

    public byte[] getSignatureRaw() {
        return signature;
    }

    public TransactionParams setSignature(byte[] signature) {
        this.signature = signature;
        return this;
    }

    public String getPublicKey() {
        return publicKey == null ? null : BytesUtils.bytesToBase64(publicKey);
    }

    public byte[] getPublicKeyRaw() {
        return publicKey;
    }

    public TransactionParams setPublicKey(byte[] publicKey) {
        this.publicKey = publicKey;
        return this;
    }
}
