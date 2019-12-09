package org.vitej.core.protocol.methods;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.util.encoders.Hex;
import org.vitej.core.utils.BytesUtils;
import org.vitej.core.wallet.Crypto;

import java.util.Arrays;

/**
 * Account address
 *
 * @see <a href="https://mainnet.vite.wiki/zh/vep/vep-16.html">https://mainnet.vite.wiki/zh/vep/vep-16.html</a>
 */
public class Address {
    private static final int contractType = 1;
    private static final int userType = 0;
    private byte[] addressCore;
    private int type;
    private byte[] checksum;

    /**
     * Create address instance
     *
     * @param addressCore Address core, for example: ab24ef68b84e642c0ddca06beec81c9acb1977bb
     * @param type        Address type, 0 for user account, 1 for contract account
     * @param checksum    Checksum, for example: d7da27a87a
     */
    public Address(byte[] addressCore, int type, byte[] checksum) {
        this.addressCore = addressCore;
        this.type = type;
        this.checksum = checksum;
    }

    /**
     * Create address instance
     *
     * @param addressCore Address core, for example: ab24ef68b84e642c0ddca06beec81c9acb1977bb
     * @param type        Address type, 0 for user account, 1 for contract account
     */
    public Address(byte[] addressCore, int type) {
        this.addressCore = addressCore;
        this.type = type;
        this.checksum = getCheckSum(addressCore, type);
    }

    /**
     * Create address instance and check whether the address is valid
     *
     * @param address String type address, for example: vite_ab24ef68b84e642c0ddca06beec81c9acb1977bbd7da27a87a
     */
    public Address(String address) {
        Preconditions.checkArgument(isValid(address), "Invalid address");
        this.addressCore = Hex.decode(address.substring(5, 45));
        this.checksum = Hex.decode(address.substring(45, 55));
        this.type = Arrays.equals(this.checksum, getCheckSum(addressCore, 0)) ? 0 : 1;
    }

    /**
     * Create address instance
     *
     * @param bytes Byte array type of address, for example: ab24ef68b84e642c0ddca06beec81c9acb1977bb00
     */
    public Address(byte[] bytes) {
        Preconditions.checkArgument(bytes.length == 21);
        this.addressCore = new byte[20];
        System.arraycopy(bytes, 0, this.addressCore, 0, 20);
        this.type = bytes[20];
        this.checksum = getCheckSum(addressCore, type);
    }

    /**
     * Return address type
     *
     * @return Address type, 0 for user account, 1 for contract account
     */
    public int getType() {
        return type;
    }

    /**
     * Return whether the address is for contract
     *
     * @return True for contract address, false for user address
     */
    public boolean isContract() {
        return type == contractType;
    }

    /**
     * Return whether the address is for user
     *
     * @return true for user address, false for contract address
     */
    public boolean isUser() {
        return type == userType;
    }

    /**
     * return byte array type of address
     *
     * @return Byte array type of address
     */
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

    /**
     * Return whether an address is valid
     *
     * @param address String type address, for example: vite_ab24ef68b84e642c0ddca06beec81c9acb1977bbd7da27a87a
     * @return True for valid, false for invalid
     */
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

    /**
     * Calculate checksum for an address
     *
     * @param addressCore Address core, for example: ab24ef68b84e642c0ddca06beec81c9acb1977bb
     * @param type        Address type, 0 for user account, 1 for contract account
     * @return Checksum, for example: d7da27a87a
     */
    public static byte[] getCheckSum(byte[] addressCore, int type) {
        byte[] checkSum = Crypto.digest(5, addressCore);
        return type == contractType ? BytesUtils.negation(checkSum) : checkSum;
    }

    /**
     * Create address instance
     *
     * @param s String type address, for example: vite_ab24ef68b84e642c0ddca06beec81c9acb1977bbd7da27a87a
     * @return If input is null, return null, else return address instance
     */
    public static Address stringToAddress(String s) {
        return StringUtils.isEmpty(s) ? null : new Address(s);
    }

    /**
     * Generate user account address by public key
     *
     * @param pubKey Public key
     * @return Account address
     */
    public static Address publicKeyToAddress(byte[] pubKey) {
        return new Address(Crypto.digest(20, pubKey), 0);
    }
}
