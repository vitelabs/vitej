package org.vitej.core.protocol;

import com.google.common.base.Preconditions;
import org.vitej.core.constants.CommonConstants;
import org.vitej.core.protocol.methods.Address;
import org.vitej.core.protocol.methods.Hash;
import org.vitej.core.protocol.methods.TokenId;
import org.vitej.core.protocol.methods.enums.EBlockType;
import org.vitej.core.protocol.methods.request.CallOffChainMethodParams;
import org.vitej.core.protocol.methods.request.Request;
import org.vitej.core.protocol.methods.request.TransactionParams;
import org.vitej.core.protocol.methods.request.VmLogFilter;
import org.vitej.core.protocol.methods.response.*;
import org.vitej.core.utils.BlockUtils;
import org.vitej.core.utils.ContractUtils;
import org.vitej.core.wallet.KeyPair;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;

/**
 * An implementation of vite RPC APIs
 */
public class Vitej implements ViteRpcMethods {
    private final RpcService rpcService;
    private KeyPair keyPair;

    public Vitej(RpcService rpcService) {
        this.rpcService = rpcService;
    }

    public Vitej(RpcService rpcService, KeyPair keyPair) {
        this.rpcService = rpcService;
        this.keyPair = keyPair;
    }

    public RpcService getRpcService() {
        return rpcService;
    }

    public KeyPair getKeyPair() {
        return keyPair;
    }

    public void setKeyPair(KeyPair keyPair) {
        this.keyPair = keyPair;
    }

    @Override
    public Request<?, NetSyncInfoResponse> netSyncInfo() {
        return new Request<>(
                "net_syncInfo",
                Collections.emptyList(),
                rpcService,
                NetSyncInfoResponse.class);
    }

    @Override
    public Request<?, NetSyncDetailResponse> netSyncDetail() {
        return new Request<>(
                "net_syncDetail",
                Collections.emptyList(),
                rpcService,
                NetSyncDetailResponse.class);
    }

    @Override
    public Request<?, NetNodeInfoResponse> netNodeInfo() {
        return new Request<>(
                "net_nodeInfo",
                Collections.emptyList(),
                rpcService,
                NetNodeInfoResponse.class);
    }

    @Override
    public Request<?, PoWNonceResponse> getPoWNonce(BigInteger difficulty, Hash data) {
        return new Request<>(
                "util_getPoWNonce",
                Arrays.asList(difficulty.toString(), data.toString()),
                rpcService,
                PoWNonceResponse.class);
    }

    @Override
    public Request<?, AccountBlocksResponse> getAccountBlocksByAddress(Address address, int index, int count) {
        return new Request<>(
                "ledger_getAccountBlocksByAddress",
                Arrays.asList(address.toString(), index, count),
                rpcService,
                AccountBlocksResponse.class);
    }

    @Override
    public Request<?, AccountBlocksResponse> getSelfAccountBlocksByAddress(int pageIndex, int pageSize) {
        Preconditions.checkNotNull(keyPair);
        return getAccountBlocksByAddress(keyPair.getAddress(), pageIndex, pageSize);
    }

    @Override
    public Request<?, AccountBlockResponse> getAccountBlockByHeight(Address address, Long height) {
        return new Request<>(
                "ledger_getAccountBlockByHeight",
                Arrays.asList(address.toString(), height.toString()),
                rpcService,
                AccountBlockResponse.class);
    }

    @Override
    public Request<?, AccountBlockResponse> getSelfAccountBlockByHeight(Long height) {
        Preconditions.checkNotNull(keyPair);
        return getAccountBlockByHeight(keyPair.getAddress(), height);
    }

    @Override
    public Request<?, AccountBlockResponse> getAccountBlockByHash(Hash hash) {
        return new Request<>(
                "ledger_getAccountBlockByHash",
                Arrays.asList(hash.toString()),
                rpcService,
                AccountBlockResponse.class);
    }

    @Override
    public Request<?, AccountBlockResponse> getLatestAccountBlock(Address address) {
        return new Request<>(
                "ledger_getLatestAccountBlock",
                Arrays.asList(address.toString()),
                rpcService,
                AccountBlockResponse.class);
    }

    @Override
    public Request<?, AccountBlockResponse> getSelfLatestAccountBlock() {
        Preconditions.checkNotNull(keyPair);
        return getLatestAccountBlock(keyPair.getAddress());
    }

    @Override
    public Request<?, AccountBlocksResponse> getAccountBlocks(Address address, Hash startBlockHash, TokenId tokenId, int count) {
        return new Request<>(
                "ledger_getAccountBlocks",
                Arrays.asList(address.toString(), startBlockHash == null ? null : startBlockHash.toString(), tokenId == null ? null : tokenId.toString(), count),
                rpcService,
                AccountBlocksResponse.class);
    }

    @Override
    public Request<?, AccountBlocksResponse> getSelfAccountBlocks(Hash startBlockHash, TokenId tokenId, int count) {
        Preconditions.checkNotNull(keyPair);
        return getAccountBlocks(keyPair.getAddress(), startBlockHash, tokenId, count);
    }

    @Override
    public Request<?, AccountInfoResponse> getAccountInfoByAddress(Address address) {
        return new Request<>(
                "ledger_getAccountInfoByAddress",
                Arrays.asList(address.toString()),
                rpcService,
                AccountInfoResponse.class);
    }

    @Override
    public Request<?, AccountInfoResponse> getSelfAccountInfo() {
        Preconditions.checkNotNull(keyPair);
        return getAccountInfoByAddress(keyPair.getAddress());
    }

    @Override
    public Request<?, AccountBlocksResponse> getUnreceivedBlocksByAddress(Address address, int index, int count) {
        return new Request<>(
                "ledger_getUnreceivedBlocksByAddress",
                Arrays.asList(address.toString(), index, count),
                rpcService,
                AccountBlocksResponse.class);
    }

    @Override
    public Request<?, AccountBlocksResponse> getSelfUnreceivedBlocks(int pageIndex, int pageSize) {
        Preconditions.checkNotNull(keyPair);
        return getUnreceivedBlocksByAddress(keyPair.getAddress(), pageIndex, pageSize);
    }

    @Override
    public Request<?, AccountInfoResponse> getUnreceivedTransactionSummaryByAddress(Address address) {
        return new Request<>(
                "ledger_getUnreceivedTransactionSummaryByAddress",
                Arrays.asList(address.toString()),
                rpcService,
                AccountInfoResponse.class);
    }

    @Override
    public Request<?, AccountInfoResponse> getSelfUnreceivedTransactionSummary() {
        Preconditions.checkNotNull(keyPair);
        return getUnreceivedTransactionSummaryByAddress(keyPair.getAddress());
    }

    @Override
    public Request<?, LatestSnapshotHashResponse> getLatestSnapshotHash() {
        return new Request<>(
                "ledger_getLatestSnapshotHash",
                Collections.emptyList(),
                rpcService,
                LatestSnapshotHashResponse.class);
    }

    @Override
    public Request<?, SnapshotBlockResponse> getLatestSnapshotBlock() {
        return new Request<>(
                "ledger_getLatestSnapshotBlock",
                Collections.emptyList(),
                rpcService,
                SnapshotBlockResponse.class);
    }

    @Override
    public Request<?, SnapshotChainHeightResponse> getSnapshotChainHeight() {
        return new Request<>(
                "ledger_getSnapshotChainHeight",
                Collections.emptyList(),
                rpcService,
                SnapshotChainHeightResponse.class);
    }

    @Override
    public Request<?, SnapshotBlockResponse> getSnapshotBlockByHash(Hash hash) {
        return new Request<>(
                "ledger_getSnapshotBlockByHash",
                Arrays.asList(hash.toString()),
                rpcService,
                SnapshotBlockResponse.class);
    }

    @Override
    public Request<?, SnapshotBlockResponse> getSnapshotBlockByHeight(Long height) {
        return new Request<>(
                "ledger_getLatestSnapshotBlock",
                Arrays.asList(height),
                rpcService,
                SnapshotBlockResponse.class);
    }

    @Override
    public Request<?, SnapshotBlocksResponse> getSnapshotBlocks(Long height, int count) {
        return new Request<>(
                "ledger_getSnapshotBlocks",
                Arrays.asList(height.toString(), count),
                rpcService,
                SnapshotBlocksResponse.class);
    }

    @Override
    public Request<?, VmlogsResponse> getVmlogs(Hash hash) {
        return new Request<>(
                "ledger_getVmLogs",
                Arrays.asList(hash.toString()),
                rpcService,
                VmlogsResponse.class);
    }

    @Override
    public Request<?, VmlogInfosResponse> getVmlogsByFilter(VmLogFilter filter) {
        return new Request<>(
                "ledger_getVmLogsByFilter",
                Arrays.asList(filter),
                rpcService,
                VmlogInfosResponse.class);
    }

    @Override
    public Request<?, CreateContractAddressResponse> createContractAddress(Address address, Long height, Hash previousHash) {
        return new Request<>(
                "contract_createContractAddress",
                Arrays.asList(address.toString(), height.toString(), previousHash.toString()),
                rpcService,
                CreateContractAddressResponse.class);
    }

    @Override
    public Request<?, ContractInfoResponse> getContractInfo(Address address) {
        return new Request<>(
                "contract_getContractInfo",
                Arrays.asList(address.toString()),
                rpcService,
                ContractInfoResponse.class);
    }

    @Override
    public Request<?, CallOffChainMethodResponse> callOffChainMethod(Address address, byte[] offchainCode, byte[] data) {
        return new Request<>(
                "contract_callOffChainMethod",
                Arrays.asList(new CallOffChainMethodParams(address, offchainCode, data)),
                rpcService,
                CallOffChainMethodResponse.class);
    }

    @Override
    public Request<?, QuotaResponse> getQuotaByAccount(Address address) {
        return new Request<>(
                "contract_getQuotaByAccount",
                Arrays.asList(address.toString()),
                rpcService,
                QuotaResponse.class);
    }

    @Override
    public Request<?, QuotaResponse> getSelfQuota() {
        Preconditions.checkNotNull(keyPair);
        return getQuotaByAccount(keyPair.getAddress());
    }

    @Override
    public Request<?, StakeListResponse> getStakeList(Address address, int index, int count) {
        return new Request<>(
                "contract_getStakeList",
                Arrays.asList(address.toString(), index, count),
                rpcService,
                StakeListResponse.class);
    }

    @Override
    public Request<?, StakeListResponse> getSelfStakeList(int pageIndex, int pageSize) {
        Preconditions.checkNotNull(keyPair);
        return getStakeList(keyPair.getAddress(), pageIndex, pageSize);
    }

    @Override
    public Request<?, StakeAmountResponse> getRequiredStakeAmount(Long quotaPerSnapshotBlock) {
        return new Request<>(
                "contract_getRequiredStakeAmount",
                Arrays.asList(quotaPerSnapshotBlock.toString()),
                rpcService,
                StakeAmountResponse.class);
    }

    @Override
    public Request<?, SBPListResponse> getSBPList(Address stakeAddress) {
        return new Request<>(
                "contract_getSBPVoteList",
                Arrays.asList(stakeAddress.toString()),
                rpcService,
                SBPListResponse.class);
    }

    @Override
    public Request<?, SBPListResponse> getSelfSBPList() {
        Preconditions.checkNotNull(keyPair);
        return getSBPList(keyPair.getAddress());
    }

    @Override
    public Request<?, SBPRewardResponse> getSBPRewardPendingWithdrawal(String sbpName) {
        return new Request<>(
                "contract_getSBPRewardPendingWithdrawal",
                Arrays.asList(sbpName),
                rpcService,
                SBPRewardResponse.class);
    }

    @Override
    public Request<?, SBPRewardDetailResponse> getSBPRewardByCycle(Long cycle) {
        return new Request<>(
                "contract_getSBPRewardByCycle",
                Arrays.asList(cycle.toString()),
                rpcService,
                SBPRewardDetailResponse.class);
    }

    @Override
    public Request<?, SBPResponse> getSBP(String sbpName) {
        return new Request<>(
                "contract_getSBP",
                Arrays.asList(sbpName),
                rpcService,
                SBPResponse.class);
    }

    @Override
    public Request<?, SBPVoteListResponse> getSBPVoteList() {
        return new Request<>(
                "contract_getSBPVoteList",
                Collections.emptyList(),
                rpcService,
                SBPVoteListResponse.class);
    }

    @Override
    public Request<?, VotedSBPResponse> getVotedSBP(Address address) {
        return new Request<>(
                "contract_getVotedSBP",
                Arrays.asList(address.toString()),
                rpcService,
                VotedSBPResponse.class);
    }

    @Override
    public Request<?, VotedSBPResponse> getSelfVotedSBP() {
        Preconditions.checkNotNull(keyPair);
        return getVotedSBP(keyPair.getAddress());
    }

    @Override
    public Request<?, SBPVoteDetailsResponse> getSBPVoteDetailsByCycle(Long cycle) {
        return new Request<>(
                "contract_getSBPVoteDetailsByCycle",
                Arrays.asList(cycle.toString()),
                rpcService,
                SBPVoteDetailsResponse.class);
    }

    @Override
    public Request<?, TokenInfoListWithTotalResponse> getTokenInfoList(int pageIndex, int pageSize) {
        return new Request<>(
                "contract_getTokenInfoList",
                Arrays.asList(pageIndex, pageSize),
                rpcService,
                TokenInfoListWithTotalResponse.class);
    }

    @Override
    public Request<?, TokenInfoResponse> getTokenInfoById(TokenId tokenId) {
        return new Request<>(
                "contract_getTokenInfoById",
                Arrays.asList(tokenId.toString()),
                rpcService,
                TokenInfoResponse.class);
    }

    @Override
    public Request<?, TokenInfoListResponse> getTokenInfoListByOwner(Address address) {
        return new Request<>(
                "contract_getTokenInfoListByOwner",
                Arrays.asList(address.toString()),
                rpcService,
                TokenInfoListResponse.class);
    }

    @Override
    public Request<?, TokenInfoListResponse> getSelfTokenInfoList() {
        Preconditions.checkNotNull(keyPair);
        return getTokenInfoListByOwner(keyPair.getAddress());
    }

    @Override
    public Request<?, EmptyResponse> sendTransaction(
            KeyPair keyPair,
            TransactionParams transaction,
            Boolean autoPoW)
            throws IOException {
        Preconditions.checkNotNull(keyPair, "key pair");
        transaction.setAddress(keyPair.getAddress());
        transaction.setPublicKey(keyPair.getPublicKey());
        if (transaction.getBlockType() == null) {
            transaction.setBlockType(EBlockType.SEND_CALL.getValue());
        }
        updateTransactionPreviousHashAndHeight(transaction);

        if (BlockUtils.isSendBlock(transaction.getBlockType())) {
            if (EBlockType.SEND_CREATE.getValue() == transaction.getBlockType()) {
                Address contractAddress = ContractUtils.getNewContractAddress(transaction);
                Preconditions.checkArgument(transaction.getToAddressRaw() == null
                                || contractAddress.equals(transaction.getToAddressRaw()),
                        "to address");
                transaction.setToAddress(contractAddress);
                transaction.setFee(CommonConstants.CREATE_CONTRACT_FEE);
            } else {
                Preconditions.checkNotNull(transaction.getToAddressRaw(), "to address");
            }
            Preconditions.checkArgument(
                    transaction.getSendBlockHashRaw() == null
                            || transaction.getSendBlockHashRaw().equals(CommonConstants.EMPTY_HASH),
                    "send block hash");
            if (transaction.getTokenIdRaw() == null) {
                transaction.setTokenId(CommonConstants.VITE_TOKEN_ID);
            }
            if (transaction.getSendBlockHashRaw() == null) {
                transaction.setSendBlockHash(CommonConstants.EMPTY_HASH);
            }
        } else {
            Preconditions.checkArgument(
                    transaction.getToAddressRaw() == null
                            || transaction.getToAddressRaw().equals(CommonConstants.EMPTY_ADDRESS),
                    "to address");
            transaction.setToAddress(CommonConstants.EMPTY_ADDRESS);
            Preconditions.checkNotNull(transaction.getSendBlockHashRaw(), "send block hash");
            Preconditions.checkArgument(
                    transaction.getTokenIdRaw() == null
                            || transaction.getTokenIdRaw().equals(CommonConstants.EMPTY_TOKENID),
                    "token id");
            transaction.setTokenId(CommonConstants.EMPTY_TOKENID);
            Preconditions.checkArgument(
                    transaction.getAmountRaw() == null
                            || transaction.getAmountRaw().signum() == 0,
                    "amount");
            Preconditions.checkArgument(
                    transaction.getFeeRaw() == null || transaction.getFeeRaw().signum() == 0,
                    "fee");
            Preconditions.checkArgument(
                    transaction.getDataRaw() == null
                            || transaction.getDataRaw().length == 0,
                    "data");
        }
        if (transaction.getAmountRaw() == null) {
            transaction.setAmount(BigInteger.ZERO);
        }
        if (transaction.getFeeRaw() == null) {
            transaction.setFee(BigInteger.ZERO);
        }
        if (transaction.getDataRaw() == null) {
            transaction.setData(new byte[]{});
        }
        Preconditions.checkArgument(
                (transaction.getDifficultyRaw() == null && transaction.getNonceRaw() == null)
                        || (transaction.getDifficultyRaw() != null && transaction.getNonceRaw() != null),
                "difficulty and nonce");
        if (transaction.getDifficultyRaw() == null) {
            if (autoPoW) {
                PoWDifficultyResponse response = getPoWDifficulty(transaction).send();
                Preconditions.checkArgument(response.getError() == null, response.getError());
                if (response.getResult().getDifficulty() != null) {
                    transaction.setDifficulty(response.getResult().getDifficulty());
                    PoWNonceResponse nonceResponse = getPoWNonce(transaction.getDifficultyRaw(), BlockUtils.getPoWData(transaction)).send();
                    Preconditions.checkArgument(nonceResponse.getError() == null, nonceResponse.getError());
                    transaction.setNonce(nonceResponse.getNonce());
                }
            }
        }
        transaction.setHash(BlockUtils.computeHash(transaction));
        transaction.setSignature(BlockUtils.computeSigunature(keyPair, transaction));

        return new Request<>(
                "ledger_sendRawTransaction",
                Arrays.asList(transaction),
                rpcService,
                EmptyResponse.class);
    }

    @Override
    public Request<?, EmptyResponse> selfSendTransaction(TransactionParams transaction, Boolean autoPoW) throws IOException {
        Preconditions.checkNotNull(keyPair);
        return sendTransaction(keyPair, transaction, autoPoW);
    }

    @Override
    public Request<?, PoWDifficultyResponse> getPoWDifficulty(TransactionParams transaction) throws IOException, IllegalArgumentException {
        if (transaction.getBlockType() == null) {
            transaction.setBlockType(EBlockType.SEND_CALL.getValue());
        }
        updateTransactionPreviousHashAndHeight(transaction);
        return new Request<>(
                "ledger_getPoWDifficulty",
                Arrays.asList(transaction),
                rpcService,
                PoWDifficultyResponse.class);
    }

    @Override
    public Request<?, RequiredQuotaResponse> getRequiredQuota(TransactionParams transaction) {
        if (transaction.getBlockType() == null) {
            transaction.setBlockType(EBlockType.SEND_CALL.getValue());
        }
        return new Request<>(
                "ledger_getRequiredQuota",
                Arrays.asList(transaction),
                rpcService,
                RequiredQuotaResponse.class);
    }

    private void updateTransactionPreviousHashAndHeight(TransactionParams transaction) throws IOException {
        if (transaction.getHeightRaw() == null && transaction.getPreviousHashRaw() == null) {
            AccountBlockResponse response = getLatestAccountBlock(transaction.getAddressRaw()).send();
            Preconditions.checkArgument(response.getError() == null, response.getError());
            if (response.getResult() != null) {
                transaction.setHeight(response.getResult().getHeight() + 1);
                transaction.setPreviousHash(response.getResult().getHash());
            } else {
                transaction.setHeight(1L);
                transaction.setPreviousHash(CommonConstants.EMPTY_HASH);
            }
        } else if (transaction.getHeightRaw() != null) {
            if (transaction.getHeightRaw() > 1) {
                AccountBlockResponse response = getAccountBlockByHeight(transaction.getAddressRaw(), transaction.getHeightRaw() - 1).send();
                Preconditions.checkArgument(response.getError() == null, response.getError());
                Preconditions.checkNotNull(response.getResult(), "invalid account height");
                transaction.setPreviousHash(response.getResult().getHash());
            } else {
                transaction.setPreviousHash(CommonConstants.EMPTY_HASH);
            }
        } else if (transaction.getPreviousHashRaw() != null) {
            if (!transaction.getPreviousHashRaw().equals(CommonConstants.EMPTY_HASH)) {
                AccountBlockResponse response = getAccountBlockByHash(transaction.getPreviousHashRaw()).send();
                Preconditions.checkArgument(response.getError() == null, response.getError());
                Preconditions.checkNotNull(response.getResult(), "invalid previous hash");
                transaction.setHeight(response.getResult().getHeight() + 1);
            } else {
                transaction.setHeight(1L);
            }
        }
    }
}
