package org.vitej.core.constants;

import org.vitej.core.protocol.methods.Address;
import org.vitej.core.protocol.methods.Hash;
import org.vitej.core.protocol.methods.TokenId;

import java.math.BigInteger;

public class CommonConstants {
    public static final Hash EMPTY_HASH = new Hash("0000000000000000000000000000000000000000000000000000000000000000");
    public static final Address EMPTY_ADDRESS = new Address("vite_0000000000000000000000000000000000000000a4f3a0cb58");
    public static final TokenId EMPTY_TOKENID = new TokenId("tti_000000000000000000004cfd");

    public static final TokenId VITE_TOKEN_ID = new TokenId("tti_5649544520544f4b454e6e40");

    public static final byte[] EMPTY_BYTES_8 = new byte[]{0, 0, 0, 0, 0, 0, 0, 0};

    public static final int RETRY_TIMES = 10;

    public static final BigInteger CREATE_CONTRACT_FEE = new BigInteger("10000000000000000000");
}
