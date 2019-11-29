package org.vitej.core.utils;

import org.apache.commons.lang3.StringUtils;

import java.math.BigInteger;

public class NumericUtils {
    public static BigInteger stringToBigInteger(String value) {
        return StringUtils.isEmpty(value) ? null : new BigInteger(value, 10);
    }

    public static BigInteger hexStringToBigInteger(String value) {
        return StringUtils.isEmpty(value) ? null : new BigInteger(value, 16);
    }

    public static Long stringToLong(String value) {
        return StringUtils.isEmpty(value) ? null : Long.parseLong(value);
    }

    public static String longToString(Long value) {
        return value.toString();
    }
}
