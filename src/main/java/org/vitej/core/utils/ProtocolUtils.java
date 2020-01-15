package org.vitej.core.utils;

import com.google.common.base.Preconditions;
import org.apache.commons.collections4.CollectionUtils;
import org.vitej.core.constants.CommonConstants;
import org.vitej.core.protocol.ViteRpcMethods;
import org.vitej.core.protocol.methods.Hash;
import org.vitej.core.protocol.methods.enums.EBlockType;
import org.vitej.core.protocol.methods.response.AccountBlock;
import org.vitej.core.protocol.methods.response.AccountBlockResponse;

import java.io.IOException;

public final class ProtocolUtils {
    /**
     * Check call contract result
     *
     * @param v             Vitej client instance
     * @param sendBlockHash Send block hash
     * @param retryTimes    Retry times when checking whether a send block is received by contract,
     *                      sleep 1 second before each retry
     * @return True for contract receive success, false for send block not exist or send block
     * not received by contract within retry times or contract receive fail
     * @throws IOException Network requests may be performed when filling in transaction fields,
     *                     which may throws IOException
     */
    public static boolean checkCallContractResult(ViteRpcMethods v, Hash sendBlockHash, int retryTimes) throws IOException {
        AccountBlockResponse response = v.getAccountBlockByHash(sendBlockHash).send();
        Preconditions.checkArgument(response.getError() == null, response.getError());
        Preconditions.checkNotNull(response.getResult());
        Preconditions.checkArgument(
                (EBlockType.SEND_CALL.getValue() == response.getResult().getBlockType() && response.getResult().getToAddress().isContract()) ||
                        (EBlockType.SEND_CREATE.getValue() == response.getResult().getBlockType()),
                "invalid transaction type");
        Hash receiveBlockHash = response.getResult().getReceiveBlockHash();
        int index = 2;
        while (receiveBlockHash == null) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            response = v.getAccountBlockByHash(sendBlockHash).send();
            Preconditions.checkArgument(response.getError() == null, response.getError());
            Preconditions.checkNotNull(response.getResult());
            receiveBlockHash = response.getResult().getReceiveBlockHash();
            if (index >= retryTimes) {
                return false;
            }
            index++;
        }
        response = v.getAccountBlockByHash(receiveBlockHash).send();
        Preconditions.checkArgument(response.getError() == null, response.getError());
        Preconditions.checkNotNull(response.getResult());
        if (!response.getResult().isContractReceiveSuccess()) {
            return false;
        } else if (CollectionUtils.isEmpty(response.getResult().getTriggeredSendBlockList())) {
            return true;
        } else {
            for (AccountBlock sendBlock : response.getResult().getTriggeredSendBlockList()) {
                if (sendBlock.getToAddress().isContract()) {
                    if (!checkCallContractResult(v, sendBlock.getHash(), retryTimes)) {
                        return false;
                    }
                }
            }
            return true;
        }
    }

    /**
     * Check call contract result, retry 10 times for default
     *
     * @param v             Vitej client instance
     * @param sendBlockHash Send block hash
     * @return True for contract receive success, false for send block not exist or send block
     * not received by contract within retry times or contract receive fail
     * @throws IOException Network requests may be performed when filling in transaction fields,
     *                     which may throws IOException
     */
    public static boolean checkCallContractResult(ViteRpcMethods v, Hash sendBlockHash) throws IOException {
        return checkCallContractResult(v, sendBlockHash, CommonConstants.RETRY_TIMES);
    }
}
