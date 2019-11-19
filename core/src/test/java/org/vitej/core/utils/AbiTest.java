package org.vitej.core.utils;

import org.junit.Assert;
import org.junit.Test;
import org.vitej.core.protocol.methods.Address;
import org.vitej.core.protocol.methods.TokenId;
import org.vitej.core.utils.abi.Abi;

import java.math.BigInteger;
import java.util.List;

public class AbiTest {
    @Test
    public void testAbiConstructorEncodeAndDecode() {
        Abi abi = Abi.fromJson("[" +
                "{ \"type\" : \"constructor\", \"inputs\" : [ { \"name\" : \"owner\", \"type\" : \"address\" } ] }" +
                "]");
        byte[] encodedParams = BytesUtils.hexStringToBytes("0000000000000000000000ab24ef68b84e642c0ddca06beec81c9acb1977bb00");
        Address address = new Address("vite_ab24ef68b84e642c0ddca06beec81c9acb1977bbd7da27a87a");
        Assert.assertArrayEquals(
                encodedParams,
                abi.encodeConstructor(address));
        List<?> decodedParams = abi.decodeConstructor(encodedParams);
        Assert.assertTrue(decodedParams.size() == 1
                && address.equals(decodedParams.get(0)));
    }

    @Test
    public void testAbiFunctionEncodeAndDecode() {
        Abi abi = Abi.fromJson("[" +
                "{ \"type\" : \"function\", \"name\" : \"send\", \"constant\" : false, \"inputs\" : [ { \"name\" : \"amount\", \"type\" : \"uint256\" } ] }" +
                "]");
        byte[] encodedParams = BytesUtils.hexStringToBytes("d6f47d63000000000000000000000000000000000000000000000000000000000000007b");
        BigInteger amount = new BigInteger("123");
        Assert.assertArrayEquals(
                encodedParams,
                abi.encodeFunction("send", amount));
        List<?> decodedParams = abi.decodeFunction(encodedParams);
        Assert.assertTrue(decodedParams.size() == 1
                && amount.equals(decodedParams.get(0)));
    }

    @Test
    public void testAbiEventEncodeAndDecode() {
        Abi abi = Abi.fromJson("[" +
                "{ \"type\" : \"event\", \"name\" : \"Transfer\", \"anonymous\" : false, \"inputs\" : [ { \"indexed\" : true, \"name\" : \"from\", \"type\" : \"address\" }, { \"indexed\" : true, \"name\" : \"to\", \"type\" : \"address\" }, { \"name\" : \"value\", \"type\" : \"uint256\" } ] }" +
                "]");
        BigInteger amount = new BigInteger("123");
        Address fromAddress = new Address("vite_ab24ef68b84e642c0ddca06beec81c9acb1977bbd7da27a87a");
        Address toAddress = new Address("vite_32f15c00af28d981033016214c2e19ffc058aaf3b36f4980ae");
        List<?> decodedParams = abi.decodeEvent(BytesUtils.hexStringToBytes("000000000000000000000000000000000000000000000000000000000000007b"), new byte[][]{
                BytesUtils.hexStringToBytes("e9a7da5bfc2bcbf4266adfba50ac5d6fa9ba4d52df50d9359a3974c36c131ce1"),
                BytesUtils.hexStringToBytes("0000000000000000000000ab24ef68b84e642c0ddca06beec81c9acb1977bb00"),
                BytesUtils.hexStringToBytes("000000000000000000000032f15c00af28d981033016214c2e19ffc058aaf301"),

        });
        Assert.assertTrue(decodedParams.size() == 3
                && fromAddress.equals(decodedParams.get(0))
                && toAddress.equals(decodedParams.get(1))
                && amount.equals(decodedParams.get(2)));
    }

    @Test
    public void testAbiOffChainEncodeAndDecode() {
        Abi abi = Abi.fromJson("[" +
                "{ \"type\" : \"offchain\", \"name\" : \"getData\", \"inputs\" : [ { \"name\" : \"id\", \"type\" : \"tokenId\" } ],\"outputs\": [ { \"name\" : \"amount\", \"type\" : \"uint256\" } ] }" +
                "]");
        byte[] encodedParams = BytesUtils.hexStringToBytes("a0a3fe85000000000000000000000000000000000000000000005649544520544f4b454e");
        TokenId tokenId = new TokenId("tti_5649544520544f4b454e6e40");
        Assert.assertArrayEquals(
                encodedParams,
                abi.encodeOffchain("getData", tokenId));
        byte[] encodedOutputParams = BytesUtils.hexStringToBytes("000000000000000000000000000000000000000000000000000000000000007b");
        List<?> decodedOutputParams = abi.decodeOffchainOutput("getData", encodedOutputParams);
        BigInteger amount = new BigInteger("123");
        Assert.assertTrue(decodedOutputParams.size() == 1
                && amount.equals(decodedOutputParams.get(0)));
    }

    @Test
    public void testGetEventId() {
        Abi abi = Abi.fromJson("[" +
                "{ \"type\" : \"event\", \"name\" : \"Transfer\", \"anonymous\" : false, \"inputs\" : [ { \"indexed\" : true, \"name\" : \"from\", \"type\" : \"address\" }, { \"indexed\" : true, \"name\" : \"to\", \"type\" : \"address\" }, { \"name\" : \"value\", \"type\" : \"uint256\" } ] }" +
                "]");
        byte[] id = abi.findEventByName("Transfer").encodeSignature();
        Assert.assertArrayEquals(
                BytesUtils.hexStringToBytes("e9a7da5bfc2bcbf4266adfba50ac5d6fa9ba4d52df50d9359a3974c36c131ce1"),
                id);
    }

    @Test
    public void testGetMethodById() {
        Abi abi = Abi.fromJson("[" +
                "{ \"type\" : \"function\", \"name\" : \"send\", \"constant\" : false, \"inputs\" : [ { \"name\" : \"amount\", \"type\" : \"uint256\" } ] }" +
                "]");
        byte[] encodedParams = BytesUtils.hexStringToBytes("d6f47d63000000000000000000000000000000000000000000000000000000000000007b");
        Abi.Function f = abi.findFunctionByData(encodedParams);
        Assert.assertNotNull(f);
        Assert.assertEquals("send", f.name);
    }
}
