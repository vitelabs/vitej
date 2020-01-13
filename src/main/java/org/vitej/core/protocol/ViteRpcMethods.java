package org.vitej.core.protocol;

import org.vitej.core.protocol.methods.Address;
import org.vitej.core.protocol.methods.Hash;
import org.vitej.core.protocol.methods.TokenId;
import org.vitej.core.protocol.methods.request.IssueTokenParams;
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
     * Return complete account block by hash
     *
     * @param hash Hash of account block
     * @return Complete account block. If account block is contract send block, returns
     * relative receive block and send blocks
     */
    Request<?, AccountBlockResponse> getCompleteAccountBlockByHash(Hash hash);

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
     * Send a transaction
     *
     * @param keyPair     Key pair to sign the transaction
     * @param transaction Transaction information
     * @return Send transaction result
     * @throws IOException Network requests may be performed when filling in transaction fields,
     *                     which may throws IOException
     */
    Request<?, EmptyResponse> sendTransaction(KeyPair keyPair, TransactionParams transaction) throws IOException;

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
     * The same as sendTransaction, keyPair field is required at initialization
     *
     * @param transaction Transaction information
     * @return Send transaction result
     * @throws IOException Network requests may be performed when filling in transaction fields,
     *                     which may throws IOException
     */
    Request<?, EmptyResponse> selfSendTransaction(TransactionParams transaction) throws IOException;

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

    /**
     * Call go-vite RPC method which is not listed above
     *
     * @param methodName   RPC method name
     * @param methodParams RPC method params
     * @return Call method response
     * @see <a href="https://vite.wiki/api/rpc">https://vite.wiki/api/rpc</a>
     */
    Request<?, CommonResponse> commonMethod(String methodName, Object... methodParams);

    /**
     * Send a transaction to built-in quota contract to obtain quota. The VITE staked will be
     * temporarily deducted from user's balance and cannot be transferred during staking period.
     * The staking account is able to retrieve staked tokens after 259,200 snapshot blocks
     * (about 3 days) by sending a cancel-staking transaction.
     *
     * @param keyPair     Key pair to pay the tokens and sign the transaction
     * @param beneficiary The address of the account who receives quota. This is not limited to
     *                    the staking account but can be any other account. In other words, you
     *                    can stake for others.
     * @param amount      The minimum staking amount is 134 VITE
     * @return Send transaction result
     * @throws IOException Network requests may be performed when filling in transaction fields,
     *                     which may throws IOException
     */
    Request<?, EmptyResponse> stakeForQuota(KeyPair keyPair, Address beneficiary, BigInteger amount) throws IOException;

    /**
     * Send a transaction to built-in quota contract to retrieve staked tokens. As a result, the
     * beneficiary account will lose the quota correspondingly.
     *
     * @param keyPair       Key pair who payed the tokens and to sign the transaction
     * @param sendBlockHash Send block hash of stakeForQuota transaction
     * @return Send transaction result
     * @throws IOException Network requests may be performed when filling in transaction fields,
     *                     which may throws IOException
     */
    Request<?, EmptyResponse> cancelQuotaStaking(KeyPair keyPair, Hash sendBlockHash) throws IOException;

    /**
     * Send a transaction to built-in governance contract to become a snapshot block producer.
     * 1,000,000 VITE staking is required. Staking cannot be retrieved immediately after
     * registration. The lock-up period is approximately 3 months (7776000 snapshot blocks).
     * After the locking period expires, the SBP's owner (registration account) can cancel the
     * SBP registration and retrieve staked VITE tokens.
     *
     * @param keyPair               Key pair to pay the tokens and sign the transaction
     * @param sbpName               String of 1-40 characters, uppercase and lowercase letters,
     *                              numbers, space, underscores and dots. Duplicated names are
     *                              not allowed. SBP name is mainly used for voting.
     * @param blockProducingAddress It is highly recommended to use a different address from SBP
     *                              registration address for security reason. Block producing
     *                              address can be changed by sending an updateSBPBlockProducingAddress
     *                              transaction by registration account.
     * @param rewardWithdrawAddress An authorized address to withdraw SBP rewards except the
     *                              registration account.
     * @return Send transaction result
     * @throws IOException Network requests may be performed when filling in transaction fields,
     *                     which may throws IOException
     */
    Request<?, EmptyResponse> registerSBP(KeyPair keyPair, String sbpName, Address blockProducingAddress, Address rewardWithdrawAddress) throws IOException;

    /**
     * Send a transaction to built-in governance contract to update SBP producing address.
     *
     * @param keyPair               Key pair of the registration account and to sign the transaction.
     * @param sbpName               SBP name
     * @param blockProducingAddress The new block producing address
     * @return Send transaction result
     * @throws IOException Network requests may be performed when filling in transaction fields,
     *                     which may throws IOException
     */
    Request<?, EmptyResponse> updateSBPBlockProducingAddress(KeyPair keyPair, String sbpName, Address blockProducingAddress) throws IOException;

    /**
     * Send a transaction to built-in governance contract to update SBP reward withdraw address.
     * The new address is authorized to withdraw SBP reward of the certain SBP producer. Meanwhile
     * the old reward withdraw address is unauthorized.
     *
     * @param keyPair               Key pair of the registration account and to sign the transaction.
     * @param sbpName               SBP name
     * @param rewardWithdrawAddress The new reward withdraw address
     * @return Send transaction result
     * @throws IOException Network requests may be performed when filling in transaction fields,
     *                     which may throws IOException
     */
    Request<?, EmptyResponse> updateSBPRewardWithdrawAddress(KeyPair keyPair, String sbpName, Address rewardWithdrawAddress) throws IOException;

    /**
     * Send a transaction to built-in governance contract to cancel a SBP. The node will be removed
     * from SBP list once the transaction is confirmed.
     *
     * @param keyPair Key pair of the registration account and to sign the transaction.
     * @param sbpName SBP name to perform the cancellation
     * @return Send transaction result
     * @throws IOException Network requests may be performed when filling in transaction fields,
     *                     which may throws IOException
     */
    Request<?, EmptyResponse> revokeSBP(KeyPair keyPair, String sbpName) throws IOException;

    /**
     * Send a transaction to built-in governance contract to withdraw SBP rewards. All available
     * rewards will be withdrawn. Available rewards are the rewards allocated since the time of
     * last withdrawal till one hour ago.
     *
     * @param keyPair        Key pair of the registration account or the authorized reward withdraw
     *                       address and to sign the transaction.
     * @param sbpName        SBP name to withdraw reward
     * @param receiveAddress Withdrawn rewards will be sent to this address
     * @return Send transaction result
     * @throws IOException Network requests may be performed when filling in transaction fields,
     *                     which may throws IOException
     */
    Request<?, EmptyResponse> withdrawSBPReward(KeyPair keyPair, String sbpName, Address receiveAddress) throws IOException;

    /**
     * Send a transaction to built-in governance contract to vote for SBP. An account can only
     * vote for one SBP.
     * Votes are calculated every round. The delegated nodes for next round will be elected based
     * on the voting result at the time being.
     *
     * @param keyPair Key pair to vote and to sign the transaction
     * @param sbpName SBP name to vote for
     * @return Send transaction result
     * @throws IOException Network requests may be performed when filling in transaction fields,
     *                     which may throws IOException
     */
    Request<?, EmptyResponse> voteForSBP(KeyPair keyPair, String sbpName) throws IOException;

    /**
     * Send a transaction to built-in governance contract to cancel SBP voting.
     *
     * @param keyPair Key pair to cancel voting and to sign the transaction
     * @return Send transaction result
     * @throws IOException Network requests may be performed when filling in transaction fields,
     *                     which may throws IOException
     */
    Request<?, EmptyResponse> cancelSBPVoting(KeyPair keyPair) throws IOException;

    /**
     * Send a transaction to built-in asset contract to issue a token. Issue token transaction will
     * burn 1000 VITE. Once the transaction is processed successfully, a total supply amount of
     * tokens will be sent to the issuer's account and the issuer is assigned as owner.
     *
     * @param keyPair Key pair to pay the issue fee and to sign the transaction
     * @param params  Issue token params, including token name, token symbol, total supply,
     *                decimals and token type.
     * @return Send transaction result
     * @throws IOException Network requests may be performed when filling in transaction fields,
     *                     which may throws IOException
     */
    Request<?, EmptyResponse> issueToken(KeyPair keyPair, IssueTokenParams params) throws IOException;

    /**
     * Send a transaction to built-in asset contract to reissue token. Once the transaction is
     * processed successfully, asset contract will transfer the newly issued tokens to the
     * specified account.
     *
     * @param keyPair        Key pair of token owner and to sign the transaction
     * @param tokenId        Reissue token id
     * @param amount         Reissue amount
     * @param receiveAddress Account address to receive newly issued tokens
     * @return Send transaction result
     * @throws IOException Network requests may be performed when filling in transaction fields,
     *                     which may throws IOException
     */
    Request<?, EmptyResponse> reIssue(KeyPair keyPair, TokenId tokenId, BigInteger amount, Address receiveAddress) throws IOException;

    /**
     * Send a transaction to built-in asset contract to burn token. The token burned will be
     * deducted from user's balance. Once the transaction is processed successfully by the
     * contract, the token's total supply will be reduced to reflect the change.
     *
     * @param keyPair Key pair to burn the token and to sign the transaction.
     * @param tokenId Burn token id
     * @param amount  Burn token amount
     * @return Send transaction result
     * @throws IOException Network requests may be performed when filling in transaction fields,
     *                     which may throws IOException
     */
    Request<?, EmptyResponse> burn(KeyPair keyPair, TokenId tokenId, BigInteger amount) throws IOException;

    /**
     * Send a transaction to built-in asset contract to transfer token ownership to a new owner.
     * A certain token can have only one owner at a moment. Only re-issuable token ownership can
     * be transfered.
     *
     * @param keyPair  Key pair of old token owner and to sign the transaction.
     * @param tokenId  Token id to be transfered ownership
     * @param newOwner New owner of the token
     * @return Send transaction result
     * @throws IOException Network requests may be performed when filling in transaction fields,
     *                     which may throws IOException
     */
    Request<?, EmptyResponse> transferOwnership(KeyPair keyPair, TokenId tokenId, Address newOwner) throws IOException;

    /**
     * Send a transaction to built-in asset contract to change token type. Re-issuable tokens can
     * be changed to non-reissuable, or fixed-supply, tokens. However, this process is one-way
     * only, meaning once the type is changed, it cannot be changed back.
     *
     * @param keyPair Key pair of old token owner and to sign the transaction.
     * @param tokenId Token id
     * @return Send transaction result
     * @throws IOException Network requests may be performed when filling in transaction fields,
     *                     which may throws IOException
     */
    Request<?, EmptyResponse> disableReIssue(KeyPair keyPair, TokenId tokenId) throws IOException;
}
