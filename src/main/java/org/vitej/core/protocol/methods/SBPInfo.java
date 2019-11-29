package org.vitej.core.protocol.methods;

import org.vitej.core.utils.NumericUtils;

import java.math.BigInteger;

public class SBPInfo {
    private String name;
    private String blockProducingAddress;
    private String rewardWithdrawAddress;
    private String stakeAddress;
    private String stakeAmount;
    private String expirationHeight;
    private Long expirationTime;
    private Long revokeTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getBlockProducingAddress() {
        return Address.stringToAddress(blockProducingAddress);
    }

    public String getBlockProducingAddressRaw() {
        return blockProducingAddress;
    }

    public void setBlockProducingAddress(String blockProducingAddress) {
        this.blockProducingAddress = blockProducingAddress;
    }

    public Address getRewardWithdrawAddress() {
        return Address.stringToAddress(rewardWithdrawAddress);
    }

    public String getRewardWithdrawAddressRaw() {
        return rewardWithdrawAddress;
    }

    public void setRewardWithdrawAddress(String rewardWithdrawAddress) {
        this.rewardWithdrawAddress = rewardWithdrawAddress;
    }

    public Address getStakeAddress() {
        return Address.stringToAddress(stakeAddress);
    }

    public String getStakeAddressRaw() {
        return stakeAddress;
    }

    public void setStakeAddress(String stakeAddress) {
        this.stakeAddress = stakeAddress;
    }

    public BigInteger getStakeAmount() {
        return NumericUtils.stringToBigInteger(stakeAmount);
    }

    public String getStakeAmountRaw() {
        return stakeAmount;
    }

    public void setStakeAmount(String stakeAmount) {
        this.stakeAmount = stakeAmount;
    }

    public Long getExpirationHeight() {
        return NumericUtils.stringToLong(expirationHeight);
    }

    public String getExpirationHeightRaw() {
        return expirationHeight;
    }

    public void setExpirationHeight(String expirationHeight) {
        this.expirationHeight = expirationHeight;
    }

    public Long getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Long expirationTime) {
        this.expirationTime = expirationTime;
    }

    public Long getRevokeTime() {
        return revokeTime;
    }

    public void setRevokeTime(Long revokeTime) {
        this.revokeTime = revokeTime;
    }
}
