package org.vitej.core.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.util.encoders.Hex;

import java.math.BigInteger;
import java.util.Arrays;

public class BytesUtils {
    private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];

    public static byte[] hexStringToBytes(String data) {
        if (data == null) return EMPTY_BYTE_ARRAY;
        if (data.startsWith("0x")) data = data.substring(2);
        if (data.length() % 2 == 1) data = "0" + data;
        return Hex.decode(data);
    }

    public static String bytesToHexString(byte[] data) {
        return data == null ? "" : Hex.toHexString(data);
    }

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

    public static BigInteger bytesToBigInteger(byte[] bb) {
        return (bb == null || bb.length == 0) ? BigInteger.ZERO : new BigInteger(1, bb);
    }

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

    public static byte[] negation(byte[] data) {
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) (~data[i]);
        }
        return data;
    }

    public static byte[] intToBytes(int integer) {
        byte[] bytes = new byte[4];
        bytes[0] = (byte) (integer >> 24);
        bytes[1] = (byte) (integer >> 16);
        bytes[2] = (byte) (integer >> 8);
        bytes[3] = (byte) integer;
        return bytes;
    }

    public static byte[] longToBytes(long longValue) {
        byte[] buffer = new byte[8];
        for (int i = 0; i < 8; i++) {
            int offset = 64 - (i + 1) * 8;
            buffer[i] = (byte) ((longValue >> offset) & 0xff);
        }
        return buffer;
    }

    public static byte[] base64ToBytes(String base64Str) {
        return StringUtils.isEmpty(base64Str) ? null : Base64.decodeBase64(base64Str);
    }

    public static String bytesToBase64(byte[] bytes) {
        return Base64.encodeBase64String(bytes);
    }

    public static byte[] leftPadBytes(byte[] bytes, int size) {
        byte[] result = new byte[size];
        System.arraycopy(bytes, 0, result, size - bytes.length, bytes.length);
        return result;
    }
}
