package org.vitej.core.protocol.methods;

import org.vitej.core.protocol.methods.request.TransactionParams;
import org.vitej.core.protocol.methods.request.VmLogFilter;
import org.vitej.core.protocol.methods.response.*;
import org.vitej.core.wallet.KeyPair;

import java.io.IOException;
import java.math.BigInteger;

public interface ViteRpcMethods {

    /**
     * 查看节点同步状态
     *
     * @return 同步状态信息
     */
    Request<?, NetSyncInfoResponse> netSyncInfo();

    /**
     * 查看同步详情
     *
     * @return 同步状态详细信息
     */
    Request<?, NetSyncDetailResponse> netSyncDetail();

    /**
     * 查看当前节点的信息
     *
     * @return 节点网络信息
     */
    Request<?, NetNodeInfoResponse> netNodeInfo();

    /**
     * 计算PoW
     *
     * @param difficulty PoW难度，可以通过getPoWDifficulty接口获取
     * @param data       Blake2b (address + previousHash)，例如，当 address 为 vite_ab24ef68b84e642c0ddca06beec81c9acb1977bbd7da27a87a ，previousHash 为 0000000000000000000000000000000000000000000000000000000000000000 时，结果为 8689fc3e7d0bcad0a1213fd90ab53437ce745408750f7303a16c75bad28da8c3
     * @return PoW的nonce，对应AccountBlock的nonce字段
     */
    Request<?, PoWNonceResponse> getPoWNonce(BigInteger difficulty, Hash data);

    /**
     * 根据用户地址查询账户块
     *
     * @param address   查询账号地址
     * @param pageIndex 分页查询 AccountBlocks 的页数，顺序是按 AccountBlocks 的高度从高到低
     * @param pageSize  分页查询 AccountBlocks 的个数，顺序是按 AccountBlocks 的高度从高到低
     * @return 账户块列表
     */
    Request<?, AccountBlocksResponse> getAccountBlocksByAddress(Address address, int pageIndex, int pageSize);

    /**
     * 同getAccountBlocksByAddress，要求初始化vitej时传入keyPair
     *
     * @param pageIndex 分页查询AccountBlocks的页数，顺序是按AccountBlocks的高度从高到低
     * @param pageSize  分页查询AccountBlocks的个数，顺序是按AccountBlocks的高度从高到低
     * @return 账户块列表
     */
    Request<?, AccountBlocksResponse> getSelfAccountBlocksByAddress(int pageIndex, int pageSize);

    /**
     * 根据高度查询账户块
     *
     * @param address 查询账号地址
     * @param height  查询的AccountBlock的高度
     * @return 用户账户块
     */
    Request<?, AccountBlockResponse> getAccountBlockByHeight(Address address, Long height);

    /**
     * 同getAccountBlockByHeight，要求初始化vitej时传入keyPair
     *
     * @param height 查询的AccountBlock的高度
     * @return 用户账户块
     */
    Request<?, AccountBlockResponse> getSelfAccountBlockByHeight(Long height);

    Request<?, AccountBlockResponse> getAccountBlockByHash(Hash hash);

    Request<?, AccountBlockResponse> getLatestAccountBlock(Address address);

    Request<?, AccountBlockResponse> getSelfLatestAccountBlock();

    Request<?, AccountBlocksResponse> getAccountBlocks(Address address, Hash startBlockHash, TokenId tokenId, int count);

    Request<?, AccountBlocksResponse> getSelfAccountBlocks(Hash startBlockHash, TokenId tokenId, int count);

    Request<?, AccountInfoResponse> getAccountInfoByAddress(Address address);

    Request<?, AccountInfoResponse> getSelfAccountInfo();

    Request<?, AccountBlocksResponse> getUnreceivedBlocksByAddress(Address address, int pageIndex, int pageSize);

    Request<?, AccountBlocksResponse> getSelfUnreceivedBlocks(int pageIndex, int pageSize);

    Request<?, UnreceivedTransactionSummaryResponse> getUnreceivedTransactionSummaryByAddress(Address address);

    Request<?, UnreceivedTransactionSummaryResponse> getSelfUnreceivedTransactionSummary();

    Request<?, LatestSnapshotHashResponse> getLatestSnapshotHash();

    Request<?, SnapshotChainHeightResponse> getSnapshotChainHeight();

    Request<?, SnapshotBlockResponse> getSnapshotBlockByHash(Hash hash);

    Request<?, SnapshotBlockResponse> getSnapshotBlockByHeight(Long height);

    Request<?, VmlogsResponse> getVmlogs(Hash hash);

    Request<?, VmlogInfosResponse> getVmlogsByFilter(VmLogFilter filter);

    Request<?, CreateContractAddressResponse> createContractAddress(Address address, Long height, Hash previousHash);

    Request<?, ContractInfoResponse> getContractInfo(Address address);

    Request<?, CallOffChainMethodResponse> callOffChainMethod(Address address, byte[] offchainCode, byte[] data);

    Request<?, QuotaResponse> getQuotaByAccount(Address address);

    Request<?, QuotaResponse> getSelfQuota();

    Request<?, StakeListResponse> getStakeList(Address address, int pageIndex, int pageSize);

    Request<?, StakeListResponse> getSelfStakeList(int pageIndex, int pageSize);

    Request<?, StakeAmountResponse> getRequiredStakeAmount(Long quotaPerSnapshotBlock);

    Request<?, SBPListResponse> getSBPList(Address stakeAddress);

    Request<?, SBPListResponse> getSelfSBPList();

    Request<?, SBPRewardResponse> getSBPRewardPendingWithdrawal(String sbpName);

    Request<?, SBPRewardDetailResponse> getSBPRewardByCycle(Long cycle);

    Request<?, SBPResponse> getSBP(String sbpName);

    Request<?, SBPVoteListResponse> getSBPVoteList();

    Request<?, VotedSBPResponse> getVotedSBP(Address address);

    Request<?, VotedSBPResponse> getSelfVotedSBP();

    Request<?, SBPVoteDetailsResponse> getSBPVoteDetailsByCycle(Long cycle);

    Request<?, TokenInfoListWithTotalResponse> getTokenInfoList(int pageIndex, int pageSize);

    Request<?, TokenInfoResponse> getTokenInfoById(TokenId tokenId);

    Request<?, TokenInfoListResponse> getTokenInfoListByOwner(Address address);

    Request<?, TokenInfoListResponse> getSelfTokenInfoList();

    Request<?, EmptyResponse> sendTransaction(KeyPair keyPair, TransactionParams transaction, Boolean autoPoW) throws IOException;

    Request<?, EmptyResponse> selfSendTransaction(TransactionParams transaction, Boolean autoPoW) throws IOException;

    Request<?, PoWDifficultyResponse> getPoWDifficulty(TransactionParams transaction) throws IOException;

    Request<?, RequiredQuotaResponse> getRequiredQuota(TransactionParams transaction);

    boolean checkCallContractResult(Hash sendBlockHash) throws IOException;

    boolean checkCallContractResult(Hash sendBlockHash, int retryTimes) throws IOException;
}
