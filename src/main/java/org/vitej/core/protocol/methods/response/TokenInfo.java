package org.vitej.core.protocol.methods.response;

import org.vitej.core.protocol.methods.Address;
import org.vitej.core.protocol.methods.TokenId;
import org.vitej.core.utils.NumericUtils;

import java.math.BigInteger;

/**
 * 代币信息
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
     * 获取代币名称
     *
     * @return 代币名称
     */
    public String getTokenName() {
        return tokenName;
    }

    public void setTokenName(String tokenName) {
        this.tokenName = tokenName;
    }

    /**
     * 获取代币简称
     *
     * @return 代币简称
     */
    public String getTokenSymbol() {
        return tokenSymbol;
    }

    public void setTokenSymbol(String tokenSymbol) {
        this.tokenSymbol = tokenSymbol;
    }

    /**
     * 获取发行总量
     *
     * @return 发行总量
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
     * 获取小数位数
     *
     * @return 小数位数
     */
    public Integer getDecimals() {
        return decimals;
    }

    public void setDecimals(Integer decimals) {
        this.decimals = decimals;
    }

    /**
     * 获取代币所有者
     *
     * @return 所有者
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
     * 获取代币id
     *
     * @return 代币id
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
     * 获取最大发行量，不可增发代币此字段值为 0
     *
     * @return 最大发行量
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
     * 获取是否可增发
     *
     * @return 是否可增发，true 可增发 false 不可增发
     */
    public Boolean getReIssuable() {
        return isReIssuable;
    }

    public void setReIssuable(Boolean reIssuable) {
        isReIssuable = reIssuable;
    }

    /**
     * 获取是否仅所有者可销毁，不可增发代币此字段值为 false
     *
     * @return true 仅所有者可销毁 false 所有持币账户可销毁
     */
    public Boolean getOwnerBurnOnly() {
        return isOwnerBurnOnly;
    }

    public void setOwnerBurnOnly(Boolean ownerBurnOnly) {
        isOwnerBurnOnly = ownerBurnOnly;
    }

    /**
     * 获取代币序号
     *
     * @return 从 0 开始，同名 tokenSymbol 的序号按铸币顺序递增
     */
    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }
}
