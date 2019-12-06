package org.vitej.core.utils;

import org.apache.commons.lang3.ArrayUtils;
import org.vitej.core.constants.CommonConstants;
import org.vitej.core.protocol.methods.Address;
import org.vitej.core.protocol.methods.Hash;
import org.vitej.core.protocol.methods.enums.EBlockType;
import org.vitej.core.protocol.methods.request.TransactionParams;
import org.vitej.core.wallet.KeyPair;

public class BlockUtils {
    public static Boolean isSendBlock(Integer blockType) {
        return EBlockType.SEND_CALL.getValue() == blockType ||
                EBlockType.SEND_CREATE.getValue() == blockType ||
                EBlockType.SEND_ISSUE.getValue() == blockType ||
                EBlockType.SEND_REFUND.getValue() == blockType;
    }

    public static Boolean isReceiveBlock(Integer blockType) {
        return !isSendBlock(blockType);
    }

    public static Boolean isContractReceiveSuccess(Address address, Integer blockType, byte[] data) {
        return address.isContract()
                && isReceiveBlock(blockType)
                && data != null
                && data.length == 33
                && data[32] == 0;
    }

    public static Hash computeHash(TransactionParams transaction) {
        byte[] source = {transaction.getBlockType().byteValue()};
        source = BytesUtils.merge(source,
                transaction.getPreviousHashRaw().getBytes(),
                BytesUtils.leftPadBytes(BytesUtils.longToBytes(transaction.getHeightRaw()), 8),
                transaction.getAddressRaw().getBytes()
        );
        if (isSendBlock(transaction.getBlockType())) {
            source = BytesUtils.merge(source, transaction.getToAddressRaw().getBytes());
            source = BytesUtils.merge(source, BytesUtils.bigIntegerToBytes(transaction.getAmountRaw(), 32));
            source = BytesUtils.merge(source, transaction.getTokenIdRaw().getBytes());
        } else {
            source = BytesUtils.merge(source, transaction.getSendBlockHashRaw().getBytes());
        }
        if (ArrayUtils.isNotEmpty(transaction.getDataRaw())) {
            source = BytesUtils.merge(source, Hash.dataToHash(transaction.getDataRaw()).getBytes());
        }
        source = BytesUtils.merge(source, BytesUtils.bigIntegerToBytes(transaction.getFeeRaw(), 32));
        if (transaction.getNonce() != null) {
            source = BytesUtils.merge(source, BytesUtils.leftPadBytes(transaction.getNonceRaw(), 8));
        } else {
            source = BytesUtils.merge(source, CommonConstants.EMPTY_BYTES_8);
        }
        return Hash.dataToHash(source);
    }

    public static byte[] computeSigunature(KeyPair keyPair, TransactionParams transaction) {
        return keyPair.sign(transaction.getHashRaw().getBytes());
    }

    public static Hash getPoWData(TransactionParams transaction) {
        return Hash.dataToHash(BytesUtils.merge(transaction.getAddressRaw().getBytes(), transaction.getPreviousHashRaw().getBytes()));
    }
}
