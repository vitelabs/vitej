package org.vitej.core.protocol.methods.response;

import org.vitej.core.protocol.methods.Address;
import org.vitej.core.protocol.methods.Response;

public class CreateContractAddressResponse extends Response<String> {
    public Address getAddress() {
        return Address.stringToAddress(getResult());
    }
}
