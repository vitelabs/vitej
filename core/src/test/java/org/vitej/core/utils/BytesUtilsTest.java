package org.vitej.core.utils;

import org.junit.Assert;
import org.junit.Test;

public class BytesUtilsTest {
    @Test
    public void testLeftPadBytes() {
        byte[] input = {1, 2, 3};
        byte[] output = {0, 0, 0, 0, 0, 1, 2, 3};
        byte[] got = BytesUtils.leftPadBytes(input, 8);
        Assert.assertArrayEquals(output, got);
    }
}
