package org.vitej.core.utils;

import org.vitej.core.protocol.methods.Address;
import org.vitej.core.protocol.methods.request.TransactionParams;
import org.vitej.core.wallet.Crypto;

public class ContractUtils {
    public static Address getNewContractAddress(TransactionParams transaction) {
        return new Address(
                Crypto.digest(20, BytesUtils.merge(transaction.getAddressRaw().getBytes(),
                        BytesUtils.leftPadBytes(BytesUtils.longToBytes(transaction.getHeightRaw()), 8),
                        transaction.getPreviousHashRaw().getBytes())),
                1);
    }

    public static byte[] getCreateContractData(byte[] bytecode, byte[] encodedParameters, int snapshotCount, int snapshotWithSeedCount, int quotaMultiplier) {
        return BytesUtils.merge(
                new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 1, (byte) snapshotCount, (byte) snapshotWithSeedCount, (byte) quotaMultiplier},
                bytecode,
                encodedParameters);
    }

    public static byte[] getCreateContractData(byte[] bytecode, byte[] encodedParameters) {
        return getCreateContractData(bytecode, encodedParameters, 0, 0, 10);
    }
}
