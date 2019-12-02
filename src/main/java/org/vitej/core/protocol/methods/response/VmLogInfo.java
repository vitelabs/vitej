package org.vitej.core.protocol.methods.response;

import org.vitej.core.protocol.methods.Address;
import org.vitej.core.protocol.methods.Hash;
import org.vitej.core.utils.NumericUtils;

/**
 * vmlog和所属的合约账户块信息
 */
public class VmLogInfo {
    private Vmlog vmlog;
    private String accountBlockHash;
    private String accountBlockHeight;
    private String address;

    /**
     * 获取vmlog
     *
     * @return vmlog
     */
    public Vmlog getVmlog() {
        return vmlog;
    }

    public void setVmlog(Vmlog vmlog) {
        this.vmlog = vmlog;
    }

    /**
     * 获取vmlog所属的账户块hash
     *
     * @return vmlog所属的账户块hash
     */
    public Hash getAccountBlockHash() {
        return Hash.stringToHash(accountBlockHash);
    }

    public String getAccountBlockHashRaw() {
        return accountBlockHash;
    }

    public void setAccountBlockHash(String accountBlockHash) {
        this.accountBlockHash = accountBlockHash;
    }

    /**
     * 获取vmlog所属的账户块高度
     *
     * @return vmlog所属的账户块高度
     */
    public Long getAccountBlockHeight() {
        return NumericUtils.stringToLong(accountBlockHeight);
    }

    public String getAccountBlockHeightRaw() {
        return accountBlockHeight;
    }

    public void setAccountBlockHeight(String accountBlockHeight) {
        this.accountBlockHeight = accountBlockHeight;
    }

    /**
     * 获取vmlog所属的账户地址
     *
     * @return vmlog所属的账户地址
     */
    public Address getAddress() {
        return Address.stringToAddress(address);
    }

    public String getAddressRaw() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
