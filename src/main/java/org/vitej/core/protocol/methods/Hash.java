package org.vitej.core.protocol.methods;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.util.encoders.Hex;
import org.vitej.core.wallet.Crypto;

import java.util.Arrays;

/**
 * 哈希
 */
public class Hash {
    private byte[] hash;

    /**
     * 生成哈希对象
     *
     * @param hash 字节数组类型的哈希，长度为32字节，例如d517e8d4dc9c676876b72ad0cbb4c45890804aa438edd1f171ffc66276202a95
     */
    public Hash(byte[] hash) {
        Preconditions.checkArgument(hash.length == 32);
        this.hash = hash;
    }

    /**
     * 生成哈希对象
     *
     * @param s 十六进制string类型的哈希，长度为32字节，例如d517e8d4dc9c676876b72ad0cbb4c45890804aa438edd1f171ffc66276202a95
     */
    public Hash(String s) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(s) && s.length() == 64);
        this.hash = Hex.decode(s);
    }

    /**
     * 获取字节数组类型的哈希
     *
     * @return 字节数组类型的哈希
     */
    public byte[] getBytes() {
        return hash;
    }

    @Override
    public String toString() {
        return Hex.toHexString(hash);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(hash);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Hash)) {
            return false;
        }
        return Arrays.equals(hash, ((Hash) o).getBytes());
    }

    /**
     * 生成哈希对象
     *
     * @param s 十六进制string类型的哈希，长度为32字节，d517e8d4dc9c676876b72ad0cbb4c45890804aa438edd1f171ffc66276202a95
     * @return 如果入参为空，则返回值为空，否则返回相应的哈希对象
     */
    public static Hash stringToHash(String s) {
        return StringUtils.isEmpty(s) ? null : new Hash(s);
    }

    /**
     * 计算字节数组对应的32位blake2b哈希
     *
     * @param byteArrays 数据内容
     * @return 哈希
     */
    public static Hash dataToHash(byte[]... byteArrays) {
        Preconditions.checkNotNull(byteArrays);
        return new Hash(Crypto.digest(32, byteArrays));
    }
}
