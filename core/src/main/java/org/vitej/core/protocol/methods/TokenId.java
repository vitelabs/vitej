package org.vitej.core.protocol.methods;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.util.encoders.Hex;
import org.vitej.core.wallet.Crypto;

import java.util.Arrays;

public class TokenId {
    private byte[] tokenIdCore;
    private byte[] checksum;

    public TokenId(byte[] tokenIdCore, byte[] checksum) {
        this.tokenIdCore = tokenIdCore;
        this.checksum = checksum;
    }

    public TokenId(String tokenId) {
        Preconditions.checkArgument(isValid(tokenId), "Invalid tokenId");
        this.tokenIdCore = Hex.decode(tokenId.substring(4, 24));
        this.checksum = Hex.decode(tokenId.substring(24, 28));
    }

    public TokenId(byte[] tokenIdCore) {
        this.tokenIdCore = tokenIdCore;
        this.checksum = getCheckSum(tokenIdCore);
    }

    public byte[] getBytes() {
        return tokenIdCore;
    }

    @Override
    public String toString() {
        return "tti_" + Hex.toHexString(tokenIdCore) + Hex.toHexString(checksum);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(tokenIdCore);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TokenId)) {
            return false;
        }
        return Arrays.equals(tokenIdCore, ((TokenId) o).getBytes());
    }

    public static TokenId stringToTokenId(String s) {
        return StringUtils.isEmpty(s) ? null : new TokenId(s);
    }

    public static boolean isValid(String tokenId) {
        if (StringUtils.isEmpty(tokenId) || tokenId.length() != 28) {
            return false;
        }
        if (!tokenId.startsWith("tti_")) {
            return false;
        }
        byte[] tokenIdCore = Hex.decode(tokenId.substring(4, 24));
        byte[] checkSum = Hex.decode(tokenId.substring(24, 28));
        return Arrays.equals(checkSum, getCheckSum(tokenIdCore));
    }

    public static byte[] getCheckSum(byte[] tokenIdCore) {
        return Crypto.digest(2, tokenIdCore);
    }
}
