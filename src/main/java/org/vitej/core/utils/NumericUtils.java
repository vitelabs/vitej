package org.vitej.core.utils;

import org.apache.commons.lang3.StringUtils;

import java.math.BigInteger;

public final class NumericUtils {
    /**
     * Convert string value to BigInteger
     *
     * @param value String value
     * @return BigInteger value, return null if input is null
     */
    public static BigInteger stringToBigInteger(String value) {
        return StringUtils.isEmpty(value) ? null : new BigInteger(value, 10);
    }

    /**
     * Convert hexadecimal string value to BigInteger
     *
     * @param value Hexadecimal string value
     * @return BigInteger value, return null if input is null
     */
    public static BigInteger hexStringToBigInteger(String value) {
        return StringUtils.isEmpty(value) ? null : new BigInteger(value, 16);
    }

    /**
     * Convert string value to long
     *
     * @param value string value
     * @return long value
     */
    public static Long stringToLong(String value) {
        return StringUtils.isEmpty(value) ? null : Long.parseLong(value);
    }

    /**
     * Convert long value to string
     *
     * @param value Long value
     * @return String value
     */
    public static String longToString(Long value) {
        return value.toString();
    }
}
