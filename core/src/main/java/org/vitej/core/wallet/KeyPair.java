package org.vitej.core.wallet;


import org.vitej.core.protocol.methods.Address;

public class KeyPair {
    private byte[] privateKey;
    private byte[] publicKey;
    private Address address;

    public KeyPair(byte[] privateKey, byte[] publicKey, Address address) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
        this.address = address;
    }

    public KeyPair(byte[] privateKey) {
        this.privateKey = privateKey;
        this.publicKey = Crypto.getPublicKey(privateKey);
        this.address = Address.publicKeyToAddress(this.publicKey);
    }

    public byte[] getPrivateKey() {
        return privateKey;
    }

    public byte[] getPublicKey() {
        return publicKey;
    }

    public Address getAddress() {
        return address;
    }

    public byte[] sign(byte[] message) throws IllegalStateException {
        return Crypto.sign(message, this.privateKey);
    }
}
