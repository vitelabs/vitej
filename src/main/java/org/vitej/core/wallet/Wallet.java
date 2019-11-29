package org.vitej.core.wallet;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class Wallet {
    private List<String> mnemonic;

    public Wallet() {
        this.mnemonic = Mnemonic.createBip39Mnemonic(24, Mnemonic.MnemonicLanguage.ENGLISH);
    }

    public Wallet(int length, Mnemonic.MnemonicLanguage language) {
        this.mnemonic = Mnemonic.createBip39Mnemonic(length, language);
    }

    public Wallet(List<String> mnemonic) {
        this.mnemonic = mnemonic;
    }

    public String exportMnemonic() {
        return StringUtils.join(mnemonic, " ");
    }

    public List<String> getMnemonic() {
        return mnemonic;
    }

    public KeyPair deriveKeyPair() {
        return deriveKeyPair(0);
    }

    public KeyPair deriveKeyPair(int index) {
        return Mnemonic.deriveKeyPair(exportMnemonic(), index);
    }
}
