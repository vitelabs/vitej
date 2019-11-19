package org.vitej.core.protocol.methods.request;

import org.vitej.core.protocol.methods.Address;
import org.vitej.core.utils.BytesUtils;

public class CallOffChainMethodParams {
    private String address;
    private String offChainCode;
    private String data;

    public CallOffChainMethodParams(Address address, byte[] offChainCode, byte[] data) {
        this.address = address.toString();
        this.offChainCode = BytesUtils.bytesToHexString(offChainCode);
        this.data = BytesUtils.bytesToBase64(data);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOffChainCode() {
        return offChainCode;
    }

    public void setOffChainCode(String offChainCode) {
        this.offChainCode = offChainCode;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
