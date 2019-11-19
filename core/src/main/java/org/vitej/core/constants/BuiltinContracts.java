package org.vitej.core.constants;

import org.vitej.core.protocol.methods.Address;
import org.vitej.core.utils.abi.Abi;

public class BuiltinContracts {
    public static final Address ADDRESS_QUOTA_CONTRACT = new Address("vite_0000000000000000000000000000000000000003f6af7459b9");
    public static final Address ADDRESS_GOVERNANCE_CONTRACT = new Address("vite_0000000000000000000000000000000000000004d28108e76b");
    public static final Address ADDRESS_ASSET_CONTRACT = new Address("vite_000000000000000000000000000000000000000595292d996d");

    private static final String ABI_JSON_QUOTA_CONTRACT = "[" +
            "{\"type\":\"function\",\"name\":\"Stake\", \"inputs\":[{\"name\":\"beneficiary\",\"type\":\"address\"}]}," +
            "{\"type\":\"function\",\"name\":\"StakeForQuota\", \"inputs\":[{\"name\":\"beneficiary\",\"type\":\"address\"}]}," +
            "{\"type\":\"function\",\"name\":\"CancelStake\",\"inputs\":[{\"name\":\"beneficiary\",\"type\":\"address\"},{\"name\":\"amount\",\"type\":\"uint256\"}]}," +
            "{\"type\":\"function\",\"name\":\"CancelQuotaStaking\",\"inputs\":[{\"name\":\"id\",\"type\":\"bytes32\"}]}," +
            "{\"type\":\"function\",\"name\":\"StakeForQuotaWithCallback\", \"inputs\":[{\"name\":\"beneficiary\",\"type\":\"address\"},{\"name\":\"stakeHeight\",\"type\":\"uint64\"}]}," +
            "{\"type\":\"function\",\"name\":\"CancelQuotaStakingWithCallback\",\"inputs\":[{\"name\":\"id\",\"type\":\"bytes32\"}]}," +
            "{\"type\":\"function\",\"name\":\"StakeForQuotaWithCallbackCallback\", \"inputs\":[{\"name\":\"id\",\"type\":\"bytes32\"},{\"name\":\"success\",\"type\":\"bool\"}]}," +
            "{\"type\":\"function\",\"name\":\"CancelQuotaStakingWithCallbackCallback\",\"inputs\":[{\"name\":\"id\",\"type\":\"bytes32\"},{\"name\":\"success\",\"type\":\"bool\"}]}" +
            "]";
    private static final String ABI_JSON_GOVERNANCE_CONTRACT = "[" +
            "{\"type\":\"function\",\"name\":\"RegisterSBP\", \"inputs\":[{\"name\":\"sbpName\",\"type\":\"string\"},{\"name\":\"blockProducingAddress\",\"type\":\"address\"},{\"name\":\"rewardWithdrawAddress\",\"type\":\"address\"}]}," +
            "{\"type\":\"function\",\"name\":\"UpdateSBPBlockProducingAddress\", \"inputs\":[{\"name\":\"sbpName\",\"type\":\"string\"},{\"name\":\"blockProducingAddress\",\"type\":\"address\"}]}," +
            "{\"type\":\"function\",\"name\":\"UpdateSBPRewardWithdrawAddress\", \"inputs\":[{\"name\":\"sbpName\",\"type\":\"string\"},{\"name\":\"rewardWithdrawAddress\",\"type\":\"address\"}]}," +
            "{\"type\":\"function\",\"name\":\"RevokeSBP\",\"inputs\":[{\"name\":\"sbpName\",\"type\":\"string\"}]}," +
            "{\"type\":\"function\",\"name\":\"WithdrawSBPReward\",\"inputs\":[{\"name\":\"sbpName\",\"type\":\"string\"},{\"name\":\"receiveAddress\",\"type\":\"address\"}]}," +
            "{\"type\":\"function\",\"name\":\"VoteForSBP\", \"inputs\":[{\"name\":\"sbpName\",\"type\":\"string\"}]}," +
            "{\"type\":\"function\",\"name\":\"CancelSBPVoting\",\"inputs\":[]}" +
            "]";
    private static final String ABI_JSON_ASSET_CONTRACT = "[" +
            "{\"type\":\"function\",\"name\":\"IssueToken\",\"inputs\":[{\"name\":\"isReIssuable\",\"type\":\"bool\"},{\"name\":\"tokenName\",\"type\":\"string\"},{\"name\":\"tokenSymbol\",\"type\":\"string\"},{\"name\":\"totalSupply\",\"type\":\"uint256\"},{\"name\":\"decimals\",\"type\":\"uint8\"},{\"name\":\"maxSupply\",\"type\":\"uint256\"},{\"name\":\"isOwnerBurnOnly\",\"type\":\"bool\"}]}," +
            "{\"type\":\"function\",\"name\":\"ReIssue\",\"inputs\":[{\"name\":\"tokenId\",\"type\":\"tokenId\"},{\"name\":\"amount\",\"type\":\"uint256\"},{\"name\":\"receiveAddress\",\"type\":\"address\"}]}," +
            "{\"type\":\"function\",\"name\":\"Burn\",\"inputs\":[]}," +
            "{\"type\":\"function\",\"name\":\"TransferOwnership\",\"inputs\":[{\"name\":\"tokenId\",\"type\":\"tokenId\"},{\"name\":\"newOwner\",\"type\":\"address\"}]}," +
            "{\"type\":\"function\",\"name\":\"DisableReIssue\",\"inputs\":[{\"name\":\"tokenId\",\"type\":\"tokenId\"}]}," +
            "{\"type\":\"function\",\"name\":\"GetTokenInformation\",\"inputs\":[{\"name\":\"tokenId\",\"type\":\"tokenId\"}]}," +
            "{\"type\":\"function\",\"name\":\"GetTokenInformationCallback\",\"inputs\":[{\"name\":\"id\",\"type\":\"bytes32\"},{\"name\":\"tokenId\",\"type\":\"tokenId\"},{\"name\":\"exist\",\"type\":\"bool\"},{\"name\":\"isReIssuable\",\"type\":\"bool\"},{\"name\":\"tokenName\",\"type\":\"string\"},{\"name\":\"tokenSymbol\",\"type\":\"string\"},{\"name\":\"totalSupply\",\"type\":\"uint256\"},{\"name\":\"decimals\",\"type\":\"uint8\"},{\"name\":\"maxSupply\",\"type\":\"uint256\"},{\"name\":\"isOwnerBurnOnly\",\"type\":\"bool\"},{\"name\":\"index\",\"type\":\"uint16\"},{\"name\":\"ownerAddress\",\"type\":\"address\"}]}," +
            "{\"type\":\"event\",\"name\":\"issueToken\",\"inputs\":[{\"name\":\"tokenId\",\"type\":\"tokenId\",\"indexed\":true}]}," +
            "{\"type\":\"event\",\"name\":\"reIssue\",\"inputs\":[{\"name\":\"tokenId\",\"type\":\"tokenId\",\"indexed\":true}]}," +
            "{\"type\":\"event\",\"name\":\"burn\",\"inputs\":[{\"name\":\"tokenId\",\"type\":\"tokenId\",\"indexed\":true},{\"name\":\"address\",\"type\":\"address\"},{\"name\":\"amount\",\"type\":\"uint256\"}]}," +
            "{\"type\":\"event\",\"name\":\"transferOwnership\",\"inputs\":[{\"name\":\"tokenId\",\"type\":\"tokenId\",\"indexed\":true},{\"name\":\"owner\",\"type\":\"address\"}]}," +
            "{\"type\":\"event\",\"name\":\"disableReIssue\",\"inputs\":[{\"name\":\"tokenId\",\"type\":\"tokenId\",\"indexed\":true}]}" +
            "]";

    public static final Abi ABI_QUOTA_CONTRACT = Abi.fromJson(ABI_JSON_QUOTA_CONTRACT);
    public static final Abi ABI_GOVERNANCE_CONTRACT = Abi.fromJson(ABI_JSON_GOVERNANCE_CONTRACT);
    public static final Abi ABI_ASSET_CONTRACT = Abi.fromJson(ABI_JSON_ASSET_CONTRACT);
}
