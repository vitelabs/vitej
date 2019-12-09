package org.vitej.core.protocol.methods.response;

import org.vitej.core.protocol.methods.Address;
import org.vitej.core.protocol.methods.TokenId;
import org.vitej.core.utils.NumericUtils;

import java.math.BigInteger;

/**
 * Token information
 */
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

    /**
     * Return token name
     *
     * @return Token name
     */
    public String getTokenName() {
        return tokenName;
    }

    public void setTokenName(String tokenName) {
        this.tokenName = tokenName;
    }

    /**
     * Return token symbol
     *
     * @return Token symbol
     */
    public String getTokenSymbol() {
        return tokenSymbol;
    }

    public void setTokenSymbol(String tokenSymbol) {
        this.tokenSymbol = tokenSymbol;
    }

    /**
     * Return total supply
     *
     * @return Total supply
     */
    public BigInteger getTotalSupply() {
        return NumericUtils.stringToBigInteger(totalSupply);
    }

    public String getTotalSupplyRaw() {
        return totalSupply;
    }

    public void setTotalSupply(String totalSupply) {
        this.totalSupply = totalSupply;
    }

    /**
     * Return decimals
     *
     * @return Decimals
     */
    public Integer getDecimals() {
        return decimals;
    }

    public void setDecimals(Integer decimals) {
        this.decimals = decimals;
    }

    /**
     * Return token owner
     *
     * @return Token owner
     */
    public Address getOwner() {
        return new Address(owner);
    }

    public String getOwnerRaw() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
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
     * Return max supply. 0 for non-reissuable token
     *
     * @return Max supply
     */
    public BigInteger getMaxSupply() {
        return NumericUtils.stringToBigInteger(maxSupply);
    }

    public String getMaxSupplyRaw() {
        return maxSupply;
    }

    public void setMaxSupply(String maxSupply) {
        this.maxSupply = maxSupply;
    }

    /**
     * Return whether this token is reissuable
     *
     * @return If true, the token is reissuable, otherwise non-reissuable
     */
    public Boolean getReIssuable() {
        return isReIssuable;
    }

    public void setReIssuable(Boolean reIssuable) {
        isReIssuable = reIssuable;
    }

    /**
     * Return whether this token can be burned by non-owner address
     *
     * @return true If true, only token owner can burn token, otherwise every token holder can.
     * False for non-reissuable token
     */
    public Boolean getOwnerBurnOnly() {
        return isOwnerBurnOnly;
    }

    public void setOwnerBurnOnly(Boolean ownerBurnOnly) {
        isOwnerBurnOnly = ownerBurnOnly;
    }

    /**
     * Return token index
     *
     * @return Token index between 0-999. Index will be allocated in ascending order for tokens having the same symbol
     */
    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }
}
