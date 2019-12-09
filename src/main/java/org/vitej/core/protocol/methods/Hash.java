package org.vitej.core.protocol.methods;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.util.encoders.Hex;
import org.vitej.core.wallet.Crypto;

import java.util.Arrays;

/**
 * 32 byte length blake2b hash in Vite
 */
public class Hash {
    private byte[] hash;

    /**
     * Create hash instance
     *
     * @param hash Byte array type hash
     */
    public Hash(byte[] hash) {
        Preconditions.checkArgument(hash.length == 32);
        this.hash = hash;
    }

    /**
     * Create hash instance
     *
     * @param s Hexadecimal format string type hash
     */
    public Hash(String s) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(s) && s.length() == 64);
        this.hash = Hex.decode(s);
    }

    /**
     * Return byte array type hash
     *
     * @return Byte array type hash
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
     * Create hash instance
     *
     * @param s Hexadecimal format string type hash
     * @return If input is null, return null, else return hash instance
     */
    public static Hash stringToHash(String s) {
        return StringUtils.isEmpty(s) ? null : new Hash(s);
    }

    /**
     * Calculate 32 byte length blake2b hash of input data
     *
     * @param byteArrays input data
     * @return hash
     */
    public static Hash dataToHash(byte[]... byteArrays) {
        Preconditions.checkNotNull(byteArrays);
        return new Hash(Crypto.digest(32, byteArrays));
    }
}
