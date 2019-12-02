package org.vitej.core.protocol.methods.response;

import org.vitej.core.protocol.methods.Address;
import org.vitej.core.utils.NumericUtils;

import java.math.BigInteger;

/**
 * 超级节点信息
 */
public class SBPInfo {
    private String name;
    private String blockProducingAddress;
    private String rewardWithdrawAddress;
    private String stakeAddress;
    private String stakeAmount;
    private String expirationHeight;
    private Long expirationTime;
    private Long revokeTime;

    /**
     * 返回超级节点名称
     *
     * @return 超级节点名称
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取签名快照块的账户地址
     *
     * @return 签名快照块的账户地址，即出块地址
     */
    public Address getBlockProducingAddress() {
        return Address.stringToAddress(blockProducingAddress);
    }

    public String getBlockProducingAddressRaw() {
        return blockProducingAddress;
    }

    public void setBlockProducingAddress(String blockProducingAddress) {
        this.blockProducingAddress = blockProducingAddress;
    }

    /**
     * 获取提取奖励地址
     *
     * @return 提取奖励地址
     */
    public Address getRewardWithdrawAddress() {
        return Address.stringToAddress(rewardWithdrawAddress);
    }

    public String getRewardWithdrawAddressRaw() {
        return rewardWithdrawAddress;
    }

    public void setRewardWithdrawAddress(String rewardWithdrawAddress) {
        this.rewardWithdrawAddress = rewardWithdrawAddress;
    }

    /**
     * 获取抵押账户地址
     *
     * @return 抵押账户地址，即注册账户地址
     */
    public Address getStakeAddress() {
        return Address.stringToAddress(stakeAddress);
    }

    public String getStakeAddressRaw() {
        return stakeAddress;
    }

    public void setStakeAddress(String stakeAddress) {
        this.stakeAddress = stakeAddress;
    }

    /**
     * 获取抵押金额
     *
     * @return 抵押金额
     */
    public BigInteger getStakeAmount() {
        return NumericUtils.stringToBigInteger(stakeAmount);
    }

    public String getStakeAmountRaw() {
        return stakeAmount;
    }

    public void setStakeAmount(String stakeAmount) {
        this.stakeAmount = stakeAmount;
    }

    /**
     * 获取抵押到期高度，到期后可以取消注册并取回抵押
     *
     * @return 抵押到期高度
     */
    public Long getExpirationHeight() {
        return NumericUtils.stringToLong(expirationHeight);
    }

    public String getExpirationHeightRaw() {
        return expirationHeight;
    }

    public void setExpirationHeight(String expirationHeight) {
        this.expirationHeight = expirationHeight;
    }

    /**
     * 获取预估抵押到期时间，注意如果抵押未到期，则预估到期时间会随出块率而变化
     *
     * @return 预估抵押到期时间
     */
    public Long getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Long expirationTime) {
        this.expirationTime = expirationTime;
    }

    /**
     * 获取取消注册时间
     *
     * @return 取消注册时间，值为 0 时表示当前未取消
     */
    public Long getRevokeTime() {
        return revokeTime;
    }

    public void setRevokeTime(Long revokeTime) {
        this.revokeTime = revokeTime;
    }
}
