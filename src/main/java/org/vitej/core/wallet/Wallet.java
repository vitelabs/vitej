package org.vitej.core.wallet;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.List;

/**
 * Vite wallet, a wallet is initialized by mnemonic phrase, contains 10 key pairs.
 */
public final class Wallet {
    private final ImmutableList<String> mnemonic;

    /**
     * Create a new wallet
     */
    public Wallet() {
        this.mnemonic = ImmutableList.copyOf(Mnemonic.createBip39Mnemonic(24, Mnemonic.MnemonicLanguage.ENGLISH));
    }

    /**
     * Initialize a wallet instance by entropy file and password
     * 
     * @param filename entropy file path
     * @param password  password
     */
    public Wallet(String filename, String password) {
        try {
            byte[] entropy = EntropyFile.loadFromFile(filename, password);
            String mnemonic = Entropy.toMnemonic(entropy);

            this.mnemonic = ImmutableList.copyOf(mnemonic.split(" "));
            Preconditions.checkArgument(Mnemonic.isValid(this.mnemonic, Mnemonic.MnemonicLanguage.ENGLISH), "Invalid mnemonic");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Create a new wallet
     *
     * @param length   Mnemonic phrase length
     * @param language Mnemonic phrase language, only English is supported now
     */
    public Wallet(int length, Mnemonic.MnemonicLanguage language) {
        this.mnemonic = ImmutableList.copyOf(Mnemonic.createBip39Mnemonic(length, language));
    }

    /**
     * Initialize a wallet instance by mnemonic phrase
     *
     * @param mnemonic Mnemonic phrase
     */
    public Wallet(List<String> mnemonic) {
        Preconditions.checkArgument(Mnemonic.isValid(mnemonic, Mnemonic.MnemonicLanguage.ENGLISH), "Invalid mnemonic");
        this.mnemonic = ImmutableList.copyOf(mnemonic);
    }

    /**
     * Initialize a wallet instance by mnemonic phrase
     *
     * @param mnemonic Mnemonic phrase, join with space
     */
    public Wallet(String mnemonic) {
        this.mnemonic = ImmutableList.copyOf(mnemonic.split(" "));
        Preconditions.checkArgument(Mnemonic.isValid(this.mnemonic, Mnemonic.MnemonicLanguage.ENGLISH), "Invalid mnemonic");
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

    /**
     * save to entropy filename
     * 
     * @param filename file name 
     * @param password password
     * @throws IOException when file not exist
     */
    public void saveToFile(String filename, String password) throws IOException {
        byte[] entropy = Entropy.fromMnemonic(toString());
        String address = deriveKeyPair().getAddress().toString();
        EntropyFile.saveToFile(address, entropy, filename, password);
    }
}
