package org.vitej.core.utils;

import org.apache.commons.lang3.ArrayUtils;
import org.vitej.core.constants.CommonConstants;
import org.vitej.core.protocol.methods.Address;
import org.vitej.core.protocol.methods.Hash;
import org.vitej.core.protocol.methods.enums.EBlockType;
import org.vitej.core.protocol.methods.request.TransactionParams;
import org.vitej.core.wallet.KeyPair;

public final class BlockUtils {
    /**
     * Check whether a block type is a send block
     *
     * @param blockType Block type
     * @return True for send block, false for receive block
     */
    public static Boolean isSendBlock(Integer blockType) {
        return EBlockType.SEND_CALL.getValue() == blockType ||
                EBlockType.SEND_CREATE.getValue() == blockType ||
                EBlockType.SEND_ISSUE.getValue() == blockType ||
                EBlockType.SEND_REFUND.getValue() == blockType;
    }

    /**
     * Check whether a block type is a receive block
     *
     * @param blockType Block Type
     * @return True for receive block, false for send block
     */
    public static Boolean isReceiveBlock(Integer blockType) {
        return !isSendBlock(blockType);
    }

    /**
     * Check whether an account block is a contract receive block and receive success
     *
     * @param address   Account block address
     * @param blockType Account block type
     * @param data      Account block data
     * @return True for the account block is a contract receive success block, false otherwise
     */
    public static Boolean isContractReceiveSuccess(Address address, Integer blockType, byte[] data) {
        return address.isContract()
                && isReceiveBlock(blockType)
                && data != null
                && data.length == 33
                && data[32] == 0;
    }

    /**
     * Compute transaction hash, only for user account block, inapplicable for contract account block
     *
     * @param transaction Transaction info
     * @return Transaction hash
     */
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

    /**
     * Compute account block signature
     *
     * @param keyPair     Key pair to sign the transaction
     * @param transaction Transaction info, hash field is required
     * @return Signature
     */
    public static byte[] computeSigunature(KeyPair keyPair, TransactionParams transaction) {
        return keyPair.sign(transaction.getHashRaw().getBytes());
    }

    /**
     * Return hash value to calculate PoW nonce
     *
     * @param transaction Transaction info, address and previousHash fields are required
     * @return Hash value to calculate PoW nonce
     */
    public static Hash getPoWData(TransactionParams transaction) {
        return Hash.dataToHash(BytesUtils.merge(transaction.getAddressRaw().getBytes(), transaction.getPreviousHashRaw().getBytes()));
    }
}
