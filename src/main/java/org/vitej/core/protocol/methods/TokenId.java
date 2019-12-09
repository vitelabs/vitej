package org.vitej.core.protocol.methods;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.util.encoders.Hex;
import org.vitej.core.wallet.Crypto;

import java.util.Arrays;

/**
 * Token id
 */
public class TokenId {
    private byte[] tokenIdCore;
    private byte[] checksum;

    /**
     * Create TokenId instance
     *
     * @param tokenIdCore Token id core, for example: 5649544520544f4b454e
     * @param checksum    Token id checksum, for example: 6e40
     */
    public TokenId(byte[] tokenIdCore, byte[] checksum) {
        this.tokenIdCore = tokenIdCore;
        this.checksum = checksum;
    }

    /**
     * Create TokenId instance
     *
     * @param tokenId String type of token id, for example: tti_5649544520544f4b454e6e40
     */
    public TokenId(String tokenId) {
        Preconditions.checkArgument(isValid(tokenId), "Invalid tokenId");
        this.tokenIdCore = Hex.decode(tokenId.substring(4, 24));
        this.checksum = Hex.decode(tokenId.substring(24, 28));
    }

    /**
     * Create TokenId instance
     *
     * @param tokenIdCore Token id core, for example: 5649544520544f4b454e
     */
    public TokenId(byte[] tokenIdCore) {
        this.tokenIdCore = tokenIdCore;
        this.checksum = getCheckSum(tokenIdCore);
    }

    /**
     * Convert token id to byte array
     *
     * @return Byte array token id core, for example: 5649544520544f4b454e
     */
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

    /**
     * Create TokenId instance
     *
     * @param s String type token id, for example: tti_5649544520544f4b454e6e40
     * @return If input is null, return null, else return tokenId
     */
    public static TokenId stringToTokenId(String s) {
        return StringUtils.isEmpty(s) ? null : new TokenId(s);
    }

    /**
     * Return whether a token id is valid
     *
     * @param tokenId String type token id
     * @return True for valid token id, false for invalid token id
     */
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

    /**
     * Calculate token if checksum
     *
     * @param tokenIdCore Token id core
     * @return Token id checksum
     */
    public static byte[] getCheckSum(byte[] tokenIdCore) {
        return Crypto.digest(2, tokenIdCore);
    }
}
