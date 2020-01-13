package org.vitej.core.utils;

import java.math.BigDecimal;
import java.math.BigInteger;

public final class CommonUtils {
    private static int VITE_DECIMALS = 18;
    private static BigDecimal VITE_DECIMALS_DIVISION = new BigDecimal(10).pow(VITE_DECIMALS);

    public static BigInteger fromViteAmount(BigDecimal viteAmount) {
        return viteAmount.multiply(VITE_DECIMALS_DIVISION).toBigInteger();
    }

    public static BigDecimal toViteAmount(BigInteger amount) {
        return new BigDecimal(amount).divide(VITE_DECIMALS_DIVISION, VITE_DECIMALS, BigDecimal.ROUND_HALF_DOWN);
    }
}
