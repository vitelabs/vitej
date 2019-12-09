package org.vitej.core.protocol.methods.request;

import org.vitej.core.protocol.methods.Address;
import org.vitej.core.utils.BytesUtils;

/**
 * Call contract's offchain method parameters
 */
public class CallOffChainMethodParams {
    private String address;
    private String offChainCode;
    private String data;

    /**
     * Call contract's offchain method parameters
     *
     * @param address      Address of contract
     * @param offChainCode Binary code for offchain query. This is the value of "OffChain Binary" section generated
     *                     when compiling the contract with --bin
     * @param data         Encoded passed-in parameters
     */
    public CallOffChainMethodParams(Address address, byte[] offChainCode, byte[] data) {
        this.address = address.toString();
        this.offChainCode = BytesUtils.bytesToHexString(offChainCode);
        this.data = BytesUtils.bytesToBase64(data);
    }

    public String getAddress() {
        return address;
    }

    public CallOffChainMethodParams setAddress(Address address) {
        this.address = address.toString();
        return this;
    }

    public String getOffChainCode() {
        return offChainCode;
    }

    public CallOffChainMethodParams setOffChainCode(byte[] offChainCode) {
        this.offChainCode = BytesUtils.bytesToHexString(offChainCode);
        return this;
    }

    public String getData() {
        return data;
    }

    public CallOffChainMethodParams setData(byte[] data) {
        this.data = BytesUtils.bytesToBase64(data);
        return this;
    }
}
