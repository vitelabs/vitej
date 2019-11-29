package org.vitej.core.protocol.methods;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.util.encoders.Hex;
import org.vitej.core.utils.BytesUtils;
import org.vitej.core.wallet.Crypto;

import java.util.Arrays;

public class Address {
    private static final int contractType = 1;
    private static final int userType = 0;
    private byte[] addressCore;
    private int type; // 0 - user address, 1 - contract address
    private byte[] checksum;

    public Address(byte[] addressCore, int type, byte[] checksum) {
        this.addressCore = addressCore;
        this.type = type;
        this.checksum = checksum;
    }

    public Address(byte[] addressCore, int type) {
        this.addressCore = addressCore;
        this.type = type;
        this.checksum = getCheckSum(addressCore, type);
    }

    public Address(String address) {
        Preconditions.checkArgument(isValid(address), "Invalid address");
        this.addressCore = Hex.decode(address.substring(5, 45));
        this.checksum = Hex.decode(address.substring(45, 55));
        this.type = Arrays.equals(this.checksum, getCheckSum(addressCore, 0)) ? 0 : 1;
    }

    public Address(byte[] bytes) {
        Preconditions.checkArgument(bytes.length == 21);
        this.addressCore = new byte[20];
        System.arraycopy(bytes, 0, this.addressCore, 0, 20);
        this.type = bytes[20];
        this.checksum = getCheckSum(addressCore, type);
    }

    public int getType() {
        return type;
    }

    public boolean isContract() {
        return type == contractType;
    }

    public boolean isUser() {
        return type == userType;
    }

    public byte[] getBytes() {
        return BytesUtils.merge(addressCore, new byte[]{(byte) type});
    }

    @Override
    public String toString() {
        return "vite_" + Hex.toHexString(addressCore) + Hex.toHexString(checksum);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(addressCore);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Address)) {
            return false;
        }
        return Arrays.equals(getBytes(), ((Address) o).getBytes());
    }

    public static boolean isValid(String address) {
        if (StringUtils.isEmpty(address) || address.length() != 55) {
            return false;
        }
        if (!address.startsWith("vite_")) {
            return false;
        }
        byte[] addressCore = Hex.decode(address.substring(5, 45));
        byte[] checkSum = Hex.decode(address.substring(45, 55));
        return Arrays.equals(checkSum, getCheckSum(addressCore, 0))
                || Arrays.equals(checkSum, getCheckSum(addressCore, 1));
    }

    public static byte[] getCheckSum(byte[] addressCore, int type) {
        byte[] checkSum = Crypto.digest(5, addressCore);
        return type == contractType ? BytesUtils.negation(checkSum) : checkSum;
    }

    public static Address stringToAddress(String s) {
        return StringUtils.isEmpty(s) ? null : new Address(s);
    }

    public static Address publicKeyToAddress(byte[] pubKey) {
        return new Address(Crypto.digest(20, pubKey), 0);
    }
}
