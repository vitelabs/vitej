package org.vitej.core.wallet;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Charsets;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.crypto.generators.SCrypt;
import org.vitej.core.utils.BytesUtils;
import org.vitej.core.utils.SecureRandomUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class EntropyFile {
    private static final int StandardScryptN = 1 << 18;
    private static final int StandardScryptP = 1;
    private static final int ScryptKeyLen = 32;
    private static final int ScryptR = 8;
    private static final String AesMode = "aes-256-gcm";
    private static final String ScryptName = "scrypt";
    private static final int CryptoStoreVersion = 1;

    private static final String gcmAdditionData = "vite";

    public static void saveToFile(String address, byte[] entropy, String filename, String password) throws IOException {
        File file = new File(filename);
        if (file.exists()) {
            throw new RuntimeException(filename + " exists");
        }

        if (!file.createNewFile()) {
            throw new RuntimeException(String.format("create file[%s] fail.", filename));
        }
        FileWriter writer = new FileWriter(file);
        EntropyJSON json = entropy(address, entropy, password.getBytes(Charsets.UTF_8));
        try {
            writer.write(JSON.toJSONString(json));
        } finally {
            writer.close();
        }
    }

    public static byte[] loadFromFile(String filename, String password) throws IOException {
        File file = new File(filename);
        if (!file.exists()) {
            throw new RuntimeException(filename + "is not exists");
        }
        EntropyJSON json = JSON.parseObject(new String(Files.readAllBytes(file.toPath())), EntropyJSON.class);

        try {
            return decrypt(json, password.getBytes(Charsets.UTF_8));
        } catch (DecoderException e) {
            throw new RuntimeException(e);
        }
    }


    private static EntropyJSON entropy(String address, byte[] entropy, byte[] password) {
        byte[] salt = SecureRandomUtils.randomBytes(32);

        byte[] derivedKey = SCrypt.generate(password, salt, StandardScryptN, ScryptR, StandardScryptP, ScryptKeyLen);

        byte[] iv = SecureRandomUtils.randomBytes(12);

        byte[] encryptKey = Arrays.copyOfRange(derivedKey, 0, 32);

//        byte[] cipherText = performCipherOperation(Cipher.ENCRYPT_MODE, iv, encryptKey, BytesUtils.leftPadBytes(entropy, 32));
        byte[] cipherText = performCipherOperation(Cipher.ENCRYPT_MODE, iv, encryptKey, entropy);

        ScryptParams scryptParams = new ScryptParams()
                .setN(StandardScryptN)
                .setR(ScryptR)
                .setP(StandardScryptP)
                .setKeylen(ScryptKeyLen)
                .setSalt(Hex.encodeHexString(salt));

        CryptoJSON crypto = new CryptoJSON()
                .setCiphername(AesMode)
                .setCipherText(Hex.encodeHexString(cipherText))
                .setNonce(Hex.encodeHexString(iv))
                .setKdf(ScryptName)
                .setScryptParams(scryptParams);

        EntropyJSON encryptJson = new EntropyJSON()
                .setPrimaryAddress(address)
                .setCrypto(crypto)
                .setSeedstoreversion(CryptoStoreVersion)
                .setTimestamp(System.currentTimeMillis() / 1000);

        return encryptJson;
    }

    public static byte[] decrypt(EntropyJSON json, byte[] password) throws IOException, DecoderException {
        byte[] salt = Hex.decodeHex(json.crypto.scryptParams.salt);

        byte[] derivedKey = SCrypt.generate(password, salt, StandardScryptN, ScryptR, StandardScryptP, ScryptKeyLen);

        byte[] encryptKey = Arrays.copyOfRange(derivedKey, 0, 32);

        byte[] nonce = Hex.decodeHex(json.crypto.nonce);

        byte[] cipherText = Hex.decodeHex(json.crypto.cipherText);

        byte[] entropy = performCipherOperation(Cipher.DECRYPT_MODE, nonce, encryptKey, cipherText);
        return entropy;
    }

    /**
     * 
     * @param mode
     * @param iv
     * @param encryptKey
     * @param text
     * @return
     * copy from https://github.com/web3j/web3j/blob/357b0213f2e5939bd87cf832c8ae2483b23d979d/core/src/main/java/org/web3j/crypto/Wallet.java#L163
     */
    private static byte[] performCipherOperation(int mode, byte[] iv, byte[] encryptKey, byte[] text) {
        try {
            // IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            GCMParameterSpec ivParameterSpec = new GCMParameterSpec(128, iv);
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");

            SecretKeySpec secretKeySpec = new SecretKeySpec(encryptKey, "AES");
            cipher.init(mode, secretKeySpec, ivParameterSpec);
            cipher.updateAAD(gcmAdditionData.getBytes(Charsets.UTF_8));
            return cipher.doFinal(text);
        } catch (NoSuchPaddingException
                | NoSuchAlgorithmException
                | InvalidAlgorithmParameterException
                | InvalidKeyException
                | BadPaddingException
                | IllegalBlockSizeException e) {
            throw new RuntimeException("Error performing cipher operation", e);
        }
    }

    @Data
    @Accessors(chain = true)
    static class EntropyJSON {
        private String primaryAddress;
        private CryptoJSON crypto;
        private int seedstoreversion;
        private long timestamp;
    }


    @Data
    @Accessors(chain = true)
    static class CryptoJSON {
        private String ciphername;
        private String cipherText;
        private String nonce;
        private String kdf;
        private ScryptParams scryptParams;
    }


    @Data
    @Accessors(chain = true)
    static class ScryptParams {
        private int n;
        private int r;
        private int p;
        private int keylen;
        private String salt;
    }
}
