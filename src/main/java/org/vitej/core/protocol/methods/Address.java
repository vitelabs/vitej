package org.vitej.core.protocol.methods;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.util.encoders.Hex;
import org.vitej.core.utils.BytesUtils;
import org.vitej.core.wallet.Crypto;

import java.util.Arrays;

/**
 * 账户地址
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
     * 生成账户地址对象
     *
     * @param addressCore 核心账户地址，例如ab24ef68b84e642c0ddca06beec81c9acb1977bb
     * @param type        账户类型，0-用户地址，1-合约地址
     * @param checksum    校验码，例如d7da27a87a
     */
    public Address(byte[] addressCore, int type, byte[] checksum) {
        this.addressCore = addressCore;
        this.type = type;
        this.checksum = checksum;
    }

    /**
     * 生成账户地址对象，根据账户类型自动生成校验码
     *
     * @param addressCore 核心账户地址，例如ab24ef68b84e642c0ddca06beec81c9acb1977bb
     * @param type        账户类型，0-用户地址，1-合约地址
     */
    public Address(byte[] addressCore, int type) {
        this.addressCore = addressCore;
        this.type = type;
        this.checksum = getCheckSum(addressCore, type);
    }

    /**
     * 生成账户地址对象，并校验有效性
     *
     * @param address 账户地址，例如vite_ab24ef68b84e642c0ddca06beec81c9acb1977bbd7da27a87a
     */
    public Address(String address) {
        Preconditions.checkArgument(isValid(address), "Invalid address");
        this.addressCore = Hex.decode(address.substring(5, 45));
        this.checksum = Hex.decode(address.substring(45, 55));
        this.type = Arrays.equals(this.checksum, getCheckSum(addressCore, 0)) ? 0 : 1;
    }

    /**
     * 生成账户地址对象
     *
     * @param bytes 区块链上使用的21字节账户地址，例如ab24ef68b84e642c0ddca06beec81c9acb1977bb00
     */
    public Address(byte[] bytes) {
        Preconditions.checkArgument(bytes.length == 21);
        this.addressCore = new byte[20];
        System.arraycopy(bytes, 0, this.addressCore, 0, 20);
        this.type = bytes[20];
        this.checksum = getCheckSum(addressCore, type);
    }

    /**
     * 获取账户类型
     *
     * @return 账户类型，0-用户地址，1-合约地址
     */
    public int getType() {
        return type;
    }

    /**
     * 判断是否合约账户
     *
     * @return true-合约账户，false-用户账户
     */
    public boolean isContract() {
        return type == contractType;
    }

    /**
     * 判断是否用户账户
     *
     * @return true-用户账户，false-合约账户
     */
    public boolean isUser() {
        return type == userType;
    }

    /**
     * 获取21字节的账户地址
     *
     * @return 21字节的账户地址，例如ab24ef68b84e642c0ddca06beec81c9acb1977bb00
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
     * 判断一个string类型的地址是否合法
     *
     * @param address string类型的地址，例如vite_ab24ef68b84e642c0ddca06beec81c9acb1977bbd7da27a87a
     * @return true-合法，false-不合法
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
     * 生成校验码
     *
     * @param addressCore 核心账户地址，例如ab24ef68b84e642c0ddca06beec81c9acb1977bb
     * @param type        账户类型，0-用户地址，1-合约地址
     * @return 校验码，例如d7da27a87a
     */
    public static byte[] getCheckSum(byte[] addressCore, int type) {
        byte[] checkSum = Crypto.digest(5, addressCore);
        return type == contractType ? BytesUtils.negation(checkSum) : checkSum;
    }

    /**
     * 生成账户地址对象
     *
     * @param s string类型的地址，例如vite_ab24ef68b84e642c0ddca06beec81c9acb1977bbd7da27a87a
     * @return 如果入参为空，返回空，否则返回地址
     */
    public static Address stringToAddress(String s) {
        return StringUtils.isEmpty(s) ? null : new Address(s);
    }

    /**
     * 根据公钥生成账户地址对象
     *
     * @param pubKey 公钥
     * @return 公钥对应的用户地址
     */
    public static Address publicKeyToAddress(byte[] pubKey) {
        return new Address(Crypto.digest(20, pubKey), 0);
    }
}
