package org.vitej.core.protocol.methods.response;

import org.vitej.core.protocol.methods.Address;
import org.vitej.core.protocol.methods.Hash;
import org.vitej.core.utils.NumericUtils;

/**
 * Event log and contract account block info
 */
public class VmLogInfo {
    private Vmlog vmlog;
    private String accountBlockHash;
    private String accountBlockHeight;
    private String address;

    /**
     * Return event log of smart contract
     *
     * @return Event log of smart contract
     */
    public Vmlog getVmlog() {
        return vmlog;
    }

    public void setVmlog(Vmlog vmlog) {
        this.vmlog = vmlog;
    }

    /**
     * Return sash of account block
     *
     * @return Hash of account block
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
     * Return height of account block
     *
     * @return Height of account block
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
     * Return address of account
     *
     * @return Address of account
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
