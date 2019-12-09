package org.vitej.core.wallet;


import org.vitej.core.protocol.methods.Address;

/**
 * Vite key pair. A key pair includes a private key and public key pair of an address.
 */
public class KeyPair {
    private byte[] privateKey;
    private byte[] publicKey;
    private Address address;

    /**
     * Create key pair instance
     *
     * @param privateKey Private key
     * @param publicKey  Public key
     * @param address    Address
     */
    public KeyPair(byte[] privateKey, byte[] publicKey, Address address) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
        this.address = address;
    }

    /**
     * Create key pair instance
     *
     * @param privateKey Private key
     */
    public KeyPair(byte[] privateKey) {
        this.privateKey = privateKey;
        this.publicKey = Crypto.getPublicKey(privateKey);
        this.address = Address.publicKeyToAddress(this.publicKey);
    }

    /**
     * Return private key
     *
     * @return Private key
     */
    public byte[] getPrivateKey() {
        return privateKey;
    }

    /**
     * Return public key
     *
     * @return Public key
     */
    public byte[] getPublicKey() {
        return publicKey;
    }

    /**
     * Return address
     *
     * @return Address
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Return signature of input data
     *
     * @param message data
     * @return signature
     * @throws IllegalStateException Throws IllegalStateException when sign failed
     */
    public byte[] sign(byte[] message) throws IllegalStateException {
        return Crypto.sign(message, this.privateKey);
    }
}
