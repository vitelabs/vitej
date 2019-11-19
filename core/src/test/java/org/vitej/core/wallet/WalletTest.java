package org.vitej.core.wallet;

import org.junit.Assert;
import org.junit.Test;
import org.vitej.core.protocol.methods.Address;
import org.vitej.core.utils.BytesUtils;

import java.util.Arrays;
import java.util.List;

public class WalletTest {
    @Test
    public void testNewWallet() {
        try {
            Wallet w = new Wallet();
            Assert.assertNotNull(w);
            KeyPair keyPairDefault = w.deriveKeyPair();
            Assert.assertNotNull(keyPairDefault);
            Assert.assertTrue(Address.isValid(keyPairDefault.getAddress().toString()));
            Assert.assertTrue(keyPairDefault.getPrivateKey().length > 0);
            Assert.assertTrue(keyPairDefault.getPublicKey().length > 0);
            KeyPair keyPair0 = w.deriveKeyPair(0);
            Assert.assertEquals(keyPair0.getAddress().toString(), keyPairDefault.getAddress().toString());
            KeyPair keyPair1 = w.deriveKeyPair(1);
            Assert.assertNotEquals(keyPair0.getAddress().toString(), keyPair1.getAddress().toString());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Got exception");
        }
    }

    @Test
    public void testNewWalletFromMnemonic() {
        try {
            List<String> mnemonic = Arrays.asList("alarm", "canal", "scheme", "actor", "left", "length", "bracket", "slush", "tuna", "garage", "prepare", "scout", "school", "pizza", "invest", "rose", "fork", "scorpion", "make", "enact", "false", "kidney", "mixed", "vast");
            Wallet w = new Wallet(mnemonic);
            Assert.assertNotNull(w);
            KeyPair keyPairDefault = w.deriveKeyPair(1);
            Assert.assertNotNull(keyPairDefault);
            Assert.assertEquals("vite_ab24ef68b84e642c0ddca06beec81c9acb1977bbd7da27a87a", keyPairDefault.getAddress().toString());
            Assert.assertEquals("9e3e18877f0d398fbbf4d7b350da6a1d9dd9d420e06a3d9f37338c0d6c1c9dc6", BytesUtils.bytesToHexString(keyPairDefault.getPrivateKey()));
            Assert.assertEquals("5876629f1b25b1c13e59a22aad48c6bb6b1c3afa2b803e10f8340e39c0c1bf83", BytesUtils.bytesToHexString(keyPairDefault.getPublicKey()));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Got exception");
        }
    }

    @Test
    public void testNewKeyPairFromPrivateKey() {
        try {
            KeyPair keyPair = new KeyPair(BytesUtils.hexStringToBytes("9e3e18877f0d398fbbf4d7b350da6a1d9dd9d420e06a3d9f37338c0d6c1c9dc6"));
            Assert.assertEquals("vite_ab24ef68b84e642c0ddca06beec81c9acb1977bbd7da27a87a", keyPair.getAddress().toString());
            Assert.assertEquals("9e3e18877f0d398fbbf4d7b350da6a1d9dd9d420e06a3d9f37338c0d6c1c9dc6", BytesUtils.bytesToHexString(keyPair.getPrivateKey()));
            Assert.assertEquals("5876629f1b25b1c13e59a22aad48c6bb6b1c3afa2b803e10f8340e39c0c1bf83", BytesUtils.bytesToHexString(keyPair.getPublicKey()));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Got exception");
        }
    }

    @Test
    public void testSign() {
        try {
            List<String> mnemonic = Arrays.asList("alarm", "canal", "scheme", "actor", "left", "length", "bracket", "slush", "tuna", "garage", "prepare", "scout", "school", "pizza", "invest", "rose", "fork", "scorpion", "make", "enact", "false", "kidney", "mixed", "vast");
            KeyPair keyPair = new Wallet(mnemonic).deriveKeyPair(1);
            byte[] signResult = keyPair.sign(BytesUtils.hexStringToBytes("889868f0ed64c6d6d9f98a7fa4b45931602406a5143d210c2e669bdb547332ce"));
            Assert.assertEquals("1c240cc3c00bcad3eb815fcfbf18970caff1d7a45a9bb0b1b7bb301146870ca311069e5906a545d3ec0824d165579818e5d2f6f20440341f89851f692ad81a04", BytesUtils.bytesToHexString(signResult));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Got exception");
        }
    }

    @Test
    public void testVerify() {
        try {
            List<String> mnemonic = Arrays.asList("alarm", "canal", "scheme", "actor", "left", "length", "bracket", "slush", "tuna", "garage", "prepare", "scout", "school", "pizza", "invest", "rose", "fork", "scorpion", "make", "enact", "false", "kidney", "mixed", "vast");
            KeyPair keyPair = new Wallet(mnemonic).deriveKeyPair(1);
            String signData = "889868f0ed64c6d6d9f98a7fa4b45931602406a5143d210c2e669bdb547332ce";
            String signResult = "1c240cc3c00bcad3eb815fcfbf18970caff1d7a45a9bb0b1b7bb301146870ca311069e5906a545d3ec0824d165579818e5d2f6f20440341f89851f692ad81a04";
            Assert.assertTrue(Crypto.verify(BytesUtils.hexStringToBytes(signResult), BytesUtils.hexStringToBytes(signData), keyPair.getPublicKey()));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Got exception");
        }
    }
}
