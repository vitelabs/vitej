package org.vitej.core.protocol.methods;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.util.encoders.Hex;
import org.vitej.core.wallet.Crypto;

import java.util.Arrays;

/**
 * 代币id
 */
public class TokenId {
    private byte[] tokenIdCore;
    private byte[] checksum;

    /**
     * 生成代币id对象
     *
     * @param tokenIdCore 核心代币id，例如5649544520544f4b454e
     * @param checksum    校验码，例如6e40
     */
    public TokenId(byte[] tokenIdCore, byte[] checksum) {
        this.tokenIdCore = tokenIdCore;
        this.checksum = checksum;
    }

    /**
     * 生成代币id对象
     *
     * @param tokenId string类型的代币id，例如tti_5649544520544f4b454e6e40
     */
    public TokenId(String tokenId) {
        Preconditions.checkArgument(isValid(tokenId), "Invalid tokenId");
        this.tokenIdCore = Hex.decode(tokenId.substring(4, 24));
        this.checksum = Hex.decode(tokenId.substring(24, 28));
    }

    /**
     * 生成代币id对象，并计算校验码
     *
     * @param tokenIdCore 核心代币id，例如5649544520544f4b454e
     */
    public TokenId(byte[] tokenIdCore) {
        this.tokenIdCore = tokenIdCore;
        this.checksum = getCheckSum(tokenIdCore);
    }

    /**
     * 获取核心代币id
     *
     * @return 核心代币id，例如5649544520544f4b454e
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
     * 生成代币id对象
     *
     * @param s string类型的代币id，例如tti_5649544520544f4b454e6e40
     * @return 如果入参为空，则返回空，否则返回代币id对象
     */
    public static TokenId stringToTokenId(String s) {
        return StringUtils.isEmpty(s) ? null : new TokenId(s);
    }

    /**
     * 判断string类型的代币id是否合法
     *
     * @param tokenId string类型的代币id，例如tti_5649544520544f4b454e6e40
     * @return true-合法，false-不合法
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
     * 生成校验码
     *
     * @param tokenIdCore 核心代币id，长度为10的字节数组
     * @return 校验码，长度为2的字节数组
     */
    public static byte[] getCheckSum(byte[] tokenIdCore) {
        return Crypto.digest(2, tokenIdCore);
    }
}
