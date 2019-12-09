package org.vitej.core.protocol.methods.response;

import org.vitej.core.protocol.methods.Address;

public class CreateContractAddressResponse extends Response<String> {
    /**
     * Return contract address
     *
     * @return Contract address
     */
    public Address getAddress() {
        return Address.stringToAddress(getResult());
    }
}
