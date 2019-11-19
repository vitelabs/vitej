package org.vitej.core.protocol.methods;

import org.vitej.core.protocol.methods.request.TransactionParams;
import org.vitej.core.protocol.methods.request.VmLogFilter;
import org.vitej.core.protocol.methods.response.*;
import org.vitej.core.wallet.KeyPair;

import java.io.IOException;
import java.math.BigInteger;

public interface ViteRpcMethods {
    Request<?, NetSyncInfoResponse> netSyncInfo();

    Request<?, NetSyncDetailResponse> netSyncDetail();

    Request<?, NetNodeInfoResponse> netNodeInfo();

    Request<?, PoWNonceResponse> getPoWNonce(BigInteger difficulty, Hash data);

    Request<?, AccountBlocksResponse> getAccountBlocksByAddress(Address address, int pageIndex, int pageSize);

    Request<?, AccountBlockResponse> getAccountBlockByHeight(Address address, Long height);

    Request<?, AccountBlockResponse> getAccountBlockByHash(Hash hash);

    Request<?, AccountBlockResponse> getLatestAccountBlock(Address address);

    Request<?, AccountBlocksResponse> getAccountBlocks(Address address, Hash startBlockHash, TokenId tokenId, int count);

    Request<?, AccountInfoResponse> getAccountInfoByAddress(Address address);

    Request<?, AccountBlocksResponse> getUnreceivedBlocksByAddress(Address address, int pageIndex, int pageSize);

    Request<?, UnreceivedTransactionSummaryResponse> getUnreceivedTransactionSummaryByAddress(Address address);

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

    Request<?, StakeListResponse> getStakeList(Address address, int pageIndex, int pageSize);

    Request<?, StakeAmountResponse> getRequiredStakeAmount(Long quotaPerSnapshotBlock);

    Request<?, SBPListResponse> getSBPList(Address stakeAddress);

    Request<?, SBPRewardResponse> getSBPRewardPendingWithdrawal(String sbpName);

    Request<?, SBPRewardDetailResponse> getSBPRewardByCycle(Long cycle);

    Request<?, SBPResponse> getSBP(String sbpName);

    Request<?, SBPVoteListResponse> getSBPVoteList();

    Request<?, VotedSBPResponse> getVotedSBP(Address address);

    Request<?, SBPVoteDetailsResponse> getSBPVoteDetailsByCycle(Long cycle);

    Request<?, TokenInfoListWithTotalResponse> getTokenInfoList(int pageIndex, int pageSize);

    Request<?, TokenInfoResponse> getTokenInfoById(TokenId tokenId);

    Request<?, TokenInfoListResponse> getTokenInfoListByOwner(Address address);

    Request<?, EmptyResponse> sendTransaction(KeyPair keyPair, TransactionParams transaction, Boolean autoPoW) throws IOException;

    Request<?, PoWDifficultyResponse> getPoWDifficulty(TransactionParams transaction) throws IOException;

    Request<?, RequiredQuotaResponse> getRequiredQuota(TransactionParams transaction);

    boolean checkCallContractResult(Hash sendBlockHash) throws IOException;

    boolean checkCallContractResult(Hash sendBlockHash, int retryTimes) throws IOException;
}
