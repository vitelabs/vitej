package org.vitej.core.protocol.methods.response;

import org.vitej.core.protocol.methods.Address;

public class CreateContractAddressResponse extends Response<String> {
    /**
     * 获取新的合约地址
     *
     * @return 新的合约地址
     */
    public Address getAddress() {
        return Address.stringToAddress(getResult());
    }
}
