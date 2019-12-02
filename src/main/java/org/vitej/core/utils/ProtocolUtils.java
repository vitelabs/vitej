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

public class ProtocolUtils {
    /**
     * 同checkCallContractResult，检查交易是否被接收时默认重试十次
     *
     * @param v             vitej client对象
     * @param sendBlockHash 请求交易哈希
     * @return true-请求成功，false-请求交易不存在，请求交易长时间不接收，或者接收失败
     * @throws IOException 本接口会产生网络请求，可能会抛出IOException
     */
    public static boolean checkCallContractResult(ViteRpcMethods v, Hash sendBlockHash) throws IOException {
        return checkCallContractResult(v, sendBlockHash, CommonConstants.RETRY_TIMES);
    }

    /**
     * 检查调用合约交易是否成功
     *
     * @param v             vitej client对象
     * @param sendBlockHash 请求交易哈希
     * @param retryTimes    检查交易是否被接收时的重试次数
     * @return true-请求成功，false-请求交易不存在，请求交易长时间不接收，或者接收失败
     * @throws IOException 本接口会产生网络请求，可能会抛出IOException
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
}
