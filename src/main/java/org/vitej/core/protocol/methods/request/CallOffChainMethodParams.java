package org.vitej.core.protocol.methods.request;

import org.vitej.core.protocol.methods.Address;
import org.vitej.core.utils.BytesUtils;

/**
 * 离线调用合约getter方法参数
 */
public class CallOffChainMethodParams {
    /**
     * 合约账户地址
     */
    private String address;
    /**
     * 用于离线查询的合约代码。即编译代码时指定--bin参数后得到的OffChain Binary代码
     */
    private String offChainCode;
    /**
     * 按ABI定义编码后的调用参数，类似调用合约时的交易data。
     */
    private String data;

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
