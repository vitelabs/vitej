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
     * Block type, {@link org.vitej.core.protocol.methods.enums.EBlockType}
     * If not set, use SEND_CALL for default
     */
    private Integer blockType;
    /**
     * Block height
     * If not set, use latest account block height + 1 for default
     */
    private Long height;
    /**
     * The hash of previous transaction
     * If not set, use latest account block hash for default
     */
    private Hash previousHash;
    /**
     * Account address
     * If not set, use key pair address for default
     */
    private Address address;
    /**
     * The address of the account the transaction is sent to
     * Required to be set in request transaction
     * Generated automatically in create contract transaction
     * Set to toAddress of send block in response transaction
     */
    private Address toAddress;
    /**
     * Required to be set in response transaction
     * No need to be set in request transaction
     */
    private Hash sendBlockHash;
    /**
     * Token id
     * If not set, use VITE token id for default
     */
    private TokenId tokenId;
    /**
     * Transfer amount
     * If not set, use 0 for default
     */
    private BigInteger amount;
    /**
     * Fee
     * If not set, use 0 for default
     * Automatically set to 10 VITE in create contract transaction
     */
    private BigInteger fee;
    /**
     * Optional data the transaction may carry
     * If not set, use null for default
     */
    private byte[] data;
    /**
     * PoW difficulty
     * If not set and autoPoW is true, automatically fill in this field
     */
    private BigInteger difficulty;
    /**
     * PoW nonce
     * If not set and autoPoW is true, automatically fill in this field
     */
    private byte[] nonce;
    /**
     * Transaction hash
     * Automatically filled in
     */
    private Hash hash;
    /**
     * Signature
     * Automatically filled in by key pair
     */
    private byte[] signature;
    /**
     * Public key
     * Automatically filled in by key pair
     */
    private byte[] publicKey;

    public TransactionParams() {
    }

    /**
     * Create receive transaction
     *
     * @param sendBlockHash The hash of corresponding request transaction
     */
    public TransactionParams(Hash sendBlockHash) {
        this.blockType = EBlockType.RECEIVE.getValue();
        this.sendBlockHash = sendBlockHash;
    }

    /**
     * Create transfer transaction or call contract transaction
     *
     * @param toAddress The address of the account the transaction is sent to
     * @param tokenId   Token id
     * @param amount    Transfer amount
     * @param data      Transfer comment or call contract data
     */
    public TransactionParams(Address toAddress, TokenId tokenId, BigInteger amount, byte[] data) {
        this.blockType = EBlockType.SEND_CALL.getValue();
        this.toAddress = toAddress;
        this.tokenId = tokenId;
        this.amount = amount;
        this.data = data;
    }

    /**
     * Create create contract transaction
     *
     * @param tokenId Token id
     * @param amount  Transfer amount when calling constructor method of the contract
     * @param data    Create contract data
     */
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
