package org.vitej.core.utils;

import org.junit.Assert;
import org.junit.Test;
import org.vitej.core.protocol.methods.Address;
import org.vitej.core.protocol.methods.TokenId;
import org.vitej.core.utils.abi.Abi;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

public class AbiTest {
    @Test
    public void testAbiConstructorEncodeAndDecode() {
        Abi abi = Abi.fromJson(
                "[{ \"type\" : \"constructor\", \"inputs\" : [ { \"name\" : \"owner\", \"type\" : \"address\" } ] }]");
        byte[] encodedParams = BytesUtils.hexStringToBytes(
                "0000000000000000000000ab24ef68b84e642c0ddca06beec81c9acb1977bb00");
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
        Abi abi = Abi.fromJson(
                "[{ \"type\" : \"function\", \"name\" : \"send\", \"constant\" : false, \"inputs\" : [ { \"name\" : \"amount\", \"type\" : \"uint256\" } ] }]");
        byte[] encodedParams = BytesUtils.hexStringToBytes(
                "d6f47d6300000000000000000000000000000000000000000000000006f05b59d3b20000");
        BigInteger amount = new BigInteger("500000000000000000");
        Assert.assertArrayEquals(
                encodedParams,
                abi.encodeFunction("send", amount));
        List<?> decodedParams = abi.decodeFunction(encodedParams);
        Assert.assertTrue(decodedParams.size() == 1
                && amount.equals(decodedParams.get(0)));
    }

    @Test
    public void testAbiEventEncodeAndDecode() {
        Abi abi = Abi.fromJson(
                "[{ \"type\" : \"event\", \"name\" : \"Transfer\", \"anonymous\" : false, \"inputs\" : [ { \"indexed\" : true, \"name\" : \"from\", \"type\" : \"address\" }, { \"indexed\" : true, \"name\" : \"to\", \"type\" : \"address\" }, { \"name\" : \"value\", \"type\" : \"uint256\" } ] }]");
        BigInteger amount = new BigInteger("123");
        Address fromAddress =
                new Address("vite_ab24ef68b84e642c0ddca06beec81c9acb1977bbd7da27a87a");
        Address toAddress = new Address("vite_32f15c00af28d981033016214c2e19ffc058aaf3b36f4980ae");
        List<?> decodedParams = abi.decodeEvent(
                BytesUtils.hexStringToBytes(
                        "000000000000000000000000000000000000000000000000000000000000007b"),
                new byte[][] {
                        BytesUtils.hexStringToBytes(
                                "e9a7da5bfc2bcbf4266adfba50ac5d6fa9ba4d52df50d9359a3974c36c131ce1"),
                        BytesUtils.hexStringToBytes(
                                "0000000000000000000000ab24ef68b84e642c0ddca06beec81c9acb1977bb00"),
                        BytesUtils.hexStringToBytes(
                                "000000000000000000000032f15c00af28d981033016214c2e19ffc058aaf301"),

                });
        Assert.assertTrue(decodedParams.size() == 3
                && fromAddress.equals(decodedParams.get(0))
                && toAddress.equals(decodedParams.get(1))
                && amount.equals(decodedParams.get(2)));
    }

    @Test
    public void testAbiOffChainEncodeAndDecode() {
        Abi abi = Abi.fromJson(
                "[{ \"type\" : \"offchain\", \"name\" : \"getData\", \"inputs\" : [ { \"name\" : \"id\", \"type\" : \"tokenId\" } ],\"outputs\": [ { \"name\" : \"amount\", \"type\" : \"uint256\" } ] }]");
        byte[] encodedParams = BytesUtils.hexStringToBytes(
                "a0a3fe85000000000000000000000000000000000000000000005649544520544f4b454e");
        TokenId tokenId = new TokenId("tti_5649544520544f4b454e6e40");
        Assert.assertArrayEquals(
                encodedParams,
                abi.encodeOffchain("getData", tokenId));
        byte[] encodedOutputParams = BytesUtils.hexStringToBytes(
                "000000000000000000000000000000000000000000000000000000000000007b");
        List<?> decodedOutputParams = abi.decodeOffchainOutput("getData", encodedOutputParams);
        BigInteger amount = new BigInteger("123");
        Assert.assertTrue(decodedOutputParams.size() == 1
                && amount.equals(decodedOutputParams.get(0)));
    }

    @Test
    public void testAbi_EmptyArray_EncodeAndDecode() {
        Abi abi = Abi.fromJson("[{\"inputs\":[{\"internalType\":\"string[]\",\"name\":\"input\",\"type\":\"string[]\"}],\"name\":\"strArr0\",\"outputs\":[{\"internalType\":\"string[]\",\"name\":\"\",\"type\":\"string[]\"}],\"stateMutability\":\"view\",\"type\":\"function\"}]");
        String[] name = {};
        byte[] encodedOutputParams = BytesUtils.hexStringToBytes(
                "0000000000000000000000000000000000000000000000000000000000000020" +
                        "0000000000000000000000000000000000000000000000000000000000000000");
        Abi.Function f = abi.findFunctionByName("strArr0");
        byte[] encodedArgument = f.encodeArguments(Arrays.asList(name));
        Assert.assertArrayEquals(
                encodedOutputParams,
                encodedArgument);
        List<?> decodedOutputParams = abi.decodeFunctionOutput("strArr0", encodedOutputParams);
        Assert.assertTrue(decodedOutputParams.size() == 1
                && Arrays.equals(name, (Object[]) decodedOutputParams.get(0)));
    }

    @Test
    public void testAbi_EmptyStringArray_EncodeAndDecode() {
        Abi abi = Abi.fromJson("[{\"inputs\":[{\"internalType\":\"string[]\",\"name\":\"input\",\"type\":\"string[]\"}],\"name\":\"strArr1\",\"outputs\":[{\"internalType\":\"string[]\",\"name\":\"\",\"type\":\"string[]\"}],\"stateMutability\":\"view\",\"type\":\"function\"}]");
        String[] name = { "" };
        byte[] encodedOutputParams = BytesUtils.hexStringToBytes(
                "0000000000000000000000000000000000000000000000000000000000000020" +
                        "0000000000000000000000000000000000000000000000000000000000000001" +
                        "0000000000000000000000000000000000000000000000000000000000000020" +
                        "0000000000000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000000");
        Abi.Function f = abi.findFunctionByName("strArr1");
        byte[] encodedArgument = f.encodeArguments(Arrays.asList(name));
        Assert.assertArrayEquals(
                encodedOutputParams,
                encodedArgument);
        List<?> decodedOutputParams = abi.decodeFunctionOutput("strArr1", encodedOutputParams);
        Assert.assertTrue(decodedOutputParams.size() == 1
                && Arrays.equals(name, (Object[]) decodedOutputParams.get(0)));
    }

    @Test
    public void testAbi_StaticStringArray_SingleElement_EncodeAndDecode() {
        Abi abi = Abi.fromJson("[{\"inputs\":[{\"internalType\":\"string[1]\",\"name\":\"input\",\"type\":\"string[1]\"}],\"name\":\"strArr1Static\",\"outputs\":[{\"internalType\":\"string[1]\",\"name\":\"\",\"type\":\"string[1]\"}],\"stateMutability\":\"view\",\"type\":\"function\"}]");
        String[] name = { "user" };
        byte[] encodedOutputParams = BytesUtils.hexStringToBytes(
                "0000000000000000000000000000000000000000000000000000000000000020" +
                        "0000000000000000000000000000000000000000000000000000000000000020" +
                        "0000000000000000000000000000000000000000000000000000000000000004" +
                        "7573657200000000000000000000000000000000000000000000000000000000");
        Abi.Function f = abi.findFunctionByName("strArr1Static");
        byte[] encodedArgument = f.encodeArguments(Arrays.asList(name));
        Assert.assertArrayEquals(
                encodedOutputParams,
                encodedArgument);
        List<?> decodedOutputParams = abi.decodeFunctionOutput("strArr1Static", encodedOutputParams);
        Assert.assertTrue(decodedOutputParams.size() == 1
                && Arrays.equals(name, (Object[]) decodedOutputParams.get(0)));
    }

    @Test
    public void testAbi_StaticStringArray_EncodeAndDecode() {
        Abi abi = Abi.fromJson("[{\"inputs\":[{\"internalType\":\"string[5]\",\"name\":\"input\",\"type\":\"string[5]\"}],\"name\":\"strArr5Static\",\"outputs\":[{\"internalType\":\"string[5]\",\"name\":\"\",\"type\":\"string[5]\"}],\"stateMutability\":\"view\",\"type\":\"function\"}]");
        String[] name = { "alice", "alice", "alice", "alice", "alice" };
        byte[] encodedOutputParams = BytesUtils.hexStringToBytes(
                "0000000000000000000000000000000000000000000000000000000000000020" +
                        "00000000000000000000000000000000000000000000000000000000000000a0" +
                        "00000000000000000000000000000000000000000000000000000000000000e0" +
                        "0000000000000000000000000000000000000000000000000000000000000120" +
                        "0000000000000000000000000000000000000000000000000000000000000160" +
                        "00000000000000000000000000000000000000000000000000000000000001a0" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000");
        Abi.Function f = abi.findFunctionByName("strArr5Static");
        byte[] encodedArgument = f.encodeArguments(Arrays.asList(name));
        Assert.assertArrayEquals(
                encodedOutputParams,
                encodedArgument);
        List<?> decodedOutputParams = abi.decodeFunctionOutput("strArr5Static", encodedOutputParams);
        Assert.assertTrue(decodedOutputParams.size() == 1
                && Arrays.equals(name, (Object[]) decodedOutputParams.get(0)));
    }

    @Test
    public void testAbi_StringArray_SingleElement_EncodeAndDecode() {
        Abi abi = Abi.fromJson("[{\"inputs\":[{\"internalType\":\"string[]\",\"name\":\"input\",\"type\":\"string[]\"}],\"name\":\"strArr1\",\"outputs\":[{\"internalType\":\"string[]\",\"name\":\"\",\"type\":\"string[]\"}],\"stateMutability\":\"view\",\"type\":\"function\"}]");
        String[] name = { "user" };
        byte[] encodedOutputParams = BytesUtils.hexStringToBytes(
                "0000000000000000000000000000000000000000000000000000000000000020" +
                        "0000000000000000000000000000000000000000000000000000000000000001" +
                        "0000000000000000000000000000000000000000000000000000000000000020" +
                        "0000000000000000000000000000000000000000000000000000000000000004" +
                        "7573657200000000000000000000000000000000000000000000000000000000");
        Abi.Function f = abi.findFunctionByName("strArr1");
        byte[] encodedArgument = f.encodeArguments(Arrays.asList(name));
        Assert.assertArrayEquals(
                encodedOutputParams,
                encodedArgument);
        List<?> decodedOutputParams = abi.decodeFunctionOutput("strArr1", encodedOutputParams);
        Assert.assertTrue(decodedOutputParams.size() == 1
                && Arrays.equals(name, (Object[]) decodedOutputParams.get(0)));
    }

    @Test
    public void testAbi_StringArray_EncodeAndDecode() {
        Abi abi = Abi.fromJson("[{\"inputs\":[{\"internalType\":\"string[]\",\"name\":\"input\",\"type\":\"string[]\"}],\"name\":\"strArr5\",\"outputs\":[{\"internalType\":\"string[]\",\"name\":\"\",\"type\":\"string[]\"}],\"stateMutability\":\"view\",\"type\":\"function\"}]");
        String[] name = { "alice", "alice", "alice", "alice", "alice" };
        byte[] encodedOutputParams = BytesUtils.hexStringToBytes(
                "0000000000000000000000000000000000000000000000000000000000000020" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "00000000000000000000000000000000000000000000000000000000000000a0" +
                        "00000000000000000000000000000000000000000000000000000000000000e0" +
                        "0000000000000000000000000000000000000000000000000000000000000120" +
                        "0000000000000000000000000000000000000000000000000000000000000160" +
                        "00000000000000000000000000000000000000000000000000000000000001a0" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000");
        Abi.Function f = abi.findFunctionByName("strArr5");
        byte[] encodedArgument = f.encodeArguments(Arrays.asList(name));
        Assert.assertArrayEquals(
                encodedOutputParams,
                encodedArgument);
        List<?> decodedOutputParams = abi.decodeFunctionOutput("strArr5", encodedOutputParams);
        Assert.assertTrue(decodedOutputParams.size() == 1);
        Assert.assertArrayEquals(name, (Object[]) decodedOutputParams.get(0));
    }

    @Test
    public void testAbi_TwoStringArray_EncodeAndDecode() {
        Abi abi = Abi.fromJson("[{\"inputs\":[{\"internalType\":\"string[]\",\"name\":\"input1\",\"type\":\"string[]\"},{\"internalType\":\"string[]\",\"name\":\"input2\",\"type\":\"string[]\"}],\"name\":\"strArr5I2R2\",\"outputs\":[{\"internalType\":\"string[]\",\"name\":\"\",\"type\":\"string[]\"},{\"internalType\":\"string[]\",\"name\":\"\",\"type\":\"string[]\"}],\"stateMutability\":\"view\",\"type\":\"function\"}]");
        String[] name = { "alice", "alice", "alice", "alice", "alice" };
        byte[] encodedOutputParams = BytesUtils.hexStringToBytes(
                "0000000000000000000000000000000000000000000000000000000000000040" +
                        "0000000000000000000000000000000000000000000000000000000000000240" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "00000000000000000000000000000000000000000000000000000000000000a0" +
                        "00000000000000000000000000000000000000000000000000000000000000e0" +
                        "0000000000000000000000000000000000000000000000000000000000000120" +
                        "0000000000000000000000000000000000000000000000000000000000000160" +
                        "00000000000000000000000000000000000000000000000000000000000001a0" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "00000000000000000000000000000000000000000000000000000000000000a0" +
                        "00000000000000000000000000000000000000000000000000000000000000e0" +
                        "0000000000000000000000000000000000000000000000000000000000000120" +
                        "0000000000000000000000000000000000000000000000000000000000000160" +
                        "00000000000000000000000000000000000000000000000000000000000001a0" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000");
        Abi.Function f = abi.findFunctionByName("strArr5I2R2");
        byte[] encodedArgument = f.encodeArguments(Arrays.asList(name), Arrays.asList(name));
        Assert.assertArrayEquals(
                encodedOutputParams,
                encodedArgument);
        List<?> decodedOutputParams = abi.decodeFunctionOutput("strArr5I2R2", encodedOutputParams);
        Assert.assertTrue(decodedOutputParams.size() == 2);
        Assert.assertArrayEquals(name, (Object[]) decodedOutputParams.get(0));
        Assert.assertArrayEquals(name, (Object[]) decodedOutputParams.get(1));
    }

    @Test
    public void testAbi_TwoStaticStringArray_EncodeAndDecode() {
        Abi abi = Abi.fromJson("[{\"inputs\":[{\"internalType\":\"string[5]\",\"name\":\"input1\",\"type\":\"string[5]\"},{\"internalType\":\"string[5]\",\"name\":\"input2\",\"type\":\"string[5]\"}],\"name\":\"strArr5I2R2Static\",\"outputs\":[{\"internalType\":\"string[5]\",\"name\":\"\",\"type\":\"string[5]\"},{\"internalType\":\"string[5]\",\"name\":\"\",\"type\":\"string[5]\"}],\"stateMutability\":\"view\",\"type\":\"function\"}]");
        String[] name = { "alice", "alice", "alice", "alice", "alice" };
        byte[] encodedOutputParams = BytesUtils.hexStringToBytes(
                "0000000000000000000000000000000000000000000000000000000000000040" +
                        "0000000000000000000000000000000000000000000000000000000000000220" +
                        "00000000000000000000000000000000000000000000000000000000000000a0" +
                        "00000000000000000000000000000000000000000000000000000000000000e0" +
                        "0000000000000000000000000000000000000000000000000000000000000120" +
                        "0000000000000000000000000000000000000000000000000000000000000160" +
                        "00000000000000000000000000000000000000000000000000000000000001a0" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "00000000000000000000000000000000000000000000000000000000000000a0" +
                        "00000000000000000000000000000000000000000000000000000000000000e0" +
                        "0000000000000000000000000000000000000000000000000000000000000120" +
                        "0000000000000000000000000000000000000000000000000000000000000160" +
                        "00000000000000000000000000000000000000000000000000000000000001a0" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000");
        Abi.Function f = abi.findFunctionByName("strArr5I2R2Static");
        byte[] encodedArgument = f.encodeArguments(Arrays.asList(name), Arrays.asList(name));
//        System.out.println(BytesUtils.bytesToHexString(encodedArgument));
        Assert.assertArrayEquals(
                encodedOutputParams,
                encodedArgument);
        List<?> decodedOutputParams = abi.decodeFunctionOutput("strArr5I2R2Static", encodedOutputParams);
        Assert.assertTrue(decodedOutputParams.size() == 2);
        Assert.assertArrayEquals(name, (Object[]) decodedOutputParams.get(0));
        Assert.assertArrayEquals(name, (Object[]) decodedOutputParams.get(1));
    }

    @Test
    public void testAbi_TwoStringArray_OneStatic_EncodeAndDecode() {
        Abi abi = Abi.fromJson("[{\"inputs\":[{\"internalType\":\"string[]\",\"name\":\"input1\",\"type\":\"string[]\"},{\"internalType\":\"string[5]\",\"name\":\"input2\",\"type\":\"string[5]\"}],\"name\":\"strArr5I2R2DynamicAndStatic\",\"outputs\":[{\"internalType\":\"string[]\",\"name\":\"\",\"type\":\"string[]\"},{\"internalType\":\"string[5]\",\"name\":\"\",\"type\":\"string[5]\"}],\"stateMutability\":\"view\",\"type\":\"function\"}]");
        String[] name = { "alice", "alice", "alice", "alice", "alice" };
        byte[] encodedOutputParams = BytesUtils.hexStringToBytes(
                "0000000000000000000000000000000000000000000000000000000000000040" +
                        "0000000000000000000000000000000000000000000000000000000000000240" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "00000000000000000000000000000000000000000000000000000000000000a0" +
                        "00000000000000000000000000000000000000000000000000000000000000e0" +
                        "0000000000000000000000000000000000000000000000000000000000000120" +
                        "0000000000000000000000000000000000000000000000000000000000000160" +
                        "00000000000000000000000000000000000000000000000000000000000001a0" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "00000000000000000000000000000000000000000000000000000000000000a0" +
                        "00000000000000000000000000000000000000000000000000000000000000e0" +
                        "0000000000000000000000000000000000000000000000000000000000000120" +
                        "0000000000000000000000000000000000000000000000000000000000000160" +
                        "00000000000000000000000000000000000000000000000000000000000001a0" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000");
        Abi.Function f = abi.findFunctionByName("strArr5I2R2DynamicAndStatic");
        byte[] encodedArgument = f.encodeArguments(Arrays.asList(name), Arrays.asList(name));
//        System.out.println(BytesUtils.bytesToHexString(encodedArgument));
        Assert.assertArrayEquals(
                encodedOutputParams,
                encodedArgument);
        List<?> decodedOutputParams = abi.decodeFunctionOutput("strArr5I2R2DynamicAndStatic", encodedOutputParams);
        Assert.assertTrue(decodedOutputParams.size() == 2);
        Assert.assertArrayEquals(name, (Object[]) decodedOutputParams.get(0));
        Assert.assertArrayEquals(name, (Object[]) decodedOutputParams.get(1));
    }

    @Test
    public void testAbi_StaticIntArray_EncodeAndDecode() {
        Abi abi = Abi.fromJson("[{\"inputs\":[{\"internalType\":\"uint256[5]\",\"name\":\"input\",\"type\":\"uint256[5]\"}],\"name\":\"intArr5Static\",\"outputs\":[{\"internalType\":\"uint256[5]\",\"name\":\"\",\"type\":\"uint256[5]\"}],\"stateMutability\":\"view\",\"type\":\"function\"}]");
        BigInteger[] name = { BigInteger.valueOf(1), BigInteger.valueOf(2), BigInteger.valueOf(3), BigInteger.valueOf(4), BigInteger.valueOf(5)};
        byte[] encodedOutputParams = BytesUtils.hexStringToBytes(
                        "0000000000000000000000000000000000000000000000000000000000000001" +
                        "0000000000000000000000000000000000000000000000000000000000000002" +
                        "0000000000000000000000000000000000000000000000000000000000000003" +
                        "0000000000000000000000000000000000000000000000000000000000000004" +
                        "0000000000000000000000000000000000000000000000000000000000000005");
        Abi.Function f = abi.findFunctionByName("intArr5Static");
        byte[] encodedArgument = f.encodeArguments(Arrays.asList(name));
        Assert.assertArrayEquals(
                encodedOutputParams,
                encodedArgument);
        List<?> decodedOutputParams = abi.decodeFunctionOutput("intArr5Static", encodedOutputParams);
        Assert.assertTrue(decodedOutputParams.size() == 1);
        Assert.assertArrayEquals(name, (Object[]) decodedOutputParams.get(0));
    }

    @Test
    public void testAbi_IntArray_EncodeAndDecode() {
        Abi abi = Abi.fromJson("[{\"inputs\":[{\"internalType\":\"uint256[]\",\"name\":\"input\",\"type\":\"uint256[]\"}],\"name\":\"intArr5\",\"outputs\":[{\"internalType\":\"uint256[]\",\"name\":\"\",\"type\":\"uint256[]\"}],\"stateMutability\":\"view\",\"type\":\"function\"}]");
        BigInteger[] name = { BigInteger.valueOf(1), BigInteger.valueOf(2), BigInteger.valueOf(3), BigInteger.valueOf(4), BigInteger.valueOf(5)};
        byte[] encodedOutputParams = BytesUtils.hexStringToBytes(
                "0000000000000000000000000000000000000000000000000000000000000020" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "0000000000000000000000000000000000000000000000000000000000000001" +
                        "0000000000000000000000000000000000000000000000000000000000000002" +
                        "0000000000000000000000000000000000000000000000000000000000000003" +
                        "0000000000000000000000000000000000000000000000000000000000000004" +
                        "0000000000000000000000000000000000000000000000000000000000000005");
        Abi.Function f = abi.findFunctionByName("intArr5");
        byte[] encodedArgument = f.encodeArguments(Arrays.asList(name));
        Assert.assertArrayEquals(
                encodedOutputParams,
                encodedArgument);
        List<?> decodedOutputParams = abi.decodeFunctionOutput("intArr5", encodedOutputParams);
        Assert.assertTrue(decodedOutputParams.size() == 1);
        Assert.assertArrayEquals(name, (Object[]) decodedOutputParams.get(0));
    }

    @Test
    public void testAbi_TwoIntArray_EncodeAndDecode() {
        Abi abi = Abi.fromJson("[{\"inputs\":[{\"internalType\":\"uint256[]\",\"name\":\"input1\",\"type\":\"uint256[]\"},{\"internalType\":\"uint256[]\",\"name\":\"input2\",\"type\":\"uint256[]\"}],\"name\":\"intArr5I2R2\",\"outputs\":[{\"internalType\":\"uint256[]\",\"name\":\"\",\"type\":\"uint256[]\"},{\"internalType\":\"uint256[]\",\"name\":\"\",\"type\":\"uint256[]\"}],\"stateMutability\":\"view\",\"type\":\"function\"}]");
        BigInteger[] name = { BigInteger.valueOf(0), BigInteger.valueOf(1), BigInteger.valueOf(2), BigInteger.valueOf(3), BigInteger.valueOf(4) };
        byte[] encodedOutputParams = BytesUtils.hexStringToBytes(
                "0000000000000000000000000000000000000000000000000000000000000040" +
                        "0000000000000000000000000000000000000000000000000000000000000100" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "0000000000000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000001" +
                        "0000000000000000000000000000000000000000000000000000000000000002" +
                        "0000000000000000000000000000000000000000000000000000000000000003" +
                        "0000000000000000000000000000000000000000000000000000000000000004" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "0000000000000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000001" +
                        "0000000000000000000000000000000000000000000000000000000000000002" +
                        "0000000000000000000000000000000000000000000000000000000000000003" +
                        "0000000000000000000000000000000000000000000000000000000000000004");
        Abi.Function f = abi.findFunctionByName("intArr5I2R2");
        byte[] encodedArgument = f.encodeArguments(Arrays.asList(name), Arrays.asList(name));
        Assert.assertArrayEquals(
                encodedOutputParams,
                encodedArgument);
        List<?> decodedOutputParams = abi.decodeFunctionOutput("intArr5I2R2", encodedOutputParams);
        Assert.assertTrue(decodedOutputParams.size() == 2);
        Assert.assertArrayEquals(name, (Object[]) decodedOutputParams.get(0));
        Assert.assertArrayEquals(name, (Object[]) decodedOutputParams.get(1));
    }

    @Test
    public void testAbi_TwoStaticIntArray_EncodeAndDecode() {
        Abi abi = Abi.fromJson("[{\"inputs\":[{\"internalType\":\"uint256[5]\",\"name\":\"input1\",\"type\":\"uint256[5]\"},{\"internalType\":\"uint256[5]\",\"name\":\"input2\",\"type\":\"uint256[5]\"}],\"name\":\"intArr5I2R2Static\",\"outputs\":[{\"internalType\":\"uint256[5]\",\"name\":\"\",\"type\":\"uint256[5]\"},{\"internalType\":\"uint256[5]\",\"name\":\"\",\"type\":\"uint256[5]\"}],\"stateMutability\":\"view\",\"type\":\"function\"}]");
        BigInteger[] name = { BigInteger.valueOf(0), BigInteger.valueOf(1), BigInteger.valueOf(2), BigInteger.valueOf(3), BigInteger.valueOf(4) };
        byte[] encodedOutputParams = BytesUtils.hexStringToBytes(
                        "0000000000000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000001" +
                        "0000000000000000000000000000000000000000000000000000000000000002" +
                        "0000000000000000000000000000000000000000000000000000000000000003" +
                        "0000000000000000000000000000000000000000000000000000000000000004" +
                        "0000000000000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000001" +
                        "0000000000000000000000000000000000000000000000000000000000000002" +
                        "0000000000000000000000000000000000000000000000000000000000000003" +
                        "0000000000000000000000000000000000000000000000000000000000000004");
        Abi.Function f = abi.findFunctionByName("intArr5I2R2Static");
        byte[] encodedArgument = f.encodeArguments(Arrays.asList(name), Arrays.asList(name));
        Assert.assertArrayEquals(
                encodedOutputParams,
                encodedArgument);
        List<?> decodedOutputParams = abi.decodeFunctionOutput("intArr5I2R2Static", encodedOutputParams);
        Assert.assertTrue(decodedOutputParams.size() == 2);
        Assert.assertArrayEquals(name, (Object[]) decodedOutputParams.get(0));
        Assert.assertArrayEquals(name, (Object[]) decodedOutputParams.get(1));
    }

    @Test
    public void testAbi_TwoIntArray_OneStatic_EncodeAndDecode() {
        Abi abi = Abi.fromJson("[{\"inputs\":[{\"internalType\":\"uint256[]\",\"name\":\"input1\",\"type\":\"uint256[]\"},{\"internalType\":\"uint256[5]\",\"name\":\"input2\",\"type\":\"uint256[5]\"}],\"name\":\"intArr5I2R2DynamicAndStatic\",\"outputs\":[{\"internalType\":\"uint256[]\",\"name\":\"\",\"type\":\"uint256[]\"},{\"internalType\":\"uint256[5]\",\"name\":\"\",\"type\":\"uint256[5]\"}],\"stateMutability\":\"view\",\"type\":\"function\"}]");
        BigInteger[] name = { BigInteger.valueOf(0), BigInteger.valueOf(1), BigInteger.valueOf(2), BigInteger.valueOf(3), BigInteger.valueOf(4) };
        byte[] encodedOutputParams = BytesUtils.hexStringToBytes(
                "00000000000000000000000000000000000000000000000000000000000000c0" +
                        "0000000000000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000001" +
                        "0000000000000000000000000000000000000000000000000000000000000002" +
                        "0000000000000000000000000000000000000000000000000000000000000003" +
                        "0000000000000000000000000000000000000000000000000000000000000004" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "0000000000000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000001" +
                        "0000000000000000000000000000000000000000000000000000000000000002" +
                        "0000000000000000000000000000000000000000000000000000000000000003" +
                        "0000000000000000000000000000000000000000000000000000000000000004");
        Abi.Function f = abi.findFunctionByName("intArr5I2R2DynamicAndStatic");
        byte[] encodedArgument = f.encodeArguments(Arrays.asList(name), Arrays.asList(name));
//        System.out.println(BytesUtils.bytesToHexString(encodedArgument));
        Assert.assertArrayEquals(
                encodedOutputParams,
                encodedArgument);
        List<?> decodedOutputParams = abi.decodeFunctionOutput("intArr5I2R2DynamicAndStatic", encodedOutputParams);
        Assert.assertTrue(decodedOutputParams.size() == 2);
        Assert.assertArrayEquals(name, (Object[]) decodedOutputParams.get(0));
        Assert.assertArrayEquals(name, (Object[]) decodedOutputParams.get(1));
    }

    @Test
    public void testAbi_TwoStaticArray_OneString_EncodeAndDecode() {
        Abi abi = Abi.fromJson("[{\"inputs\":[{\"internalType\":\"string[5]\",\"name\":\"input1\",\"type\":\"string[5]\"},{\"internalType\":\"uint256[5]\",\"name\":\"input2\",\"type\":\"uint256[5]\"}],\"name\":\"strIntArr5I2R2Static\",\"outputs\":[{\"internalType\":\"string[5]\",\"name\":\"\",\"type\":\"string[5]\"},{\"internalType\":\"uint256[5]\",\"name\":\"\",\"type\":\"uint256[5]\"}],\"stateMutability\":\"view\",\"type\":\"function\"}]");
        BigInteger[] value = { BigInteger.valueOf(0), BigInteger.valueOf(1), BigInteger.valueOf(2), BigInteger.valueOf(3), BigInteger.valueOf(4) };
        String[] name = { "alice", "alice", "alice", "alice", "alice" };
        byte[] encodedOutputParams = BytesUtils.hexStringToBytes(
                "00000000000000000000000000000000000000000000000000000000000000c0" +
                        "0000000000000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000001" +
                        "0000000000000000000000000000000000000000000000000000000000000002" +
                        "0000000000000000000000000000000000000000000000000000000000000003" +
                        "0000000000000000000000000000000000000000000000000000000000000004" +
                        "00000000000000000000000000000000000000000000000000000000000000a0" +
                        "00000000000000000000000000000000000000000000000000000000000000e0" +
                        "0000000000000000000000000000000000000000000000000000000000000120" +
                        "0000000000000000000000000000000000000000000000000000000000000160" +
                        "00000000000000000000000000000000000000000000000000000000000001a0" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000");
        Abi.Function f = abi.findFunctionByName("strIntArr5I2R2Static");
        byte[] encodedArgument = f.encodeArguments(Arrays.asList(name), Arrays.asList(value));
//        System.out.println(BytesUtils.bytesToHexString(encodedArgument));
        Assert.assertArrayEquals(
                encodedOutputParams,
                encodedArgument);
        List<?> decodedOutputParams = abi.decodeFunctionOutput("strIntArr5I2R2Static", encodedOutputParams);
        Assert.assertTrue(decodedOutputParams.size() == 2);
        Assert.assertArrayEquals(name, (Object[]) decodedOutputParams.get(0));
        Assert.assertArrayEquals(value, (Object[]) decodedOutputParams.get(1));
    }

    @Test
    public void testAbi_TwoArray_OneIntStatic_EncodeAndDecode() {
        Abi abi = Abi.fromJson("[{\"inputs\":[{\"internalType\":\"uint256[5]\",\"name\":\"input1\",\"type\":\"uint256[5]\"},{\"internalType\":\"string[]\",\"name\":\"input2\",\"type\":\"string[]\"}],\"name\":\"strIntArr5I2R2DynamicAndStatic\",\"outputs\":[{\"internalType\":\"uint256[5]\",\"name\":\"\",\"type\":\"uint256[5]\"},{\"internalType\":\"string[]\",\"name\":\"\",\"type\":\"string[]\"}],\"stateMutability\":\"view\",\"type\":\"function\"}]");
        BigInteger[] value = { BigInteger.valueOf(0), BigInteger.valueOf(1), BigInteger.valueOf(2), BigInteger.valueOf(3), BigInteger.valueOf(4) };
        String[] name = { "alice", "alice", "alice", "alice", "alice" };
        byte[] encodedOutputParams = BytesUtils.hexStringToBytes(
                "0000000000000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000001" +
                        "0000000000000000000000000000000000000000000000000000000000000002" +
                        "0000000000000000000000000000000000000000000000000000000000000003" +
                        "0000000000000000000000000000000000000000000000000000000000000004" +
                        "00000000000000000000000000000000000000000000000000000000000000c0" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "00000000000000000000000000000000000000000000000000000000000000a0" +
                        "00000000000000000000000000000000000000000000000000000000000000e0" +
                        "0000000000000000000000000000000000000000000000000000000000000120" +
                        "0000000000000000000000000000000000000000000000000000000000000160" +
                        "00000000000000000000000000000000000000000000000000000000000001a0" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000");
        Abi.Function f = abi.findFunctionByName("strIntArr5I2R2DynamicAndStatic");
        byte[] encodedArgument = f.encodeArguments(Arrays.asList(value), Arrays.asList(name));
        Assert.assertArrayEquals(
                encodedOutputParams,
                encodedArgument);
        List<?> decodedOutputParams = abi.decodeFunctionOutput("strIntArr5I2R2DynamicAndStatic", encodedOutputParams);
        Assert.assertTrue(decodedOutputParams.size() == 2);
        Assert.assertArrayEquals(value, (Object[]) decodedOutputParams.get(0));
        Assert.assertArrayEquals(name, (Object[]) decodedOutputParams.get(1));
    }

    @Test
    public void testAbi_2DIntArray_EncodeAndDecode() {
        Abi abi = Abi.fromJson("[{\"inputs\":[{\"internalType\":\"uint256[][]\",\"name\":\"input\",\"type\":\"uint256[][]\"}],\"name\":\"intArr2DWithInput\",\"outputs\":[{\"internalType\":\"uint256[][]\",\"name\":\"\",\"type\":\"uint256[][]\"}],\"stateMutability\":\"view\",\"type\":\"function\"}]");
        BigInteger[][] name = { { BigInteger.valueOf(1) },
                { BigInteger.valueOf(2), BigInteger.valueOf(2) },
                { BigInteger.valueOf(3), BigInteger.valueOf(3), BigInteger.valueOf(3) },
                { BigInteger.valueOf(4), BigInteger.valueOf(4), BigInteger.valueOf(4), BigInteger.valueOf(4) },
                { BigInteger.valueOf(5), BigInteger.valueOf(5), BigInteger.valueOf(5), BigInteger.valueOf(5), BigInteger.valueOf(5)} };
        byte[] encodedOutputParams = BytesUtils.hexStringToBytes(
                "0000000000000000000000000000000000000000000000000000000000000020" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "00000000000000000000000000000000000000000000000000000000000000a0" +
                        "00000000000000000000000000000000000000000000000000000000000000e0" +
                        "0000000000000000000000000000000000000000000000000000000000000140" +
                        "00000000000000000000000000000000000000000000000000000000000001c0" +
                        "0000000000000000000000000000000000000000000000000000000000000260" +
                        "0000000000000000000000000000000000000000000000000000000000000001" +
                        "0000000000000000000000000000000000000000000000000000000000000001" +
                        "0000000000000000000000000000000000000000000000000000000000000002" +
                        "0000000000000000000000000000000000000000000000000000000000000002" +
                        "0000000000000000000000000000000000000000000000000000000000000002" +
                        "0000000000000000000000000000000000000000000000000000000000000003" +
                        "0000000000000000000000000000000000000000000000000000000000000003" +
                        "0000000000000000000000000000000000000000000000000000000000000003" +
                        "0000000000000000000000000000000000000000000000000000000000000003" +
                        "0000000000000000000000000000000000000000000000000000000000000004" +
                        "0000000000000000000000000000000000000000000000000000000000000004" +
                        "0000000000000000000000000000000000000000000000000000000000000004" +
                        "0000000000000000000000000000000000000000000000000000000000000004" +
                        "0000000000000000000000000000000000000000000000000000000000000004" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "0000000000000000000000000000000000000000000000000000000000000005");
        Abi.Function f = abi.findFunctionByName("intArr2DWithInput");
        byte[] encodedArgument = f.encodeArguments(Arrays.asList(name));
        Assert.assertArrayEquals(
                encodedOutputParams,
                encodedArgument);
        List<?> decodedOutputParams = abi.decodeFunctionOutput("intArr2DWithInput", encodedOutputParams);
        Assert.assertTrue(decodedOutputParams.size() == 1);
        Assert.assertArrayEquals(name, (Object[]) decodedOutputParams.get(0));
    }

    @Test
    public void testAbi_2DPartialStaticIntArray_FirstDimensionDynamic_EncodeAndDecode() {
        Abi abi = Abi.fromJson("[{\"inputs\":[{\"internalType\":\"uint256[5][]\",\"name\":\"input\",\"type\":\"uint256[5][]\"}],\"name\":\"intArr2DStaticWithInput\",\"outputs\":[{\"internalType\":\"uint256[5][]\",\"name\":\"\",\"type\":\"uint256[5][]\"}],\"stateMutability\":\"view\",\"type\":\"function\"}]");
        BigInteger[][] name = { { BigInteger.valueOf(1), BigInteger.valueOf(1), BigInteger.valueOf(1), BigInteger.valueOf(1), BigInteger.valueOf(1) },
                { BigInteger.valueOf(2), BigInteger.valueOf(2), BigInteger.valueOf(2), BigInteger.valueOf(2), BigInteger.valueOf(2) },
                { BigInteger.valueOf(3), BigInteger.valueOf(3), BigInteger.valueOf(3), BigInteger.valueOf(3), BigInteger.valueOf(3) },
                { BigInteger.valueOf(4), BigInteger.valueOf(4), BigInteger.valueOf(4), BigInteger.valueOf(4), BigInteger.valueOf(4) },
                { BigInteger.valueOf(5), BigInteger.valueOf(5), BigInteger.valueOf(5), BigInteger.valueOf(5), BigInteger.valueOf(5)} };
        byte[] encodedOutputParams = BytesUtils.hexStringToBytes(
                "0000000000000000000000000000000000000000000000000000000000000020" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "0000000000000000000000000000000000000000000000000000000000000001" +
                        "0000000000000000000000000000000000000000000000000000000000000001" +
                        "0000000000000000000000000000000000000000000000000000000000000001" +
                        "0000000000000000000000000000000000000000000000000000000000000001" +
                        "0000000000000000000000000000000000000000000000000000000000000001" +
                        "0000000000000000000000000000000000000000000000000000000000000002" +
                        "0000000000000000000000000000000000000000000000000000000000000002" +
                        "0000000000000000000000000000000000000000000000000000000000000002" +
                        "0000000000000000000000000000000000000000000000000000000000000002" +
                        "0000000000000000000000000000000000000000000000000000000000000002" +
                        "0000000000000000000000000000000000000000000000000000000000000003" +
                        "0000000000000000000000000000000000000000000000000000000000000003" +
                        "0000000000000000000000000000000000000000000000000000000000000003" +
                        "0000000000000000000000000000000000000000000000000000000000000003" +
                        "0000000000000000000000000000000000000000000000000000000000000003" +
                        "0000000000000000000000000000000000000000000000000000000000000004" +
                        "0000000000000000000000000000000000000000000000000000000000000004" +
                        "0000000000000000000000000000000000000000000000000000000000000004" +
                        "0000000000000000000000000000000000000000000000000000000000000004" +
                        "0000000000000000000000000000000000000000000000000000000000000004" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "0000000000000000000000000000000000000000000000000000000000000005");
        Abi.Function f = abi.findFunctionByName("intArr2DStaticWithInput");
        byte[] encodedArgument = f.encodeArguments(Arrays.asList(name));
        Assert.assertArrayEquals(
                encodedOutputParams,
                encodedArgument);
        List<?> decodedOutputParams = abi.decodeFunctionOutput("intArr2DStaticWithInput", encodedOutputParams);
        Assert.assertTrue(decodedOutputParams.size() == 1);
        Assert.assertArrayEquals(name, (Object[]) decodedOutputParams.get(0));
    }

    @Test
    public void testAbi_2DPartialStaticIntArray_SecondDimensionDynamic_EncodeAndDecode() {
        Abi abi = Abi.fromJson("[{\"inputs\":[{\"internalType\":\"uint256[][5]\",\"name\":\"input\",\"type\":\"uint256[][5]\"}],\"name\":\"intArr2DPartialStaticWithInput\",\"outputs\":[{\"internalType\":\"uint256[][5]\",\"name\":\"\",\"type\":\"uint256[][5]\"}],\"stateMutability\":\"view\",\"type\":\"function\"}]");
        BigInteger[][] name = { { BigInteger.valueOf(1) },
                { BigInteger.valueOf(2), BigInteger.valueOf(2) },
                { BigInteger.valueOf(3), BigInteger.valueOf(3), BigInteger.valueOf(3) },
                { BigInteger.valueOf(4), BigInteger.valueOf(4), BigInteger.valueOf(4), BigInteger.valueOf(4) },
                { BigInteger.valueOf(5), BigInteger.valueOf(5), BigInteger.valueOf(5), BigInteger.valueOf(5), BigInteger.valueOf(5)} };
        byte[] encodedOutputParams = BytesUtils.hexStringToBytes(
                "0000000000000000000000000000000000000000000000000000000000000020" +
                        "00000000000000000000000000000000000000000000000000000000000000a0" +
                        "00000000000000000000000000000000000000000000000000000000000000e0" +
                        "0000000000000000000000000000000000000000000000000000000000000140" +
                        "00000000000000000000000000000000000000000000000000000000000001c0" +
                        "0000000000000000000000000000000000000000000000000000000000000260" +
                        "0000000000000000000000000000000000000000000000000000000000000001" +
                        "0000000000000000000000000000000000000000000000000000000000000001" +
                        "0000000000000000000000000000000000000000000000000000000000000002" +
                        "0000000000000000000000000000000000000000000000000000000000000002" +
                        "0000000000000000000000000000000000000000000000000000000000000002" +
                        "0000000000000000000000000000000000000000000000000000000000000003" +
                        "0000000000000000000000000000000000000000000000000000000000000003" +
                        "0000000000000000000000000000000000000000000000000000000000000003" +
                        "0000000000000000000000000000000000000000000000000000000000000003" +
                        "0000000000000000000000000000000000000000000000000000000000000004" +
                        "0000000000000000000000000000000000000000000000000000000000000004" +
                        "0000000000000000000000000000000000000000000000000000000000000004" +
                        "0000000000000000000000000000000000000000000000000000000000000004" +
                        "0000000000000000000000000000000000000000000000000000000000000004" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "0000000000000000000000000000000000000000000000000000000000000005");
        Abi.Function f = abi.findFunctionByName("intArr2DPartialStaticWithInput");
        byte[] encodedArgument = f.encodeArguments(Arrays.asList(name));
        Assert.assertArrayEquals(
                encodedOutputParams,
                encodedArgument);
        List<?> decodedOutputParams = abi.decodeFunctionOutput("intArr2DPartialStaticWithInput", encodedOutputParams);
        Assert.assertTrue(decodedOutputParams.size() == 1);
        Assert.assertArrayEquals(name, (Object[]) decodedOutputParams.get(0));
    }

    @Test
    public void testAbi_2DStaticIntArray_EncodeAndDecode() {
        Abi abi = Abi.fromJson("[{\"inputs\":[{\"internalType\":\"uint256[5][3]\",\"name\":\"input\",\"type\":\"uint256[5][3]\"}],\"name\":\"intArr2DCompleteStaticWithInput\",\"outputs\":[{\"internalType\":\"uint256[5][3]\",\"name\":\"\",\"type\":\"uint256[5][3]\"}],\"stateMutability\":\"view\",\"type\":\"function\"}]");
        BigInteger[][] name = { { BigInteger.valueOf(1), BigInteger.valueOf(1), BigInteger.valueOf(1), BigInteger.valueOf(1), BigInteger.valueOf(1) },
                { BigInteger.valueOf(2), BigInteger.valueOf(2), BigInteger.valueOf(2), BigInteger.valueOf(2), BigInteger.valueOf(2) },
                { BigInteger.valueOf(3), BigInteger.valueOf(3), BigInteger.valueOf(3), BigInteger.valueOf(3), BigInteger.valueOf(3) } };
        byte[] encodedOutputParams = BytesUtils.hexStringToBytes(
                "0000000000000000000000000000000000000000000000000000000000000001" +
                        "0000000000000000000000000000000000000000000000000000000000000001" +
                        "0000000000000000000000000000000000000000000000000000000000000001" +
                        "0000000000000000000000000000000000000000000000000000000000000001" +
                        "0000000000000000000000000000000000000000000000000000000000000001" +
                        "0000000000000000000000000000000000000000000000000000000000000002" +
                        "0000000000000000000000000000000000000000000000000000000000000002" +
                        "0000000000000000000000000000000000000000000000000000000000000002" +
                        "0000000000000000000000000000000000000000000000000000000000000002" +
                        "0000000000000000000000000000000000000000000000000000000000000002" +
                        "0000000000000000000000000000000000000000000000000000000000000003" +
                        "0000000000000000000000000000000000000000000000000000000000000003" +
                        "0000000000000000000000000000000000000000000000000000000000000003" +
                        "0000000000000000000000000000000000000000000000000000000000000003" +
                        "0000000000000000000000000000000000000000000000000000000000000003");
        Abi.Function f = abi.findFunctionByName("intArr2DCompleteStaticWithInput");
        byte[] encodedArgument = f.encodeArguments(Arrays.asList(name));
        Assert.assertArrayEquals(
                encodedOutputParams,
                encodedArgument);
        List<?> decodedOutputParams = abi.decodeFunctionOutput("intArr2DCompleteStaticWithInput", encodedOutputParams);
        Assert.assertTrue(decodedOutputParams.size() == 1);
        Assert.assertArrayEquals(name, (Object[]) decodedOutputParams.get(0));
    }

    @Test
    public void testAbi_2DStringArray_EncodeAndDecode() {
        Abi abi = Abi.fromJson("[{\"inputs\":[{\"internalType\":\"string[][]\",\"name\":\"input\",\"type\":\"string[][]\"}],\"name\":\"strArr2DWithInput\",\"outputs\":[{\"internalType\":\"string[][]\",\"name\":\"\",\"type\":\"string[][]\"}],\"stateMutability\":\"view\",\"type\":\"function\"}]");
        String[][] name = { { "alice" },
                { "alice", "alice" },
                { "alice", "alice", "alice" },
                { "alice", "alice", "alice", "alice" },
                { "alice", "alice", "alice", "alice", "alice" } };
        byte[] encodedOutputParams = BytesUtils.hexStringToBytes(
                "0000000000000000000000000000000000000000000000000000000000000020" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "00000000000000000000000000000000000000000000000000000000000000a0" +
                        "0000000000000000000000000000000000000000000000000000000000000120" +
                        "0000000000000000000000000000000000000000000000000000000000000200" +
                        "0000000000000000000000000000000000000000000000000000000000000340" +
                        "00000000000000000000000000000000000000000000000000000000000004e0" +
                        "0000000000000000000000000000000000000000000000000000000000000001" +
                        "0000000000000000000000000000000000000000000000000000000000000020" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000002" +
                        "0000000000000000000000000000000000000000000000000000000000000040" +
                        "0000000000000000000000000000000000000000000000000000000000000080" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000003" +
                        "0000000000000000000000000000000000000000000000000000000000000060" +
                        "00000000000000000000000000000000000000000000000000000000000000a0" +
                        "00000000000000000000000000000000000000000000000000000000000000e0" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000004" +
                        "0000000000000000000000000000000000000000000000000000000000000080" +
                        "00000000000000000000000000000000000000000000000000000000000000c0" +
                        "0000000000000000000000000000000000000000000000000000000000000100" +
                        "0000000000000000000000000000000000000000000000000000000000000140" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "00000000000000000000000000000000000000000000000000000000000000a0" +
                        "00000000000000000000000000000000000000000000000000000000000000e0" +
                        "0000000000000000000000000000000000000000000000000000000000000120" +
                        "0000000000000000000000000000000000000000000000000000000000000160" +
                        "00000000000000000000000000000000000000000000000000000000000001a0" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000");
        Abi.Function f = abi.findFunctionByName("strArr2DWithInput");
        byte[] encodedArgument = f.encodeArguments(Arrays.asList(name));
        Assert.assertArrayEquals(
                encodedOutputParams,
                encodedArgument);
        List<?> decodedOutputParams = abi.decodeFunctionOutput("strArr2DWithInput", encodedOutputParams);
        Assert.assertTrue(decodedOutputParams.size() == 1);
        Assert.assertArrayEquals(name, (Object[]) decodedOutputParams.get(0));
    }

    @Test
    public void testAbi_2DPartialStaticStringArray_FirstDimensionDynamic_EncodeAndDecode() {
        Abi abi = Abi.fromJson("[{\"inputs\":[{\"internalType\":\"string[3][]\",\"name\":\"input\",\"type\":\"string[3][]\"}],\"name\":\"strArr2DStaticWithInput\",\"outputs\":[{\"internalType\":\"string[3][]\",\"name\":\"\",\"type\":\"string[3][]\"}],\"stateMutability\":\"view\",\"type\":\"function\"}]");
        String[][] name = { { "alice", "alice", "alice" },
                { "alice", "alice", "alice" },
                { "alice", "alice", "alice" },
                { "alice", "alice", "alice" },
                { "alice", "alice", "alice" } };
        byte[] encodedOutputParams = BytesUtils.hexStringToBytes(
                "0000000000000000000000000000000000000000000000000000000000000020" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "00000000000000000000000000000000000000000000000000000000000000a0" +
                        "00000000000000000000000000000000000000000000000000000000000001c0" +
                        "00000000000000000000000000000000000000000000000000000000000002e0" +
                        "0000000000000000000000000000000000000000000000000000000000000400" +
                        "0000000000000000000000000000000000000000000000000000000000000520" +
                        "0000000000000000000000000000000000000000000000000000000000000060" +
                        "00000000000000000000000000000000000000000000000000000000000000a0" +
                        "00000000000000000000000000000000000000000000000000000000000000e0" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000060" +
                        "00000000000000000000000000000000000000000000000000000000000000a0" +
                        "00000000000000000000000000000000000000000000000000000000000000e0" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000060" +
                        "00000000000000000000000000000000000000000000000000000000000000a0" +
                        "00000000000000000000000000000000000000000000000000000000000000e0" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000060" +
                        "00000000000000000000000000000000000000000000000000000000000000a0" +
                        "00000000000000000000000000000000000000000000000000000000000000e0" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000060" +
                        "00000000000000000000000000000000000000000000000000000000000000a0" +
                        "00000000000000000000000000000000000000000000000000000000000000e0" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000");
        Abi.Function f = abi.findFunctionByName("strArr2DStaticWithInput");
        byte[] encodedArgument = f.encodeArguments(Arrays.asList(name));
        Assert.assertArrayEquals(
                encodedOutputParams,
                encodedArgument);
        List<?> decodedOutputParams = abi.decodeFunctionOutput("strArr2DStaticWithInput", encodedOutputParams);
        Assert.assertTrue(decodedOutputParams.size() == 1);
        Assert.assertArrayEquals(name, (Object[]) decodedOutputParams.get(0));
    }

    @Test
    public void testAbi_2DPartialStaticStringArray_SecondDimensionDynamic_EncodeAndDecode() {
        Abi abi = Abi.fromJson("[{\"inputs\":[{\"internalType\":\"string[][5]\",\"name\":\"input\",\"type\":\"string[][5]\"}],\"name\":\"strArr2DPartialStaticWithInput\",\"outputs\":[{\"internalType\":\"string[][5]\",\"name\":\"\",\"type\":\"string[][5]\"}],\"stateMutability\":\"view\",\"type\":\"function\"}]");
        String[][] name = { { "alice" },
                { "alice", "alice" },
                { "alice", "alice", "alice" },
                { "alice", "alice", "alice", "alice" },
                { "alice", "alice", "alice", "alice", "alice" } };
        byte[] encodedOutputParams = BytesUtils.hexStringToBytes(
                "0000000000000000000000000000000000000000000000000000000000000020" +
                        "00000000000000000000000000000000000000000000000000000000000000a0" +
                        "0000000000000000000000000000000000000000000000000000000000000120" +
                        "0000000000000000000000000000000000000000000000000000000000000200" +
                        "0000000000000000000000000000000000000000000000000000000000000340" +
                        "00000000000000000000000000000000000000000000000000000000000004e0" +
                        "0000000000000000000000000000000000000000000000000000000000000001" +
                        "0000000000000000000000000000000000000000000000000000000000000020" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000002" +
                        "0000000000000000000000000000000000000000000000000000000000000040" +
                        "0000000000000000000000000000000000000000000000000000000000000080" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000003" +
                        "0000000000000000000000000000000000000000000000000000000000000060" +
                        "00000000000000000000000000000000000000000000000000000000000000a0" +
                        "00000000000000000000000000000000000000000000000000000000000000e0" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000004" +
                        "0000000000000000000000000000000000000000000000000000000000000080" +
                        "00000000000000000000000000000000000000000000000000000000000000c0" +
                        "0000000000000000000000000000000000000000000000000000000000000100" +
                        "0000000000000000000000000000000000000000000000000000000000000140" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "00000000000000000000000000000000000000000000000000000000000000a0" +
                        "00000000000000000000000000000000000000000000000000000000000000e0" +
                        "0000000000000000000000000000000000000000000000000000000000000120" +
                        "0000000000000000000000000000000000000000000000000000000000000160" +
                        "00000000000000000000000000000000000000000000000000000000000001a0" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000");
        Abi.Function f = abi.findFunctionByName("strArr2DPartialStaticWithInput");
        byte[] encodedArgument = f.encodeArguments(Arrays.asList(name));
        Assert.assertArrayEquals(
                encodedOutputParams,
                encodedArgument);
        List<?> decodedOutputParams = abi.decodeFunctionOutput("strArr2DPartialStaticWithInput", encodedOutputParams);
        Assert.assertTrue(decodedOutputParams.size() == 1);
        Assert.assertArrayEquals(name, (Object[]) decodedOutputParams.get(0));
    }

    @Test
    public void testAbi_2DStaticStringArray_EncodeAndDecode() {
        Abi abi = Abi.fromJson("[{\"inputs\":[{\"internalType\":\"string[5][3]\",\"name\":\"input\",\"type\":\"string[5][3]\"}],\"name\":\"strArr2DCompleteStaticWithInput\",\"outputs\":[{\"internalType\":\"string[5][3]\",\"name\":\"\",\"type\":\"string[5][3]\"}],\"stateMutability\":\"pure\",\"type\":\"function\"}]");
        String[][] name = { { "alice", "alice", "alice", "alice", "alice" },
                { "alice", "alice", "alice", "alice", "alice" },
                { "alice", "alice", "alice", "alice", "alice" } };
        byte[] encodedOutputParams = BytesUtils.hexStringToBytes(
                "0000000000000000000000000000000000000000000000000000000000000020" +
                        "0000000000000000000000000000000000000000000000000000000000000060" +
                        "0000000000000000000000000000000000000000000000000000000000000240" +
                        "0000000000000000000000000000000000000000000000000000000000000420" +
                        "00000000000000000000000000000000000000000000000000000000000000a0" +
                        "00000000000000000000000000000000000000000000000000000000000000e0" +
                        "0000000000000000000000000000000000000000000000000000000000000120" +
                        "0000000000000000000000000000000000000000000000000000000000000160" +
                        "00000000000000000000000000000000000000000000000000000000000001a0" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "00000000000000000000000000000000000000000000000000000000000000a0" +
                        "00000000000000000000000000000000000000000000000000000000000000e0" +
                        "0000000000000000000000000000000000000000000000000000000000000120" +
                        "0000000000000000000000000000000000000000000000000000000000000160" +
                        "00000000000000000000000000000000000000000000000000000000000001a0" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "00000000000000000000000000000000000000000000000000000000000000a0" +
                        "00000000000000000000000000000000000000000000000000000000000000e0" +
                        "0000000000000000000000000000000000000000000000000000000000000120" +
                        "0000000000000000000000000000000000000000000000000000000000000160" +
                        "00000000000000000000000000000000000000000000000000000000000001a0" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000");
        Abi.Function f = abi.findFunctionByName("strArr2DCompleteStaticWithInput");
        byte[] encodedArgument = f.encodeArguments(Arrays.asList(name));
        Assert.assertArrayEquals(
                encodedOutputParams,
                encodedArgument);
        List<?> decodedOutputParams = abi.decodeFunctionOutput("strArr2DCompleteStaticWithInput", encodedOutputParams);
        Assert.assertTrue(decodedOutputParams.size() == 1);
        Assert.assertArrayEquals(name, (Object[]) decodedOutputParams.get(0));
    }

    @Test
    public void testAbi_Three2DArray_EncodeAndDecode() {
        Abi abi = Abi.fromJson("[{\"inputs\":[{\"internalType\":\"uint256[][]\",\"name\":\"input1\",\"type\":\"uint256[][]\"},{\"internalType\":\"string[5][]\",\"name\":\"input2\",\"type\":\"string[5][]\"},{\"internalType\":\"uint256[3][5]\",\"name\":\"input3\",\"type\":\"uint256[3][5]\"}],\"name\":\"compoundArr2DR3WithInput\",\"outputs\":[{\"internalType\":\"uint256[][]\",\"name\":\"\",\"type\":\"uint256[][]\"},{\"internalType\":\"string[5][]\",\"name\":\"\",\"type\":\"string[5][]\"},{\"internalType\":\"uint256[3][5]\",\"name\":\"\",\"type\":\"uint256[3][5]\"}],\"stateMutability\":\"pure\",\"type\":\"function\"}]");
        BigInteger[][] value1 = { { BigInteger.valueOf(1), BigInteger.valueOf(2), BigInteger.valueOf(3) },
                { BigInteger.valueOf(1), BigInteger.valueOf(2), BigInteger.valueOf(3) },
                { BigInteger.valueOf(1), BigInteger.valueOf(2), BigInteger.valueOf(3) },
                { BigInteger.valueOf(1), BigInteger.valueOf(2), BigInteger.valueOf(3) },
                { BigInteger.valueOf(1), BigInteger.valueOf(2), BigInteger.valueOf(3)} };
        BigInteger[][] value2 = { { BigInteger.valueOf(1) },
                { BigInteger.valueOf(1), BigInteger.valueOf(2) },
                { BigInteger.valueOf(1), BigInteger.valueOf(2), BigInteger.valueOf(3) },
                { BigInteger.valueOf(1), BigInteger.valueOf(2), BigInteger.valueOf(3), BigInteger.valueOf(4) },
                { BigInteger.valueOf(1), BigInteger.valueOf(2), BigInteger.valueOf(3), BigInteger.valueOf(4), BigInteger.valueOf(5)} };
        String[][] name = { { "alice", "alice", "alice", "alice", "alice" },
                { "alice", "alice", "alice", "alice", "alice" },
                { "alice", "alice", "alice", "alice", "alice" },
                { "alice", "alice", "alice", "alice", "alice" },
                { "alice", "alice", "alice", "alice", "alice" } };
        byte[] encodedOutputParams = BytesUtils.hexStringToBytes(
                "0000000000000000000000000000000000000000000000000000000000000220" +
                        "0000000000000000000000000000000000000000000000000000000000000560" +
                        "0000000000000000000000000000000000000000000000000000000000000001" +
                        "0000000000000000000000000000000000000000000000000000000000000002" +
                        "0000000000000000000000000000000000000000000000000000000000000003" +
                        "0000000000000000000000000000000000000000000000000000000000000001" +
                        "0000000000000000000000000000000000000000000000000000000000000002" +
                        "0000000000000000000000000000000000000000000000000000000000000003" +
                        "0000000000000000000000000000000000000000000000000000000000000001" +
                        "0000000000000000000000000000000000000000000000000000000000000002" +
                        "0000000000000000000000000000000000000000000000000000000000000003" +
                        "0000000000000000000000000000000000000000000000000000000000000001" +
                        "0000000000000000000000000000000000000000000000000000000000000002" +
                        "0000000000000000000000000000000000000000000000000000000000000003" +
                        "0000000000000000000000000000000000000000000000000000000000000001" +
                        "0000000000000000000000000000000000000000000000000000000000000002" +
                        "0000000000000000000000000000000000000000000000000000000000000003" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "00000000000000000000000000000000000000000000000000000000000000a0" +
                        "00000000000000000000000000000000000000000000000000000000000000e0" +
                        "0000000000000000000000000000000000000000000000000000000000000140" +
                        "00000000000000000000000000000000000000000000000000000000000001c0" +
                        "0000000000000000000000000000000000000000000000000000000000000260" +
                        "0000000000000000000000000000000000000000000000000000000000000001" +
                        "0000000000000000000000000000000000000000000000000000000000000001" +
                        "0000000000000000000000000000000000000000000000000000000000000002" +
                        "0000000000000000000000000000000000000000000000000000000000000001" +
                        "0000000000000000000000000000000000000000000000000000000000000002" +
                        "0000000000000000000000000000000000000000000000000000000000000003" +
                        "0000000000000000000000000000000000000000000000000000000000000001" +
                        "0000000000000000000000000000000000000000000000000000000000000002" +
                        "0000000000000000000000000000000000000000000000000000000000000003" +
                        "0000000000000000000000000000000000000000000000000000000000000004" +
                        "0000000000000000000000000000000000000000000000000000000000000001" +
                        "0000000000000000000000000000000000000000000000000000000000000002" +
                        "0000000000000000000000000000000000000000000000000000000000000003" +
                        "0000000000000000000000000000000000000000000000000000000000000004" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "0000000000000000000000000000000000000000000000000000000000000001" +
                        "0000000000000000000000000000000000000000000000000000000000000002" +
                        "0000000000000000000000000000000000000000000000000000000000000003" +
                        "0000000000000000000000000000000000000000000000000000000000000004" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "00000000000000000000000000000000000000000000000000000000000000a0" +
                        "0000000000000000000000000000000000000000000000000000000000000280" +
                        "0000000000000000000000000000000000000000000000000000000000000460" +
                        "0000000000000000000000000000000000000000000000000000000000000640" +
                        "0000000000000000000000000000000000000000000000000000000000000820" +
                        "00000000000000000000000000000000000000000000000000000000000000a0" +
                        "00000000000000000000000000000000000000000000000000000000000000e0" +
                        "0000000000000000000000000000000000000000000000000000000000000120" +
                        "0000000000000000000000000000000000000000000000000000000000000160" +
                        "00000000000000000000000000000000000000000000000000000000000001a0" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "00000000000000000000000000000000000000000000000000000000000000a0" +
                        "00000000000000000000000000000000000000000000000000000000000000e0" +
                        "0000000000000000000000000000000000000000000000000000000000000120" +
                        "0000000000000000000000000000000000000000000000000000000000000160" +
                        "00000000000000000000000000000000000000000000000000000000000001a0" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "00000000000000000000000000000000000000000000000000000000000000a0" +
                        "00000000000000000000000000000000000000000000000000000000000000e0" +
                        "0000000000000000000000000000000000000000000000000000000000000120" +
                        "0000000000000000000000000000000000000000000000000000000000000160" +
                        "00000000000000000000000000000000000000000000000000000000000001a0" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "00000000000000000000000000000000000000000000000000000000000000a0" +
                        "00000000000000000000000000000000000000000000000000000000000000e0" +
                        "0000000000000000000000000000000000000000000000000000000000000120" +
                        "0000000000000000000000000000000000000000000000000000000000000160" +
                        "00000000000000000000000000000000000000000000000000000000000001a0" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "00000000000000000000000000000000000000000000000000000000000000a0" +
                        "00000000000000000000000000000000000000000000000000000000000000e0" +
                        "0000000000000000000000000000000000000000000000000000000000000120" +
                        "0000000000000000000000000000000000000000000000000000000000000160" +
                        "00000000000000000000000000000000000000000000000000000000000001a0" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000");
        Abi.Function f = abi.findFunctionByName("compoundArr2DR3WithInput");
        byte[] encodedArgument = f.encodeArguments(Arrays.asList(value2), Arrays.asList(name), Arrays.asList(value1));
        Assert.assertArrayEquals(
                encodedOutputParams,
                encodedArgument);
        List<?> decodedOutputParams = abi.decodeFunctionOutput("compoundArr2DR3WithInput", encodedOutputParams);
        Assert.assertTrue(decodedOutputParams.size() == 3);
        Assert.assertArrayEquals(value2, (Object[]) decodedOutputParams.get(0));
        Assert.assertArrayEquals(name, (Object[]) decodedOutputParams.get(1));
        Assert.assertArrayEquals(value1, (Object[]) decodedOutputParams.get(2));
    }

    @Test
    public void testAbi_3DIntArray_EncodeAndDecode() {
        Abi abi = Abi.fromJson("[{\"inputs\":[{\"internalType\":\"uint256[2][][3]\",\"name\":\"input\",\"type\":\"uint256[2][][3]\"}],\"name\":\"intArr3DStaticWithInput\",\"outputs\":[{\"internalType\":\"uint256[2][][3]\",\"name\":\"\",\"type\":\"uint256[2][][3]\"}],\"stateMutability\":\"pure\",\"type\":\"function\"}]");
        BigInteger[][][] name = { { { BigInteger.valueOf(1), BigInteger.valueOf(2) }  },
                { { BigInteger.valueOf(1), BigInteger.valueOf(2) }, { BigInteger.valueOf(1), BigInteger.valueOf(2) } },
                { { BigInteger.valueOf(1), BigInteger.valueOf(2) }, { BigInteger.valueOf(1), BigInteger.valueOf(2) }, { BigInteger.valueOf(1), BigInteger.valueOf(2) } } };
        byte[] encodedOutputParams = BytesUtils.hexStringToBytes(
                "0000000000000000000000000000000000000000000000000000000000000020" +
                        "0000000000000000000000000000000000000000000000000000000000000060" +
                        "00000000000000000000000000000000000000000000000000000000000000c0" +
                        "0000000000000000000000000000000000000000000000000000000000000160" +
                        "0000000000000000000000000000000000000000000000000000000000000001" +
                        "0000000000000000000000000000000000000000000000000000000000000001" +
                        "0000000000000000000000000000000000000000000000000000000000000002" +
                        "0000000000000000000000000000000000000000000000000000000000000002" +
                        "0000000000000000000000000000000000000000000000000000000000000001" +
                        "0000000000000000000000000000000000000000000000000000000000000002" +
                        "0000000000000000000000000000000000000000000000000000000000000001" +
                        "0000000000000000000000000000000000000000000000000000000000000002" +
                        "0000000000000000000000000000000000000000000000000000000000000003" +
                        "0000000000000000000000000000000000000000000000000000000000000001" +
                        "0000000000000000000000000000000000000000000000000000000000000002" +
                        "0000000000000000000000000000000000000000000000000000000000000001" +
                        "0000000000000000000000000000000000000000000000000000000000000002" +
                        "0000000000000000000000000000000000000000000000000000000000000001" +
                        "0000000000000000000000000000000000000000000000000000000000000002");
        Abi.Function f = abi.findFunctionByName("intArr3DStaticWithInput");
        byte[] encodedArgument = f.encodeArguments(Arrays.asList(name));
//        System.out.println(BytesUtils.bytesToHexString(encodedArgument));
        Assert.assertArrayEquals(
                encodedOutputParams,
                encodedArgument);
        List<?> decodedOutputParams = abi.decodeFunctionOutput("intArr3DStaticWithInput", encodedOutputParams);
        Assert.assertTrue(decodedOutputParams.size() == 1);
        Assert.assertArrayEquals(name, (Object[]) decodedOutputParams.get(0));
    }

    @Test
    public void testAbi_3DStringArray_EncodeAndDecode() {
        Abi abi = Abi.fromJson("[{\"inputs\":[{\"internalType\":\"string[][3][]\",\"name\":\"input\",\"type\":\"string[][3][]\"}],\"name\":\"strArr3DStaticWithInput\",\"outputs\":[{\"internalType\":\"string[][3][]\",\"name\":\"\",\"type\":\"string[][3][]\"}],\"stateMutability\":\"pure\",\"type\":\"function\"}]");
        String[][][] name = { { { "alice" }, { "alice" }, { "alice" } },
                { { "alice" }, { "alice" }, { "alice" } } };
        byte[] encodedOutputParams = BytesUtils.hexStringToBytes(
                "0000000000000000000000000000000000000000000000000000000000000020" +
                        "0000000000000000000000000000000000000000000000000000000000000002" +
                        "0000000000000000000000000000000000000000000000000000000000000040" +
                        "0000000000000000000000000000000000000000000000000000000000000220" +
                        "0000000000000000000000000000000000000000000000000000000000000060" +
                        "00000000000000000000000000000000000000000000000000000000000000e0" +
                        "0000000000000000000000000000000000000000000000000000000000000160" +
                        "0000000000000000000000000000000000000000000000000000000000000001" +
                        "0000000000000000000000000000000000000000000000000000000000000020" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000001" +
                        "0000000000000000000000000000000000000000000000000000000000000020" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000001" +
                        "0000000000000000000000000000000000000000000000000000000000000020" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000060" +
                        "00000000000000000000000000000000000000000000000000000000000000e0" +
                        "0000000000000000000000000000000000000000000000000000000000000160" +
                        "0000000000000000000000000000000000000000000000000000000000000001" +
                        "0000000000000000000000000000000000000000000000000000000000000020" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000001" +
                        "0000000000000000000000000000000000000000000000000000000000000020" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000000000000000000000000000000000000000000000001" +
                        "0000000000000000000000000000000000000000000000000000000000000020" +
                        "0000000000000000000000000000000000000000000000000000000000000005" +
                        "616c696365000000000000000000000000000000000000000000000000000000");
        Abi.Function f = abi.findFunctionByName("strArr3DStaticWithInput");
        byte[] encodedArgument = f.encodeArguments(Arrays.asList(name));
//        System.out.println(BytesUtils.bytesToHexString(encodedArgument));
        Assert.assertArrayEquals(
                encodedOutputParams,
                encodedArgument);
        List<?> decodedOutputParams = abi.decodeFunctionOutput("strArr3DStaticWithInput", encodedOutputParams);
        Assert.assertTrue(decodedOutputParams.size() == 1);
        Assert.assertArrayEquals(name, (Object[]) decodedOutputParams.get(0));
    }

    @Test
    public void testAbi_SimpleStructNotSupport_EncodeAndDecode() {
        Assert.assertThrows("Unknown type: tuple", Exception.class, () -> {
            Abi abi = Abi.fromJson("[{\"inputs\":[],\"name\":\"getBet\",\"outputs\":[{\"components\":[{\"internalType\":\"uint256\",\"name\":\"lowerLimit\",\"type\":\"uint256\"},{\"internalType\":\"uint256\",\"name\":\"upperLimit\",\"type\":\"uint256\"},{\"internalType\":\"uint256\",\"name\":\"tipPer\",\"type\":\"uint256\"}],\"internalType\":\"struct Hello.BetLimit\",\"name\":\"\",\"type\":\"tuple\"}],\"stateMutability\":\"pure\",\"type\":\"function\"}]");
            String[][][] name = { { { "alice" }, { "alice" }, { "alice" } },
                    { { "alice" }, { "alice" }, { "alice" } } };
            byte[] encodedOutputParams = BytesUtils.hexStringToBytes(
                    "0000000000000000000000000000000000000000000000000000000000000001" +
                            "0000000000000000000000000000000000000000000000000000000000000001" +
                            "0000000000000000000000000000000000000000000000000000000000000001");
            Abi.Function f = abi.findFunctionByName("getBet");
            byte[] encodedArgument = f.encodeArguments(Arrays.asList(name));
//            System.out.println(BytesUtils.bytesToHexString(encodedArgument));
            Assert.assertArrayEquals(encodedOutputParams, encodedArgument);
            List<?> decodedOutputParams = abi.decodeFunctionOutput("getBet", encodedOutputParams);
//            Assert.assertTrue(decodedOutputParams.size() == 1);
//            Assert.assertArrayEquals(name, (Object[]) decodedOutputParams.get(0));
        });
    }

    @Test
    public void testGetEventId() {
        Abi abi = Abi.fromJson("[" +
                "{ \"type\" : \"event\", \"name\" : \"Transfer\", \"anonymous\" : false, \"inputs\" : [ { \"indexed\" : true, \"name\" : \"from\", \"type\" : \"address\" }, { \"indexed\" : true, \"name\" : \"to\", \"type\" : \"address\" }, { \"name\" : \"value\", \"type\" : \"uint256\" } ] }]");
        byte[] id = abi.findEventByName("Transfer").encodeSignature();
        Assert.assertArrayEquals(
                BytesUtils.hexStringToBytes(
                        "e9a7da5bfc2bcbf4266adfba50ac5d6fa9ba4d52df50d9359a3974c36c131ce1"),
                id);
    }

    @Test
    public void testGetMethodById() {
        Abi abi = Abi.fromJson(
                "[{ \"type\" : \"function\", \"name\" : \"send\", \"constant\" : false, \"inputs\" : [ { \"name\" : \"amount\", \"type\" : \"uint256\" } ] }]");
        byte[] encodedParams = BytesUtils.hexStringToBytes(
                "d6f47d63000000000000000000000000000000000000000000000000000000000000007b");
        Abi.Function f = abi.findFunctionByData(encodedParams);
        Assert.assertNotNull(f);
        Assert.assertEquals("send", f.name);
    }
}
