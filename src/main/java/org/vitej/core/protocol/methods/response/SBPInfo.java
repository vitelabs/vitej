package org.vitej.core.protocol.methods.response;

import org.vitej.core.protocol.methods.Address;
import org.vitej.core.utils.NumericUtils;

import java.math.BigInteger;

/**
 * SBP info
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
     * Return name of SBP
     *
     * @return Name of SBP
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Return block producing address
     *
     * @return Block producing address
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
     * Return address of reward withdrawal account
     *
     * @return Address of reward withdrawal account
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
     * Return address of registration account
     *
     * @return Address of registration account
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
     * Return amount of staking
     *
     * @return Amount of staking
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
     * Return target unlocking height. Registered SBP node can be cancelled after the locking
     * period expires.
     *
     * @return Target unlocking height
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
     * Return estimated target unlocking time
     *
     * @return Estimated target unlocking time
     */
    public Long getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Long expirationTime) {
        this.expirationTime = expirationTime;
    }

    /**
     * Return time of cancellation
     *
     * @return Time of cancellation. For non-cancelled SBP nodes, 0 is filled
     */
    public Long getRevokeTime() {
        return revokeTime;
    }

    public void setRevokeTime(Long revokeTime) {
        this.revokeTime = revokeTime;
    }
}
