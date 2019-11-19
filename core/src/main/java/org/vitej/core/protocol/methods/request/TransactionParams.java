package org.vitej.core.protocol.methods.request;

import org.vitej.core.constants.CommonConstants;
import org.vitej.core.protocol.methods.Address;
import org.vitej.core.protocol.methods.Hash;
import org.vitej.core.protocol.methods.TokenId;
import org.vitej.core.protocol.methods.enums.EBlockType;
import org.vitej.core.utils.BytesUtils;

import java.math.BigInteger;

public class TransactionParams {
    private Integer blockType;
    private Long height;
    private Hash previousHash;
    private Address address;
    private Address toAddress;
    private Hash sendBlockHash;
    private TokenId tokenId;
    private BigInteger amount;
    private BigInteger fee;
    private byte[] data;

    private BigInteger difficulty;
    private byte[] nonce;

    private Hash hash;
    private byte[] signature;
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
