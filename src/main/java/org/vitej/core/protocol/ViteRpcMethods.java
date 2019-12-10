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
 * go-vite RPC API
 */
public interface ViteRpcMethods {

    /**
     * Return sync status of the node
     *
     * @return Sync status of the node
     */
    Request<?, NetSyncInfoResponse> netSyncInfo();

    /**
     * Return synchron detail
     *
     * @return Synchron detail
     */
    Request<?, NetSyncDetailResponse> netSyncDetail();

    /**
     * Return node info
     *
     * @return Node info
     */
    Request<?, NetNodeInfoResponse> netNodeInfo();

    /**
     * Calculate a PoW nonce based on the given difficulty. Usually this method is called to obtain
     * an temporary amount quota upon sending a transaction with no staking.
     *
     * @param difficulty PoW difficulty
     * @param data       Blake2b (address + previousHash),
     *                   For example,
     *                   if address is vite_ab24ef68b84e642c0ddca06beec81c9acb1977bbd7da27a87a and
     *                   previousHash is 0000000000000000000000000000000000000000000000000000000000000000,
     *                   the hash value is 8689fc3e7d0bcad0a1213fd90ab53437ce745408750f7303a16c75bad28da8c3
     * @return Nonce
     */
    Request<?, PoWNonceResponse> getPoWNonce(BigInteger difficulty, Hash data);

    /**
     * Return account blocks in descent order by height
     *
     * @param address   Account address
     * @param pageIndex Page index, start with 0
     * @param pageSize  Page size
     * @return Account blocks
     */
    Request<?, AccountBlocksResponse> getAccountBlocksByAddress(Address address, int pageIndex, int pageSize);

    /**
     * The same as getAccountBlocksByAddress, keyPair field is required at initialization
     *
     * @param pageIndex Page index, start with 0
     * @param pageSize  Page size
     * @return Account blocks
     */
    Request<?, AccountBlocksResponse> getSelfAccountBlocksByAddress(int pageIndex, int pageSize);

    /**
     * Return account block by height
     *
     * @param address Account address
     * @param height  Height of account block
     * @return Account block
     */
    Request<?, AccountBlockResponse> getAccountBlockByHeight(Address address, Long height);

    /**
     * The same as getAccountBlockByHeight, keyPair field is required at initialization
     *
     * @param height Height of account block
     * @return Account block
     */
    Request<?, AccountBlockResponse> getSelfAccountBlockByHeight(Long height);

    /**
     * Return account block by hash
     *
     * @param hash Hash of account block
     * @return Account block
     */
    Request<?, AccountBlockResponse> getAccountBlockByHash(Hash hash);

    /**
     * Return the latest account block
     *
     * @param address Account address
     * @return Account block
     */
    Request<?, AccountBlockResponse> getLatestAccountBlock(Address address);

    /**
     * getLatestAccountBlock, keyPair field is required at initialization
     *
     * @return Account block
     */
    Request<?, AccountBlockResponse> getSelfLatestAccountBlock();

    /**
     * Batch return account blocks
     *
     * @param address        Account address
     * @param startBlockHash Hash of account block, optional. For the latest block, filling in null
     * @param tokenId        Token type id, optional. This is used to get the transactions
     *                       associated with certain token. Otherwise, filling in null
     * @param count          Number of account blocks
     * @return Account blocks
     */
    Request<?, AccountBlocksResponse> getAccountBlocks(Address address, Hash startBlockHash, TokenId tokenId, int count);

    /**
     * The same as getAccountBlocks, keyPair field is required at initialization
     *
     * @param startBlockHash Hash of account block, optional. For the latest block, filling in null
     * @param tokenId        Token type id, optional. This is used to get the transactions
     *                       associated with certain token. Otherwise, filling in null
     * @param count          Number of account blocks
     * @return Account blocks
     */
    Request<?, AccountBlocksResponse> getSelfAccountBlocks(Hash startBlockHash, TokenId tokenId, int count);

    /**
     * Return account info by address
     *
     * @param address Account address
     * @return Account info
     */
    Request<?, AccountInfoResponse> getAccountInfoByAddress(Address address);

    /**
     * The same asgetAccountInfoByAddress, keyPair field is required at initialization
     *
     * @return Account info
     */
    Request<?, AccountInfoResponse> getSelfAccountInfo();

    /**
     * Return all unreceived transactions by address
     *
     * @param address   Account address
     * @param pageIndex Page index, start with 0
     * @param pageSize  Page size
     * @return Unreceived transactions
     */
    Request<?, AccountBlocksResponse> getUnreceivedBlocksByAddress(Address address, int pageIndex, int pageSize);

    /**
     * The same as getUnreceivedBlocksByAddress, keyPair field is required at initialization
     *
     * @param pageIndex Page index, start with 0
     * @param pageSize  Page size
     * @return Unreceived transactions
     */
    Request<?, AccountBlocksResponse> getSelfUnreceivedBlocks(int pageIndex, int pageSize);

    /**
     * Return unreceived transaction summary by address
     *
     * @param address Account address
     * @return Unreceived transaction summary
     */
    Request<?, AccountInfoResponse> getUnreceivedTransactionSummaryByAddress(Address address);

    /**
     * The same as getUnreceivedTransactionSummaryByAddress, keyPair field is required at initialization
     *
     * @return Unreceived transaction summary
     */
    Request<?, AccountInfoResponse> getSelfUnreceivedTransactionSummary();

    /**
     * Return latest snapshot block hash
     *
     * @return Latest snapshot block hash
     */
    Request<?, LatestSnapshotHashResponse> getLatestSnapshotHash();

    /**
     * Return latest snapshot block
     *
     * @return Latest snapshot block
     */
    Request<?, SnapshotBlockResponse> getLatestSnapshotBlock();

    /**
     * Return current snapshot chain height
     *
     * @return Current snapshot chain height
     */
    Request<?, SnapshotChainHeightResponse> getSnapshotChainHeight();

    /**
     * Return snapshot block by hash
     *
     * @param hash Hash of snapshot block
     * @return Snapshot block
     */
    Request<?, SnapshotBlockResponse> getSnapshotBlockByHash(Hash hash);

    /**
     * Return snapshot block by height
     *
     * @param height Height of snapshot block
     * @return Snapshot block
     */
    Request<?, SnapshotBlockResponse> getSnapshotBlockByHeight(Long height);

    /**
     * Batch return snapshot blocks in descent order by height
     *
     * @param height Start height
     * @param count  Number of snapshot blocks
     * @return Snapshot blocks
     */
    Request<?, SnapshotBlocksResponse> getSnapshotBlocks(Long height, int count);

    /**
     * Return event logs generated in the given response block of contract
     *
     * @param hash Hash of contract account block
     * @return Event logs
     */
    Request<?, VmlogsResponse> getVmlogs(Hash hash);

    /**
     * Return event logs generated in contract response blocks by specified height range and topics
     *
     * @param filter FilterParam
     * @return Event logs
     */
    Request<?, VmlogInfosResponse> getVmlogsByFilter(VmLogFilter filter);

    /**
     * Create a new contract address
     *
     * @param address      Address of creator
     * @param height       Current height of account chain
     * @param previousHash Hash of previous account block
     * @return New contract address
     */
    Request<?, CreateContractAddressResponse> createContractAddress(Address address, Long height, Hash previousHash);

    /**
     * Return contract information by address
     *
     * @param address Address of contract
     * @return Contract information
     * @see <a href="https://mainnet.vite.wiki/zh/tutorial/contract/contract.html">https://mainnet.vite.wiki/zh/tutorial/contract/contract.html</a>
     */
    Request<?, ContractInfoResponse> getContractInfo(Address address);

    /**
     * Call contract's offchain method
     *
     * @param address      Address of contract
     * @param offchainCode Binary code for offchain query. This is the value of "OffChain Binary"
     *                     section generated when compiling the contract with --bin
     * @param data         Encoded passed-in parameters
     * @return Encoded return value. Use decode methods to get decoded value
     */
    Request<?, CallOffChainMethodResponse> callOffChainMethod(Address address, byte[] offchainCode, byte[] data);

    /**
     * Return quota balance by account
     *
     * @param address Address of account
     * @return Quota
     */
    Request<?, QuotaResponse> getQuotaByAccount(Address address);

    /**
     * The same as getQuotaByAccount, keyPair field is required at initialization
     *
     * @return Quota
     */
    Request<?, QuotaResponse> getSelfQuota();

    /**
     * Return staking records by account, ordered by target unlocking height in descending order
     *
     * @param address   Address of staking account
     * @param pageIndex Page index, starting from 0
     * @param pageSize  Page size
     * @return Staking records
     */
    Request<?, StakeListResponse> getStakeList(Address address, int pageIndex, int pageSize);

    /**
     * The same as getStakeList, keyPair field is required at initialization
     *
     * @param pageIndex Page index, starting from 0
     * @param pageSize  Page size
     * @return Staking records
     */
    Request<?, StakeListResponse> getSelfStakeList(int pageIndex, int pageSize);

    /**
     * Return the minimum required amount of staking in order to obtain the given quota
     *
     * @param quotaPerSnapshotBlock Quotas accumulated per second. For example, an amount of 21,000
     *                              quota are consumed to send a transaction with no comment, in
     *                              this case, to satisfy the minimum 280 (21000/75) quota
     *                              accumulation per second in an epoch, a staking amount of 134
     *                              VITE is required
     * @return The minimum required amount of staking
     */
    Request<?, StakeAmountResponse> getRequiredStakeAmount(Long quotaPerSnapshotBlock);

    /**
     * Return registered SBP list, including historical SBP nodes, ordered by target unlocking
     * height in descending order
     *
     * @param stakeAddress Address of registration account
     * @return SBP list
     */
    Request<?, SBPListResponse> getSBPList(Address stakeAddress);

    /**
     * The same as getSBPList, keyPair field is required at initialization
     *
     * @return SBP list
     */
    Request<?, SBPListResponse> getSelfSBPList();

    /**
     * Return un-retrieved SBP rewards by SBP name
     *
     * @param sbpName Name of SBP
     * @return Reward info
     */
    Request<?, SBPRewardResponse> getSBPRewardPendingWithdrawal(String sbpName);

    /**
     * Return SBP rewards of all SBP nodes by cycle
     *
     * @param cycle Index of cycle
     * @return SBP rewards of all SBP nodes in the cycle
     */
    Request<?, SBPRewardDetailResponse> getSBPRewardByCycle(Long cycle);

    /**
     * Return SBP node information
     *
     * @param sbpName Name of SBP
     * @return SBP info
     */
    Request<?, SBPResponse> getSBP(String sbpName);

    /**
     * Return current number of votes of all SBP nodes
     *
     * @return Current number of votes of all SBP nodes
     */
    Request<?, SBPVoteListResponse> getSBPVoteList();

    /**
     * Return voting information by account
     *
     * @param address Address of voting account
     * @return Voting information
     */
    Request<?, VotedSBPResponse> getVotedSBP(Address address);

    /**
     * The same as getVotedSBP, keyPair field is required at initialization
     *
     * @return Voting information
     */
    Request<?, VotedSBPResponse> getSelfVotedSBP();

    /**
     * Return voting details of all SBP nodes by cycle
     *
     * @param cycle Index of cycle
     * @return Voting details of all SBP nodes in last round of the cycle
     */
    Request<?, SBPVoteDetailsResponse> getSBPVoteDetailsByCycle(Long cycle);

    /**
     * Return a list of all tokens issued
     *
     * @param pageIndex Page index, starting from 0
     * @param pageSize  Page size
     * @return Token information list
     */
    Request<?, TokenInfoListWithTotalResponse> getTokenInfoList(int pageIndex, int pageSize);

    /**
     * Return token information
     *
     * @param tokenId Token id
     * @return token information
     */
    Request<?, TokenInfoResponse> getTokenInfoById(TokenId tokenId);

    /**
     * Return a list of tokens issued by the given owner
     *
     * @param address Address of token owner
     * @return Token information list
     */
    Request<?, TokenInfoListResponse> getTokenInfoListByOwner(Address address);

    /**
     * The same as getTokenInfoListByOwner, keyPair field is required at initialization
     *
     * @return Token information list
     */
    Request<?, TokenInfoListResponse> getSelfTokenInfoList();

    /**
     * Send a transaction
     *
     * @param keyPair     Key pair to sign the transaction
     * @param transaction Transaction information
     * @param autoPoW     Calculate PoW automatically when quota of the account is not enough
     *                    to send the transaction
     * @return Send transaction result
     * @throws IOException Network requests may be performed when filling in transaction fields,
     *                     which may throws IOException
     */
    Request<?, EmptyResponse> sendTransaction(KeyPair keyPair, TransactionParams transaction, Boolean autoPoW) throws IOException;

    /**
     * The same as sendTransaction, keyPair field is required at initialization
     *
     * @param transaction Transaction information
     * @param autoPoW     Calculate PoW automatically when quota of the account is not enough
     *                    to send the transaction
     * @return Send transaction result
     * @throws IOException Network requests may be performed when filling in transaction fields,
     *                     which may throws IOException
     */
    Request<?, EmptyResponse> selfSendTransaction(TransactionParams transaction, Boolean autoPoW) throws IOException;

    /**
     * Return PoW difficulty for sending transaction
     *
     * @param transaction Transaction information
     * @return Quota required for sending the transaction and PoW difficulty
     * @throws IOException Network requests may be performed when filling in transaction fields,
     *                     which may throws IOException
     */
    Request<?, PoWDifficultyResponse> getPoWDifficulty(TransactionParams transaction) throws IOException;

    /**
     * Return quota required for sending the transaction
     *
     * @param transaction Transaction information
     * @return Quota required for sending the transaction
     */
    Request<?, RequiredQuotaResponse> getRequiredQuota(TransactionParams transaction);
}
