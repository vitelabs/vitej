package org.vitej.core.protocol.methods;

import org.vitej.core.utils.NumericUtils;

import java.math.BigInteger;

public class TokenInfo {
    private String tokenName;
    private String tokenSymbol;
    private String totalSupply;
    private Integer decimals;
    private String owner;
    private String tokenId;
    private String maxSupply;
    private Boolean isReIssuable;
    private Boolean isOwnerBurnOnly;
    private Integer index;

    public String getTokenName() {
        return tokenName;
    }

    public void setTokenName(String tokenName) {
        this.tokenName = tokenName;
    }

    public String getTokenSymbol() {
        return tokenSymbol;
    }

    public void setTokenSymbol(String tokenSymbol) {
        this.tokenSymbol = tokenSymbol;
    }

    public BigInteger getTotalSupply() {
        return NumericUtils.stringToBigInteger(totalSupply);
    }

    public String getTotalSupplyRaw() {
        return totalSupply;
    }

    public void setTotalSupply(String totalSupply) {
        this.totalSupply = totalSupply;
    }

    public Integer getDecimals() {
        return decimals;
    }

    public void setDecimals(Integer decimals) {
        this.decimals = decimals;
    }

    public Address getOwner() {
        return new Address(owner);
    }

    public String getOwnerRaw() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
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

    public BigInteger getMaxSupply() {
        return NumericUtils.stringToBigInteger(maxSupply);
    }

    public String getMaxSupplyRaw() {
        return maxSupply;
    }

    public void setMaxSupply(String maxSupply) {
        this.maxSupply = maxSupply;
    }

    public Boolean getReIssuable() {
        return isReIssuable;
    }

    public void setReIssuable(Boolean reIssuable) {
        isReIssuable = reIssuable;
    }

    public Boolean getOwnerBurnOnly() {
        return isOwnerBurnOnly;
    }

    public void setOwnerBurnOnly(Boolean ownerBurnOnly) {
        isOwnerBurnOnly = ownerBurnOnly;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }
}
