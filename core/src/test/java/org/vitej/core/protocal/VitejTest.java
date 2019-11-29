package org.vitej.core.protocal;

import org.junit.Assert;
import org.junit.Test;
import org.vitej.core.constants.BuiltinContracts;
import org.vitej.core.constants.CommonConstants;
import org.vitej.core.protocol.HttpService;
import org.vitej.core.protocol.Vitej;
import org.vitej.core.protocol.methods.Address;
import org.vitej.core.protocol.methods.Hash;
import org.vitej.core.protocol.methods.Request;
import org.vitej.core.protocol.methods.TokenId;
import org.vitej.core.protocol.methods.enums.EBlockType;
import org.vitej.core.protocol.methods.request.TransactionParams;
import org.vitej.core.protocol.methods.request.VmLogFilter;
import org.vitej.core.protocol.methods.response.*;
import org.vitej.core.utils.BytesUtils;
import org.vitej.core.utils.ContractUtils;
import org.vitej.core.utils.abi.Abi;
import org.vitej.core.wallet.KeyPair;
import org.vitej.core.wallet.Wallet;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class VitejTest {
    private Vitej vitej = new Vitej(new HttpService("http://127.0.0.1:48132"));

    @Test
    public void testSendTransaction() {
        Hash sendBlockHash = null;
        try {
            List<String> mnemonic = Arrays.asList("alarm", "canal", "scheme", "actor", "left", "length", "bracket", "slush", "tuna", "garage", "prepare", "scout", "school", "pizza", "invest", "rose", "fork", "scorpion", "make", "enact", "false", "kidney", "mixed", "vast");
            KeyPair keyPair = new Wallet(mnemonic).deriveKeyPair(1);
            Assert.assertNotNull(keyPair);
            Request<?, EmptyResponse> request = vitej.sendTransaction(
                    keyPair,
                    new TransactionParams()
                            .setBlockType(EBlockType.SEND_CALL.getValue())
                            .setToAddress(new Address("vite_0996e651f3885e6e6b83dfba8caa095ff7aa248e4a429db7bd"))
                            .setAmount(new BigInteger("100"))
                            .setTokenId(CommonConstants.VITE_TOKEN_ID)
                            .setData("hello".getBytes()),
                    true
            );
            Assert.assertTrue(request.getParams().size() == 1 && ((TransactionParams) request.getParams().get(0)).getHashRaw() != null);
            sendBlockHash = ((TransactionParams) request.getParams().get(0)).getHashRaw();
            EmptyResponse response = request.send();
            Assert.assertNull(response.getError());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Got exception");
        }
        try {
            List<String> mnemonic = Arrays.asList("network", "north", "tell", "potato", "predict", "almost", "wonder", "spirit", "wheel", "smile", "disease", "bonus", "round", "flock", "pole", "review", "music", "oven", "clarify", "exclude", "loyal", "episode", "image", "notable");
            KeyPair keyPair = new Wallet(mnemonic).deriveKeyPair(0);
            Assert.assertNotNull(keyPair);
            Request<?, EmptyResponse> request = vitej.sendTransaction(
                    keyPair,
                    new TransactionParams()
                            .setBlockType(EBlockType.RECEIVE.getValue())
                            .setSendBlockHash(sendBlockHash),
                    true
            );
            Assert.assertTrue(request.getParams().size() == 1 && ((TransactionParams) request.getParams().get(0)).getHashRaw() != null);
            EmptyResponse response = request.send();
            Assert.assertNull(response.getError());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Got exception");
        }
    }

    @Test
    public void testCreateContract() {
        Hash sendBlockHash = null;
        try {
            List<String> mnemonic = Arrays.asList("alarm", "canal", "scheme", "actor", "left", "length", "bracket", "slush", "tuna", "garage", "prepare", "scout", "school", "pizza", "invest", "rose", "fork", "scorpion", "make", "enact", "false", "kidney", "mixed", "vast");
            KeyPair keyPair = new Wallet(mnemonic).deriveKeyPair(1);
            Assert.assertNotNull(keyPair);
            byte[] bytecode = BytesUtils.hexStringToBytes("6080604052348015600f57600080fd5b50604051602080608183398101806040526020811015602d57600080fd5b810190808051906020019092919050505050603580604c6000396000f3fe6080604052600080fdfea165627a7a723058208602dc0b6a1bf2e56f2160299868dc8c3f435c9af6d384858722a21906c7c0740029");
            Abi contractAbi = Abi.fromJson("[{\"inputs\":[{\"name\":\"i\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"}]");
            byte[] encodedParameters = contractAbi.encodeConstructor(new BigInteger("123"));
            Request<?, EmptyResponse> request = vitej.sendTransaction(
                    keyPair,
                    new TransactionParams()
                            .setBlockType(EBlockType.SEND_CREATE.getValue())
                            .setData(ContractUtils.getCreateContractData(bytecode, encodedParameters)),
                    true
            );
            Assert.assertTrue(request.getParams().size() == 1 && ((TransactionParams) request.getParams().get(0)).getHashRaw() != null);
            sendBlockHash = ((TransactionParams) request.getParams().get(0)).getHashRaw();
            EmptyResponse response = request.send();
            Assert.assertNull(response.getError());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Got exception");
        }
        try {
            Boolean callSuccess = vitej.checkCallContractResult(sendBlockHash);
            Assert.assertTrue(callSuccess);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCallContractSuccess() {
        Hash sendBlockHash = null;
        try {
            List<String> mnemonic = Arrays.asList("alarm", "canal", "scheme", "actor", "left", "length", "bracket", "slush", "tuna", "garage", "prepare", "scout", "school", "pizza", "invest", "rose", "fork", "scorpion", "make", "enact", "false", "kidney", "mixed", "vast");
            KeyPair keyPair = new Wallet(mnemonic).deriveKeyPair(1);
            Assert.assertNotNull(keyPair);
            Request<?, EmptyResponse> request = vitej.sendTransaction(
                    keyPair,
                    new TransactionParams()
                            .setBlockType(EBlockType.SEND_CALL.getValue())
                            .setToAddress(BuiltinContracts.ADDRESS_QUOTA_CONTRACT)
                            .setAmount(new BigInteger("1000000000000000000000"))
                            .setTokenId(CommonConstants.VITE_TOKEN_ID)
                            .setData(BuiltinContracts.ABI_QUOTA_CONTRACT.encodeFunction("StakeForQuota", keyPair.getAddress())),
                    true
            );
            Assert.assertTrue(request.getParams().size() == 1 && ((TransactionParams) request.getParams().get(0)).getHashRaw() != null);
            sendBlockHash = ((TransactionParams) request.getParams().get(0)).getHashRaw();
            EmptyResponse response = request.send();
            Assert.assertNull(response.getError());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Got exception");
        }
        try {
            Boolean callSuccess = vitej.checkCallContractResult(sendBlockHash);
            Assert.assertTrue(callSuccess);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetPoWDifficulty() {
        try {
            PoWDifficultyResponse response = vitej.getPoWDifficulty(
                    new TransactionParams()
                            .setAddress(new Address("vite_ab24ef68b84e642c0ddca06beec81c9acb1977bbd7da27a87a"))
                            .setToAddress(new Address("vite_098dfae02679a4ca05a4c8bf5dd00a8757f0c622bfccce7d68"))
                            .setData("hello".getBytes())
            ).send();
            Assert.assertNull(response.getError());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Got exception");
        }
    }

    @Test
    public void testGetRequiredQuota() {
        try {
            RequiredQuotaResponse response = vitej.getRequiredQuota(
                    new TransactionParams()
                            .setAddress(new Address("vite_ab24ef68b84e642c0ddca06beec81c9acb1977bbd7da27a87a"))
                            .setToAddress(new Address("vite_098dfae02679a4ca05a4c8bf5dd00a8757f0c622bfccce7d68"))
                            .setData("hello".getBytes())
            ).send();
            Assert.assertNull(response.getError());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Got exception");
        }
    }

    @Test
    public void testNetSyncInfo() {
        try {
            NetSyncInfoResponse response = vitej.netSyncInfo().send();
            Assert.assertNull(response.getError());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Got exception");
        }
    }

    @Test
    public void testNetSyncDetail() {
        try {
            NetSyncDetailResponse response = vitej.netSyncDetail().send();
            Assert.assertNull(response.getError());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Got exception");
        }
    }

    @Test
    public void testNetNodeInfo() {
        try {
            NetNodeInfoResponse response = vitej.netNodeInfo().send();
            Assert.assertNull(response.getError());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Got exception");
        }
    }

    @Test
    public void testGetPoWNonce() {
        try {
            PoWNonceResponse response = vitej.getPoWNonce(
                    new BigInteger("65535"),
                    new Hash("d517e8d4dc9c676876b72ad0cbb4c45890804aa438edd1f171ffc66276202a95")
            ).send();
            Assert.assertNull(response.getError());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Got exception");
        }
    }

    @Test
    public void testGetAccountBlocksByAddress() {
        try {
            AccountBlocksResponse response = vitej.getAccountBlocksByAddress(
                    new Address("vite_ab24ef68b84e642c0ddca06beec81c9acb1977bbd7da27a87a"), 0, 10
            ).send();
            Assert.assertNull(response.getError());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Got exception");
        }
    }

    @Test
    public void testAsyncGetAccountBlocksByAddress() {
        try {
            CompletableFuture<AccountBlocksResponse> future = vitej.getAccountBlocksByAddress(
                    new Address("vite_ab24ef68b84e642c0ddca06beec81c9acb1977bbd7da27a87a"), 0, 10
            ).sendAsync();
            AccountBlocksResponse response = future.get();
            Assert.assertNull(response.getError());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Got exception");
        }
    }

    @Test
    public void testGetAccountBlockByHeight() {
        try {
            AccountBlockResponse response = vitej.getAccountBlockByHeight(
                    new Address("vite_ab24ef68b84e642c0ddca06beec81c9acb1977bbd7da27a87a"), 1L
            ).send();
            Assert.assertNull(response.getError());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Got exception");
        }
    }

    @Test
    public void testGetAccountBlockByHash() {
        try {
            AccountBlockResponse response = vitej.getAccountBlockByHash(
                    new Hash("d517e8d4dc9c676876b72ad0cbb4c45890804aa438edd1f171ffc66276202a95")
            ).send();
            Assert.assertNull(response.getError());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Got exception");
        }
    }

    @Test
    public void testGetLatestAccountBlock() {
        try {
            AccountBlockResponse response = vitej.getLatestAccountBlock(
                    new Address("vite_ab24ef68b84e642c0ddca06beec81c9acb1977bbd7da27a87a")
            ).send();
            Assert.assertNull(response.getError());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Got exception");
        }
    }

    @Test
    public void testGetAccountBlocks() {
        try {
            AccountBlocksResponse response = vitej.getAccountBlocks(
                    new Address("vite_ab24ef68b84e642c0ddca06beec81c9acb1977bbd7da27a87a"),
                    new Hash("d517e8d4dc9c676876b72ad0cbb4c45890804aa438edd1f171ffc66276202a95"),
                    CommonConstants.VITE_TOKEN_ID,
                    10
            ).send();
            Assert.assertNull(response.getError());
            response = vitej.getAccountBlocks(
                    new Address("vite_ab24ef68b84e642c0ddca06beec81c9acb1977bbd7da27a87a"),
                    new Hash("d517e8d4dc9c676876b72ad0cbb4c45890804aa438edd1f171ffc66276202a95"),
                    null,
                    10
            ).send();
            Assert.assertNull(response.getError());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Got exception");
        }
    }

    @Test
    public void testGetAccountInfoByAddress() {
        try {
            AccountInfoResponse response = vitej.getAccountInfoByAddress(
                    new Address("vite_ab24ef68b84e642c0ddca06beec81c9acb1977bbd7da27a87a")
            ).send();
            Assert.assertNull(response.getError());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Got exception");
        }
    }

    @Test
    public void testGetUnReceivedBlocksByAddress() {
        try {
            AccountBlocksResponse response = vitej.getUnreceivedBlocksByAddress(
                    new Address("vite_ab24ef68b84e642c0ddca06beec81c9acb1977bbd7da27a87a"), 0, 10
            ).send();
            Assert.assertNull(response.getError());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Got exception");
        }
    }

    @Test
    public void testGetUnReceivedTransactionSummaryByAddress() {
        try {
            UnreceivedTransactionSummaryResponse response = vitej.getUnreceivedTransactionSummaryByAddress(
                    new Address("vite_ab24ef68b84e642c0ddca06beec81c9acb1977bbd7da27a87a")
            ).send();
            Assert.assertNull(response.getError());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Got exception");
        }
    }

    @Test
    public void testGetLatestSnapshotHash() {
        try {
            LatestSnapshotHashResponse response = vitej.getLatestSnapshotHash().send();
            Assert.assertNull(response.getError());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Got exception");
        }
    }

    @Test
    public void testGetSnapshotChainHeight() {
        try {
            SnapshotChainHeightResponse response = vitej.getSnapshotChainHeight().send();
            Assert.assertNull(response.getError());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Got exception");
        }
    }

    @Test
    public void testGetSnapshotBlockByHash() {
        try {
            SnapshotBlockResponse response = vitej.getSnapshotBlockByHash(
                    new Hash("0dfbbf928927b2c222d4dc0d6764dbfc3bce57f2239a69d72eace7faabb5f134")
            ).send();
            Assert.assertNull(response.getError());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Got exception");
        }
    }

    @Test
    public void testGetSnapshotBlockByHeight() {
        try {
            SnapshotBlockResponse response = vitej.getSnapshotBlockByHeight(1L).send();
            Assert.assertNull(response.getError());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Got exception");
        }
    }

    @Test
    public void testGetVmlogs() {
        try {
            VmlogsResponse response = vitej.getVmlogs(
                    new Hash("88e42882ab0eb2693056d1a85ae40a7017be34e56b3ce6c8afc3580a33a9ca36")
            ).send();
            Assert.assertNull(response.getError());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Got exception");
        }
    }

    @Test
    public void testGetVmlogsByFilter() {
        try {
            VmlogInfosResponse response = vitej.getVmlogsByFilter(
                    new VmLogFilter(new Address("vite_000000000000000000000000000000000000000595292d996d"),
                            1L, 10L)
            ).send();
            Assert.assertNull(response.getError());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Got exception");
        }
    }

    @Test
    public void testCreateContractAddress() {
        try {
            CreateContractAddressResponse response = vitej.createContractAddress(
                    new Address("vite_ab24ef68b84e642c0ddca06beec81c9acb1977bbd7da27a87a"),
                    2L,
                    new Hash("d517e8d4dc9c676876b72ad0cbb4c45890804aa438edd1f171ffc66276202a95")
            ).send();
            Assert.assertNull(response.getError());
            Assert.assertEquals("vite_32f15c00af28d981033016214c2e19ffc058aaf3b36f4980ae", response.getAddress().toString());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Got exception");
        }
    }

    @Test
    public void testGetContractInfo() {
        try {
            ContractInfoResponse response = vitej.getContractInfo(
                    new Address("vite_da0e4189f8155035d5b373f8f1328e43d7d70980f4fb69ff18")
            ).send();
            Assert.assertNull(response.getError());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Got exception");
        }
    }

    @Test
    public void testCallOffChainMethod() {
        try {
            Abi abi = Abi.fromJson("[{\"inputs\":[],\"name\":\"getData\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"type\":\"offchain\"}]");
            String methodName = "getData";
            byte[] callOffChainData = abi.encodeOffchain(methodName);
            CallOffChainMethodResponse response = vitej.callOffChainMethod(
                    new Address("vite_da0e4189f8155035d5b373f8f1328e43d7d70980f4fb69ff18"),
                    BytesUtils.hexStringToBytes("6080604052600436106042576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff168063c1a34865146044576042565b005b604a6060565b6040518082815260200191505060405180910390f35b60006000600050549050606e565b9056fea165627a7a7230582098acc939ef119097e24d6b599d9dd18bb2061a9fab6ec77401def1c0a7e52ecd0029"),
                    callOffChainData
            ).send();
            Assert.assertNull(response.getError());
            List<?> resultList = abi.decodeOffchainOutput(methodName, response.getReturnData());
            Assert.assertEquals(resultList.size(), 1);
            Assert.assertEquals(resultList.get(0), BigInteger.ZERO);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Got exception");
        }
    }

    @Test
    public void testGetQuotaByAccount() {
        try {
            QuotaResponse response = vitej.getQuotaByAccount(
                    new Address("vite_ab24ef68b84e642c0ddca06beec81c9acb1977bbd7da27a87a")
            ).send();
            Assert.assertNull(response.getError());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Got exception");
        }
    }

    @Test
    public void testGetStakeList() {
        try {
            StakeListResponse response = vitej.getStakeList(
                    new Address("vite_ab24ef68b84e642c0ddca06beec81c9acb1977bbd7da27a87a"), 0, 10
            ).send();
            Assert.assertNull(response.getError());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Got exception");
        }
    }

    @Test
    public void testGetRequiredStakeAmount() {
        try {
            // calculate stake amount for sending a normal account block without comment per second
            StakeAmountResponse response = vitej.getRequiredStakeAmount(
                    21000L
            ).send();
            Assert.assertNull(response.getError());
            Assert.assertEquals(response.getStakeAmount(), new BigInteger("10000000000000000000000"));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Got exception");
        }
    }

    @Test
    public void testGetSBPList() {
        try {
            SBPListResponse response = vitej.getSBPList(
                    new Address("vite_360232b0378111b122685a15e612143dc9a89cfa7e803f4b5a")
            ).send();
            Assert.assertNull(response.getError());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Got exception");
        }
    }

    @Test
    public void testGetSBPRewardPendingWithdrawal() {
        try {
            SBPRewardResponse response = vitej.getSBPRewardPendingWithdrawal(
                    "s1"
            ).send();
            Assert.assertNull(response.getError());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Got exception");
        }
    }

    @Test
    public void testGetSBPRewardByCycle() {
        try {
            SBPRewardDetailResponse response = vitej.getSBPRewardByCycle(
                    176L
            ).send();
            Assert.assertNull(response.getError());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Got exception");
        }
    }

    @Test
    public void testGetSBP() {
        try {
            SBPResponse response = vitej.getSBP(
                    "s1"
            ).send();
            Assert.assertNull(response.getError());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Got exception");
        }
    }

    @Test
    public void testGetSBPVoteList() {
        try {
            SBPVoteListResponse response = vitej.getSBPVoteList().send();
            Assert.assertNull(response.getError());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Got exception");
        }
    }

    @Test
    public void testGetVotedSBP() {
        try {
            VotedSBPResponse response = vitej.getVotedSBP(
                    new Address("vite_ab24ef68b84e642c0ddca06beec81c9acb1977bbd7da27a87a")
            ).send();
            Assert.assertNull(response.getError());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Got exception");
        }
    }

    @Test
    public void testGetSBPVoteDetailsByCycle() {
        try {
            SBPVoteDetailsResponse response = vitej.getSBPVoteDetailsByCycle(
                    176L
            ).send();
            Assert.assertNull(response.getError());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Got exception");
        }
    }

    @Test
    public void testGetTokenInfoList() {
        try {
            TokenInfoListWithTotalResponse response = vitej.getTokenInfoList(
                    0, 10
            ).send();
            Assert.assertNull(response.getError());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Got exception");
        }
    }

    @Test
    public void testGetTokenInfoById() {
        try {
            TokenInfoResponse response = vitej.getTokenInfoById(
                    new TokenId("tti_5649544520544f4b454e6e40")
            ).send();
            Assert.assertNull(response.getError());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Got exception");
        }
    }

    @Test
    public void testGetTokenInfoListByOwner() {
        try {
            TokenInfoListResponse response = vitej.getTokenInfoListByOwner(
                    new Address("vite_ab24ef68b84e642c0ddca06beec81c9acb1977bbd7da27a87a")
            ).send();
            Assert.assertNull(response.getError());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Got exception");
        }
    }
}
