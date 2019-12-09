package org.vitej.core.wallet;

import com.google.common.base.Preconditions;
import org.bouncycastle.crypto.digests.SHA512Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;
import org.vitej.core.exception.ActionNotSupportedException;
import org.vitej.core.utils.BytesUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;

import static java.util.Arrays.copyOf;
import static java.util.Collections.unmodifiableList;
import static java.util.Collections.unmodifiableMap;

public class Mnemonic {
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private static final String seceret = "ed25519 blake2b seed";

    private static byte[] generateSeed(int length) {
        byte[] seed = new byte[length];
        SECURE_RANDOM.nextBytes(seed);
        return seed;
    }

    public static List<String> createBip39Mnemonic(int length, MnemonicLanguage language) {
        Preconditions.checkArgument(length == 24, "Invalid seed length");

        byte[] seed = generateSeed(32 * length / 3 / 8);

        int seedLength = seed.length * 8;
        byte[] seedWithChecksum = copyOf(seed, seed.length + 1);
        seedWithChecksum[seed.length] = checksum(seed);

        int checksumLength = seedLength / 32;
        int mnemonicSentenceLength = (seedLength + checksumLength) / 11;

        try {
            List<String> ret = new ArrayList<>();
            for (int i = 0; i < mnemonicSentenceLength; i++) {
                ret.add(language.getWord(next11Bits(seedWithChecksum, i * 11)));
            }
            return ret;
        } finally {
            wipe(seedWithChecksum);
        }
    }


    public static byte[] bip39ToSeed(List<String> mnemonic, MnemonicLanguage language) {
        Preconditions.checkArgument(isValid(mnemonic, language), "Invalid mnemonic");
        byte[] seedWithChecksum = extractSeedWithChecksum(mnemonic, language);
        try {
            return extractSeed(seedWithChecksum);
        } finally {
            wipe(seedWithChecksum);
        }
    }

    public static boolean isValid(List<String> mnemonic, MnemonicLanguage language) {
        if (!mnemonic.stream().allMatch(language::wordExists)) {
            return false;
        }

        byte[] seedWithChecksum = extractSeedWithChecksum(mnemonic, language);
        byte[] seed = extractSeed(seedWithChecksum);

        byte expectedChecksum = checksum(seed);
        try {
            return expectedChecksum == seedWithChecksum[seedWithChecksum.length - 1];
        } finally {
            wipe(seedWithChecksum);
            wipe(seed);
        }
    }

    private static byte[] extractSeedWithChecksum(List<String> mnemonic, MnemonicLanguage language) {
        int mnemonicSentenceLength = mnemonic.size();

        int seedWithChecksumLength = mnemonicSentenceLength * 11;
        byte[] seedWithChecksum = new byte[(seedWithChecksumLength + 7) / 8];


        List<Integer> mnemonicIndexes = new ArrayList<>();
        for (String word : mnemonic) {
            mnemonicIndexes.add(language.getIndex(word));
        }

        for (int i = 0; i < mnemonicSentenceLength; i++) {
            writeNext11Bits(seedWithChecksum, mnemonicIndexes.get(i), i * 11);
        }

        return seedWithChecksum;
    }

    private static byte[] extractSeed(byte[] seedWithChecksum) {
        return copyOf(seedWithChecksum, seedWithChecksum.length - 1);
    }

    private static byte checksum(final byte[] seed) {
        try {
            final byte[] hash = MessageDigest.getInstance("SHA-256").digest(seed);
            final byte firstByte = hash[0];
            Arrays.fill(hash, (byte) 0);
            return firstByte;
        } catch (NoSuchAlgorithmException e) {
            throw new ActionNotSupportedException("Seed generation not supported", e);
        }
    }

    private static int next11Bits(byte[] bytes, int offset) {
        final int skip = offset / 8;
        final int lowerBitsToRemove = (3 * 8 - 11) - (offset % 8);
        return (((int) bytes[skip] & 0xff) << 16 |
                ((int) bytes[skip + 1] & 0xff) << 8 |
                (lowerBitsToRemove < 8
                        ? (int) bytes[skip + 2] & 0xff
                        : 0)) >> lowerBitsToRemove & (1 << 11) - 1;
    }

    private static void writeNext11Bits(byte[] bytes, int value, int offset) {
        int skip = offset / 8;
        int bitSkip = offset % 8;
        {//byte 0
            byte firstValue = bytes[skip];
            byte toWrite = (byte) (value >> (3 + bitSkip));
            bytes[skip] = (byte) (firstValue | toWrite);
        }

        {//byte 1
            byte valueInByte = bytes[skip + 1];
            final int i = 5 - bitSkip;
            byte toWrite = (byte) (i > 0 ? value << i : value >> -i);
            bytes[skip + 1] = (byte) (valueInByte | toWrite);
        }

        if (bitSkip >= 6) {//byte 2
            byte valueInByte = bytes[skip + 2];
            byte toWrite = (byte) (value << 13 - bitSkip);
            bytes[skip + 2] = (byte) (valueInByte | toWrite);
        }
    }

    public static KeyPair deriveKeyPair(String mnemonic, int index) {
        byte[] seed = PBKDF2SHA512.derive(mnemonic, "mnemonic" + "", 2048, 64);
        String VITE_ACCOUNT_PATH_FORMAT = "m/44'/666666'/%d'";
        String path = VITE_ACCOUNT_PATH_FORMAT.replaceAll("%d", index + "");
        byte[] result = deriveForPath(path, seed);
        Preconditions.checkNotNull(result);
        byte[] key = new byte[32];
        System.arraycopy(result, 0, key, 0, 32);
        return new KeyPair(key);
    }

    private static byte[] deriveForPath(String path, byte[] seed) {
        try {
            byte[] bytes = hmacSha512(seceret.getBytes("utf-8"), seed);
            byte[] masterKey = new byte[32];
            byte[] chainCode = new byte[32];
            System.arraycopy(bytes, 0, masterKey, 0, 32);
            System.arraycopy(bytes, 32, chainCode, 0, 32);
            String segments[] = path.replaceAll("'", "").split("/");
            byte[] result = null;
            for (int i = 1; i < 4; i++) {
                int i32 = Integer.parseInt(segments[i]);
                i32 = i32 | 1 << 31;
                result = derive(i32, masterKey, chainCode);
                masterKey = new byte[32];
                chainCode = new byte[32];
                System.arraycopy(result, 0, masterKey, 0, 32);
                System.arraycopy(result, 32, chainCode, 0, 32);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] hmacSha512(byte[] key, byte[] data) {
        return hmacSha512(createHmacSha512Digest(key), data);
    }

    private static HMac createHmacSha512Digest(byte[] key) {
        SHA512Digest digest = new SHA512Digest();
        HMac hMac = new HMac(digest);
        hMac.init(new KeyParameter(key));
        return hMac;
    }

    private static byte[] hmacSha512(HMac hmacSha512, byte[] input) {
        hmacSha512.reset();
        hmacSha512.update(input, 0, input.length);
        byte[] out = new byte[64];
        hmacSha512.doFinal(out, 0);
        return out;
    }

    private static byte[] derive(int index, byte[] masterkey, byte[] chainCode) {
        byte[] iBytes = BytesUtils.intToBytes(index);
        byte[] prefix = {0x0};
        byte[] keymodfify = BytesUtils.merge(prefix, masterkey);
        keymodfify = BytesUtils.merge(keymodfify, iBytes);
        return hmacSha512(chainCode, keymodfify);
    }

    public enum MnemonicLanguage {
        ENGLISH("english.txt");

        private final List<String> dictionary;
        private final Map<String, Integer> dictionaryMap;

        MnemonicLanguage(String fileName) {
            try {
                List<String> lines = new ArrayList<>();
                String line = null;
                BufferedReader bufferReader = null;
                try {
                    InputStream inputStream = Mnemonic.class.getResourceAsStream("/" + fileName);
                    bufferReader = new BufferedReader(new InputStreamReader(inputStream));
                    while ((line = bufferReader.readLine()) != null) {
                        lines.add(line);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        bufferReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                this.dictionary = unmodifiableList(lines);
                Map<String, Integer> tempDictionaryMap = new HashMap<>();
                for (String word : dictionary) {
                    tempDictionaryMap.put(word, dictionary.indexOf(word));
                }
                this.dictionaryMap = unmodifiableMap(tempDictionaryMap);
            } catch (Exception e) {
                throw new IllegalStateException("Could'nt read file " + fileName, e);
            }
        }

        public List<String> getDictionary() {
            return dictionary;
        }

        public Map<String, Integer> getDictionaryMap() {
            return dictionaryMap;
        }

        public String getWord(int index) {
            return dictionary.get(index);
        }

        public boolean wordExists(String word) {
            return dictionaryMap.containsKey(word);
        }

        public Integer getIndex(String word) {
            return dictionaryMap.get(word);
        }
    }

    private static void wipe(byte[] b) {
        Arrays.fill(b, (byte) 0);
    }
}
