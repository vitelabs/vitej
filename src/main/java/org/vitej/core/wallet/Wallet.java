package org.vitej.core.wallet;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Vite wallet, a wallet is initialized by mnemonic phrase, contains 10 key pairs.
 */
public class Wallet {
    private List<String> mnemonic;

    /**
     * Create a new wallet
     */
    public Wallet() {
        this.mnemonic = Mnemonic.createBip39Mnemonic(24, Mnemonic.MnemonicLanguage.ENGLISH);
    }

    /**
     * Create a new wallet
     *
     * @param length   Mnemonic phrase length
     * @param language Mnemonic phrase language, only English is supported now
     */
    public Wallet(int length, Mnemonic.MnemonicLanguage language) {
        this.mnemonic = Mnemonic.createBip39Mnemonic(length, language);
    }

    /**
     * Initialize a wallet instance by mnemonic phrase
     *
     * @param mnemonic Mnemonic phrase
     */
    public Wallet(List<String> mnemonic) {
        this.mnemonic = mnemonic;
    }

    @Override
    public String toString() {
        return StringUtils.join(mnemonic, " ");
    }

    /**
     * Return mnemonic phrase
     *
     * @return Mnemonic phrase
     */
    public List<String> getMnemonic() {
        return mnemonic;
    }

    /**
     * Return first key pair in the wallet
     *
     * @return Key pair
     */
    public KeyPair deriveKeyPair() {
        return deriveKeyPair(0);
    }

    /**
     * Return the index th key pair in the wallet
     *
     * @param index key pair index
     * @return Key pair
     */
    public KeyPair deriveKeyPair(int index) {
        return Mnemonic.deriveKeyPair(toString(), index);
    }
}
