package org.vitej.core.utils;

import org.junit.Assert;
import org.junit.Test;
import org.vitej.core.protocol.methods.Address;
import org.vitej.core.protocol.methods.Hash;
import org.vitej.core.protocol.methods.request.TransactionParams;
import org.vitej.core.utils.abi.Abi;

import java.math.BigInteger;

public class ContractUtilsTest {
    @Test
    public void testGetNewContractAddress() {
        Address expected = new Address("vite_32f15c00af28d981033016214c2e19ffc058aaf3b36f4980ae");
        Address got = ContractUtils.getNewContractAddress(
                new TransactionParams()
                        .setAddress(new Address("vite_ab24ef68b84e642c0ddca06beec81c9acb1977bbd7da27a87a"))
                        .setHeight(2L)
                        .setPreviousHash(new Hash("d517e8d4dc9c676876b72ad0cbb4c45890804aa438edd1f171ffc66276202a95")));
        Assert.assertEquals(expected, got);
    }

    @Test
    public void testGetCreateContractData() {
        byte[] bytecode = BytesUtils.hexStringToBytes("6080604052348015600f57600080fd5b50604051602080608183398101806040526020811015602d57600080fd5b810190808051906020019092919050505050603580604c6000396000f3fe6080604052600080fdfea165627a7a723058208602dc0b6a1bf2e56f2160299868dc8c3f435c9af6d384858722a21906c7c0740029");
        Abi contractAbi = Abi.fromJson("[{\"inputs\":[{\"name\":\"i\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"}]");
        byte[] encodedParameters = contractAbi.encodeConstructor(new BigInteger("123"));
        byte[] expected = BytesUtils.hexStringToBytes("000000000000000000020101020f6080604052348015600f57600080fd5b50604051602080608183398101806040526020811015602d57600080fd5b810190808051906020019092919050505050603580604c6000396000f3fe6080604052600080fdfea165627a7a723058208602dc0b6a1bf2e56f2160299868dc8c3f435c9af6d384858722a21906c7c0740029000000000000000000000000000000000000000000000000000000000000007b");
        byte[] got = ContractUtils.getCreateContractData(bytecode, encodedParameters, 1, 2, 15);
        Assert.assertArrayEquals(expected, got);
    }
}
