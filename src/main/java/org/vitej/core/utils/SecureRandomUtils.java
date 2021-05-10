package org.vitej.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.SecureRandomSpi;
import java.security.Security;

/**
 * Utility class for working with SecureRandom implementation.
 *
 * Taken from Web3j implementation 
 * https://github.com/web3j/web3j/blob/357b0213f2e5939bd87cf832c8ae2483b23d979d/crypto/src/main/java/org/web3j/crypto/SecureRandomUtils.java#L23
 */
public final class SecureRandomUtils {
    private static final Logger log = LoggerFactory.getLogger(LinuxSecureRandom.class);

    private static final SecureRandom SECURE_RANDOM;

    static {
        if (isAndroidRuntime()) {
            new LinuxSecureRandom();
        }
        SECURE_RANDOM = new SecureRandom();
    }

    static SecureRandom secureRandom() {
        return SECURE_RANDOM;
    }


    public static byte[] randomBytes(int size) {
        byte[] bytes = new byte[size];
        secureRandom().nextBytes(bytes);
        return bytes;
    }

    // Taken from BitcoinJ implementation
    // https://github.com/bitcoinj/bitcoinj/blob/3cb1f6c6c589f84fe6e1fb56bf26d94cccc85429/core/src/main/java/org/bitcoinj/core/Utils.java#L573
    private static int isAndroid = -1;

    static boolean isAndroidRuntime() {
        if (isAndroid == -1) {
            final String runtime = System.getProperty("java.runtime.name");
            isAndroid = (runtime != null && runtime.equals("Android Runtime")) ? 1 : 0;
        }
        return isAndroid == 1;
    }

    private SecureRandomUtils() {}



    static class LinuxSecureRandom extends SecureRandomSpi {
        private static final FileInputStream urandom;

        private static class LinuxSecureRandomProvider extends Provider {
            public LinuxSecureRandomProvider() {
                super(
                        "LinuxSecureRandom",
                        1.0,
                        "A Linux specific random number provider that uses /dev/urandom");
                put("SecureRandom.LinuxSecureRandom", LinuxSecureRandom.class.getName());
            }
        }



        static {
            try {
                File file = new File("/dev/urandom");
                // This stream is deliberately leaked.
                urandom = new FileInputStream(file);
                if (urandom.read() == -1) {
                    throw new RuntimeException("/dev/urandom not readable?");
                }
                // Now override the default SecureRandom implementation with this one.
                int position = Security.insertProviderAt(new LinuxSecureRandomProvider(), 1);

                if (position != -1) {
                    log.info("Secure randomness will be read from {} only.", file);
                } else {
                    log.info("Randomness is already secure.");
                }
            } catch (FileNotFoundException e) {
                // Should never happen.
                log.error("/dev/urandom does not appear to exist or is not openable");
                throw new RuntimeException(e);
            } catch (IOException e) {
                log.error("/dev/urandom does not appear to be readable");
                throw new RuntimeException(e);
            }
        }

        private final DataInputStream dis;

        public LinuxSecureRandom() {
            // DataInputStream is not thread safe, so each random object has its own.
            dis = new DataInputStream(urandom);
        }

        @Override
        protected void engineSetSeed(byte[] bytes) {
            // Ignore.
        }

        @Override
        protected void engineNextBytes(byte[] bytes) {
            try {
                dis.readFully(bytes); // This will block until all the bytes can be read.
            } catch (IOException e) {
                throw new RuntimeException(e); // Fatal error. Do not attempt to recover from this.
            }
        }

        @Override
        protected byte[] engineGenerateSeed(int i) {
            byte[] bits = new byte[i];
            engineNextBytes(bits);
            return bits;
        }
    }
}
