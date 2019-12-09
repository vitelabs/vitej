package org.vitej.core.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.util.encoders.Hex;

import java.math.BigInteger;
import java.util.Arrays;

public class BytesUtils {
    private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];

    /**
     * Convert hexadecimal string value to byte array
     *
     * @param data Hexadecimal string value
     * @return Byte array value
     */
    public static byte[] hexStringToBytes(String data) {
        if (data == null) return EMPTY_BYTE_ARRAY;
        if (data.startsWith("0x")) data = data.substring(2);
        if (data.length() % 2 == 1) data = "0" + data;
        return Hex.decode(data);
    }

    /**
     * Convert byte array to hexadecimal string value
     *
     * @param data Byte array value
     * @return Hexadecimal string value
     */
    public static String bytesToHexString(byte[] data) {
        return data == null ? "" : Hex.toHexString(data);
    }

    /**
     * Convert unsigned BigInteger value to byte array
     *
     * @param b        BigInteger value
     * @param numBytes Byte array length, left padding with 0
     * @return Byte array value
     */
    public static byte[] bigIntegerToBytes(BigInteger b, int numBytes) {
        if (b == null)
            return null;
        byte[] bytes = new byte[numBytes];
        byte[] biBytes = b.toByteArray();
        int start = (biBytes.length == numBytes + 1) ? 1 : 0;
        int length = Math.min(biBytes.length, numBytes);
        System.arraycopy(biBytes, start, bytes, numBytes - length, length);
        return bytes;
    }

    /**
     * Convert signed BigInteger value to byte array
     *
     * @param b        BigInteger value
     * @param numBytes Byte array length, left padding with 0 or FF according to signum of b
     * @return Byte array value
     */
    public static byte[] bigIntegerToBytesSigned(BigInteger b, int numBytes) {
        if (b == null)
            return null;
        byte[] bytes = new byte[numBytes];
        Arrays.fill(bytes, b.signum() < 0 ? (byte) 0xFF : 0x00);
        byte[] biBytes = b.toByteArray();
        int start = (biBytes.length == numBytes + 1) ? 1 : 0;
        int length = Math.min(biBytes.length, numBytes);
        System.arraycopy(biBytes, start, bytes, numBytes - length, length);
        return bytes;
    }

    /**
     * Convert byte array value to BigInteger
     *
     * @param bb Byte array value
     * @return BigInteger value
     */
    public static BigInteger bytesToBigInteger(byte[] bb) {
        return (bb == null || bb.length == 0) ? BigInteger.ZERO : new BigInteger(1, bb);
    }

    /**
     * Merge byte arrays
     *
     * @param arrays Input byte arrays
     * @return Merged byte arrays
     */
    public static byte[] merge(byte[]... arrays) {
        int count = 0;
        for (byte[] array : arrays) {
            count += array.length;
        }

        byte[] mergedArray = new byte[count];
        int start = 0;
        for (byte[] array : arrays) {
            System.arraycopy(array, 0, mergedArray, start, array.length);
            start += array.length;
        }
        return mergedArray;
    }

    /**
     * Return negation of input byte array
     *
     * @param data Input byte array
     * @return Negation of input byte array
     */
    public static byte[] negation(byte[] data) {
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) (~data[i]);
        }
        return data;
    }

    /**
     * Convert integer value to byte array of length 4
     *
     * @param integer Integer value
     * @return Byte array
     */
    public static byte[] intToBytes(int integer) {
        byte[] bytes = new byte[4];
        bytes[0] = (byte) (integer >> 24);
        bytes[1] = (byte) (integer >> 16);
        bytes[2] = (byte) (integer >> 8);
        bytes[3] = (byte) integer;
        return bytes;
    }

    /**
     * Convert long value to byte array of length 8
     *
     * @param longValue Long value
     * @return Byte array
     */
    public static byte[] longToBytes(long longValue) {
        byte[] buffer = new byte[8];
        for (int i = 0; i < 8; i++) {
            int offset = 64 - (i + 1) * 8;
            buffer[i] = (byte) ((longValue >> offset) & 0xff);
        }
        return buffer;
    }

    /**
     * Convert base64 string to byte array
     *
     * @param base64Str Base64 string value
     * @return Byte array
     */
    public static byte[] base64ToBytes(String base64Str) {
        return StringUtils.isEmpty(base64Str) ? null : Base64.decodeBase64(base64Str);
    }

    /**
     * Convert byte array value to base64 string
     *
     * @param bytes Byte array value
     * @return Base64 string value
     */
    public static String bytesToBase64(byte[] bytes) {
        return Base64.encodeBase64String(bytes);
    }

    /**
     * Left pad byte array to target size with zero.
     * If input byte array is equal to or over than target size, return the original input
     *
     * @param bytes Byte array to be left padded
     * @param size  Target array size
     * @return Byte array of target size
     */
    public static byte[] leftPadBytes(byte[] bytes, int size) {
        if (bytes.length >= size) {
            return bytes;
        }
        byte[] result = new byte[size];
        System.arraycopy(bytes, 0, result, size - bytes.length, bytes.length);
        return result;
    }
}
