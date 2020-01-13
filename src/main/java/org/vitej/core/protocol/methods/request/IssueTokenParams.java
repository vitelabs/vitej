package org.vitej.core.protocol.methods.request;

import java.math.BigInteger;

public class IssueTokenParams {
    /**
     * Token type. true for re-issuable, false for fixed-supply.
     */
    private boolean isReIssuable = false;
    /**
     * 1-40 characters, including uppercase and lowercase letters, spaces and underscores.
     * Cannot have consecutive spaces; cannot begin or end with spaces
     */
    private String tokenName;
    /**
     * 1-10 characters, including uppercase and lowercase letters and numbers.
     * Existing names like VITE, VCP and VX cannot be reused.
     */
    private String tokenSymbol;
    /**
     * Initial supply.
     */
    private BigInteger totalSupply;
    /**
     *
     */
    private int decimals;
    /**
     * Maximum supply. Mandatory for re-issuable token.
     */
    private BigInteger maxSupply = BigInteger.ZERO;
    /**
     * Whether the token can be burned by owner only. Mandatory for re-issuable token.
     */
    private boolean isOwnerBurnOnly = false;

    public boolean isReIssuable() {
        return isReIssuable;
    }

    public IssueTokenParams setReIssuable(boolean reIssuable) {
        isReIssuable = reIssuable;
        return this;
    }

    public String getTokenName() {
        return tokenName;
    }

    public IssueTokenParams setTokenName(String tokenName) {
        this.tokenName = tokenName;
        return this;
    }

    public String getTokenSymbol() {
        return tokenSymbol;
    }

    public IssueTokenParams setTokenSymbol(String tokenSymbol) {
        this.tokenSymbol = tokenSymbol;
        return this;
    }

    public BigInteger getTotalSupply() {
        return totalSupply;
    }

    public IssueTokenParams setTotalSupply(BigInteger totalSupply) {
        this.totalSupply = totalSupply;
        return this;
    }

    public int getDecimals() {
        return decimals;
    }

    public IssueTokenParams setDecimals(int decimals) {
        this.decimals = decimals;
        return this;
    }

    public BigInteger getMaxSupply() {
        return maxSupply;
    }

    public IssueTokenParams setMaxSupply(BigInteger maxSupply) {
        this.maxSupply = maxSupply;
        return this;
    }

    public boolean isOwnerBurnOnly() {
        return isOwnerBurnOnly;
    }

    public IssueTokenParams setOwnerBurnOnly(boolean ownerBurnOnly) {
        isOwnerBurnOnly = ownerBurnOnly;
        return this;
    }
}
