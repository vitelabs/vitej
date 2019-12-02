package org.vitej.core.protocol;

import org.vitej.core.protocol.methods.Address;
import org.vitej.core.protocol.methods.Hash;
import org.vitej.core.protocol.methods.TokenId;
import org.vitej.core.protocol.methods.request.Request;
import org.vitej.core.protocol.methods.request.TransactionParams;
import org.vitej.core.protocol.methods.request.VmLogFilter;
import org.vitej.core.protocol.methods.response.*;
import org.vitej.core.wallet.KeyPair;

import java.io.IOException;
import java.math.BigInteger;

/**
 * go-vite提供的rpc接口
 */
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
     * @param data       Blake2b (address + previousHash)，
     *                   例如，当 address 为 vite_ab24ef68b84e642c0ddca06beec81c9acb1977bbd7da27a87a ，
     *                   previousHash 为 0000000000000000000000000000000000000000000000000000000000000000 时，
     *                   结果为 8689fc3e7d0bcad0a1213fd90ab53437ce745408750f7303a16c75bad28da8c3
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

    /**
     * 根据hash查询账户块
     *
     * @param hash 查询的 AccountBlock 的 Hash
     * @return 用户账户块
     */
    Request<?, AccountBlockResponse> getAccountBlockByHash(Hash hash);

    /**
     * 查询最新的账户块
     *
     * @param address 查询账号地址
     * @return 用户账户块
     */
    Request<?, AccountBlockResponse> getLatestAccountBlock(Address address);

    /**
     * getLatestAccountBlock，要求初始化vitej时传入keyPair
     *
     * @return 用户账户块
     */
    Request<?, AccountBlockResponse> getSelfLatestAccountBlock();

    /**
     * 批量查询账户块
     *
     * @param address        账户地址
     * @param startBlockHash （可选）查询的起始AccountBlock的Hash。如果填写null，则默认为当前账户最新的AccountBlock的Hash
     * @param tokenId        （可选）筛选出与TokenTypeId相关的AccountBlocks。如果填写null，则不筛选AccountBlocks
     * @param count          查询的AccountBlock数量
     * @return 用户账户块列表
     */
    Request<?, AccountBlocksResponse> getAccountBlocks(Address address, Hash startBlockHash, TokenId tokenId, int count);

    /**
     * 同getAccountBlocks，要求初始化vitej时传入keyPair
     *
     * @param startBlockHash （可选）查询的起始AccountBlock的Hash。如果填写null，则默认为当前账户最新的AccountBlock的Hash
     * @param tokenId        （可选）筛选出与TokenTypeId相关的AccountBlocks。如果填写null，则不筛选AccountBlocks
     * @param count          查询的AccountBlock数量
     * @return 用户账户块列表
     */
    Request<?, AccountBlocksResponse> getSelfAccountBlocks(Hash startBlockHash, TokenId tokenId, int count);

    /**
     * 查询账户信息，包括账户余额和账户链高度
     *
     * @param address 账户地址
     * @return 账户信息
     */
    Request<?, AccountInfoResponse> getAccountInfoByAddress(Address address);

    /**
     * 同getAccountInfoByAddress，要求初始化vitej时传入keyPair
     *
     * @return 账户信息
     */
    Request<?, AccountInfoResponse> getSelfAccountInfo();

    /**
     * 查询待接收账户块列表
     *
     * @param address   账户地址
     * @param pageIndex 页码，从 0 开始
     * @param pageSize  每页大小
     * @return 待接收账户块列表
     */
    Request<?, AccountBlocksResponse> getUnreceivedBlocksByAddress(Address address, int pageIndex, int pageSize);

    /**
     * 同getUnreceivedBlocksByAddress，要求初始化vitej时传入keyPair
     *
     * @param pageIndex 页码，从 0 开始
     * @param pageSize  每页大小
     * @return 待接收账户块列表
     */
    Request<?, AccountBlocksResponse> getSelfUnreceivedBlocks(int pageIndex, int pageSize);

    /**
     * 查询待接收账户块信息汇总，包括交易数量和余额
     *
     * @param address 账户地址
     * @return 待接收账户块信息汇总
     */
    Request<?, UnreceivedTransactionSummaryResponse> getUnreceivedTransactionSummaryByAddress(Address address);

    /**
     * 同getUnreceivedTransactionSummaryByAddress，要求初始化vitej时传入keyPair
     *
     * @return 待接收账户块信息汇总
     */
    Request<?, UnreceivedTransactionSummaryResponse> getSelfUnreceivedTransactionSummary();

    /**
     * 获取最新的快照块hash
     *
     * @return 最新的快照块hash
     */
    Request<?, LatestSnapshotHashResponse> getLatestSnapshotHash();

    /**
     * 获取当前快照链高度
     *
     * @return 快照链高度
     */
    Request<?, SnapshotChainHeightResponse> getSnapshotChainHeight();

    /**
     * 根据hash查询快照块
     *
     * @param hash 快照块hash
     * @return 快照块
     */
    Request<?, SnapshotBlockResponse> getSnapshotBlockByHash(Hash hash);

    /**
     * 根据高度查询快照块
     *
     * @param height 快照块高度
     * @return 快照块
     */
    Request<?, SnapshotBlockResponse> getSnapshotBlockByHeight(Long height);

    /**
     * 查询vmlog
     *
     * @param hash 账户块hash
     * @return vmlog
     */
    Request<?, VmlogsResponse> getVmlogs(Hash hash);

    /**
     * 批量查询vmlog，返回值按账户块高度从低到高排序
     *
     * @param filter 过滤参数
     * @return vmlog
     */
    Request<?, VmlogInfosResponse> getVmlogsByFilter(VmLogFilter filter);

    /**
     * 创建合约时生成新的合约地址
     *
     * @param address      交易发起方账户地址
     * @param height       当前账户块高度
     * @param previousHash 交易发起方账户链上上一个块的哈希
     * @return 新的合约地址
     */
    Request<?, CreateContractAddressResponse> createContractAddress(Address address, Long height, Hash previousHash);

    /**
     * 查询合约信息
     *
     * @param address 合约账户地址
     * @return 合约信息
     */
    Request<?, ContractInfoResponse> getContractInfo(Address address);

    /**
     * 离线调用合约的getter方法。
     *
     * @param address      合约账户地址
     * @param offchainCode 用于离线查询的合约代码。即编译代码时指定--bin参数后得到的OffChain Binary代码
     * @param data         按ABI定义编码后的调用参数，类似调用合约时的交易data
     * @return getter方法返回值，可以用ABI反解析
     */
    Request<?, CallOffChainMethodResponse> callOffChainMethod(Address address, byte[] offchainCode, byte[] data);

    /**
     * 查询账户配额
     *
     * @param address 账户地址
     * @return 账户配额
     */
    Request<?, QuotaResponse> getQuotaByAccount(Address address);

    /**
     * 同getQuotaByAccount，要求初始化vitej时传入keyPair
     *
     * @return 账户配额
     */
    Request<?, QuotaResponse> getSelfQuota();

    /**
     * 查询账户的抵押信息列表，按到期快照块高度倒序排序
     *
     * @param address   抵押账户地址
     * @param pageIndex 页码，从 0 开始
     * @param pageSize  每页条数
     * @return 抵押信息列表
     */
    Request<?, StakeListResponse> getStakeList(Address address, int pageIndex, int pageSize);

    /**
     * 同getStakeList，要求初始化vitej时传入keyPair
     *
     * @param pageIndex 页码，从 0 开始
     * @param pageSize  每页条数
     * @return 抵押信息列表
     */
    Request<?, StakeListResponse> getSelfStakeList(int pageIndex, int pageSize);

    /**
     * 根据配额计算最小抵押金额
     *
     * @param quotaPerSnapshotBlock 每秒使用的配额，例如：
     *                              以 1/75 TPS 的交易频率发不带备注的转账交易，
     *                              单笔交易消耗的配额为 21000，
     *                              每秒消耗的配额为 21000/75=280，
     *                              此时最少需要抵押 134 VITE
     * @return 最小抵押金额
     */
    Request<?, StakeAmountResponse> getRequiredStakeAmount(Long quotaPerSnapshotBlock);

    /**
     * 查询注册的超级节点列表，包括已取消注册的超级节点，返回结果中未取消在前，已取消在后，按抵押到期高度倒序排列
     *
     * @param stakeAddress 注册账户地址
     * @return 注册的超级节点列表
     */
    Request<?, SBPListResponse> getSBPList(Address stakeAddress);

    /**
     * 同getSBPList，要求初始化vitej时传入keyPair
     *
     * @return 注册的超级节点列表
     */
    Request<?, SBPListResponse> getSelfSBPList();

    /**
     * 查询超级节点待提取奖励
     *
     * @param sbpName 超级节点名称
     * @return 待提取奖励
     */
    Request<?, SBPRewardResponse> getSBPRewardPendingWithdrawal(String sbpName);

    /**
     * 按周期查询某一天所有超级节点的奖励
     *
     * @param cycle 周期
     * @return 该周期内所有超级节点的奖励
     */
    Request<?, SBPRewardDetailResponse> getSBPRewardByCycle(Long cycle);

    /**
     * 根据名称查询超级节点信息
     *
     * @param sbpName 超级节点名称
     * @return 超级节点信息
     */
    Request<?, SBPResponse> getSBP(String sbpName);

    /**
     * 查询所有超级节点当前获得的投票数
     *
     * @return 所有超级节点当前获得的投票数
     */
    Request<?, SBPVoteListResponse> getSBPVoteList();

    /**
     * 查询投票信息
     *
     * @param address 账户地址
     * @return 投票的超级节点和投票信息
     */
    Request<?, VotedSBPResponse> getVotedSBP(Address address);

    /**
     * 同getVotedSBP，要求初始化vitej时传入keyPair
     *
     * @return 投票的超级节点和投票信息
     */
    Request<?, VotedSBPResponse> getSelfVotedSBP();

    /**
     * 按周期查询当天最后一轮共识的超级节点的投票明细
     *
     * @param cycle 周期
     * @return 当天最后一轮共识的超级节点的投票明细
     */
    Request<?, SBPVoteDetailsResponse> getSBPVoteDetailsByCycle(Long cycle);

    /**
     * 查询代币信息列表
     *
     * @param pageIndex 页码，从 0 开始
     * @param pageSize  每页条数
     * @return 代币信息
     */
    Request<?, TokenInfoListWithTotalResponse> getTokenInfoList(int pageIndex, int pageSize);

    /**
     * 查询代币信息
     *
     * @param tokenId 代币id
     * @return 代币信息
     */
    Request<?, TokenInfoResponse> getTokenInfoById(TokenId tokenId);

    /**
     * 根据代币所有者账户地址查询代币信息列表
     *
     * @param address 代币所有者账户地址
     * @return 代币信息
     */
    Request<?, TokenInfoListResponse> getTokenInfoListByOwner(Address address);

    /**
     * 同getTokenInfoListByOwner，要求初始化vitej时传入keyPair
     *
     * @return 代币信息
     */
    Request<?, TokenInfoListResponse> getSelfTokenInfoList();

    /**
     * 发交易
     *
     * @param keyPair     签名账户的地址和私钥
     * @param transaction 发送的交易内容，只需要填写基本的交易字段
     * @param autoPoW     是否自动计算PoW
     * @return 完整的交易信息
     * @throws IOException 填充交易内容时会产生网络请求，可能会抛出IOException
     */
    Request<?, EmptyResponse> sendTransaction(KeyPair keyPair, TransactionParams transaction, Boolean autoPoW) throws IOException;

    /**
     * 发交易，要求初始化vitej时传入keyPair
     *
     * @param transaction 发送的交易内容，只需要填写基本的交易字段
     * @param autoPoW     是否自动计算PoW
     * @return 完整的交易信息
     * @throws IOException 填充交易内容时会产生网络请求，可能会抛出IOException
     */
    Request<?, EmptyResponse> selfSendTransaction(TransactionParams transaction, Boolean autoPoW) throws IOException;

    /**
     * 计算交易所需配额和是否需要计算PoW
     *
     * @param transaction 交易内容，只需要填写基本的交易信息
     * @return 交易所所需配额和是否需要计算PoW
     * @throws IOException 填充交易内容时会产生网络请求，可能会抛出IOException
     */
    Request<?, PoWDifficultyResponse> getPoWDifficulty(TransactionParams transaction) throws IOException;

    /**
     * 计算交易所需配额
     *
     * @param transaction 交易内容，只需要填写基本的交易信息
     * @return 交易所所需配额
     */
    Request<?, RequiredQuotaResponse> getRequiredQuota(TransactionParams transaction);
}
