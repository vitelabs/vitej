package org.vitej.core.wallet;

import com.rfksystems.blake2b.Blake2b;
import com.rfksystems.blake2b.security.Blake2bProvider;
import net.i2p.crypto.eddsa.EdDSAEngine;
import net.i2p.crypto.eddsa.EdDSAPrivateKey;
import net.i2p.crypto.eddsa.EdDSAPublicKey;
import net.i2p.crypto.eddsa.math.Curve;
import net.i2p.crypto.eddsa.math.Field;
import net.i2p.crypto.eddsa.math.ed25519.Ed25519LittleEndianEncoding;
import net.i2p.crypto.eddsa.math.ed25519.Ed25519ScalarOps;
import net.i2p.crypto.eddsa.spec.EdDSANamedCurveSpec;
import net.i2p.crypto.eddsa.spec.EdDSANamedCurveTable;
import net.i2p.crypto.eddsa.spec.EdDSAPrivateKeySpec;
import net.i2p.crypto.eddsa.spec.EdDSAPublicKeySpec;

import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.Provider;
import java.security.Security;
import java.util.Arrays;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

public class Crypto {
    private static Field ED25519_FIELD = new Field(
            256,
            net.i2p.crypto.eddsa.Utils.hexToBytes("edffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff7f"),
            new Ed25519LittleEndianEncoding());
    private static Curve ED25519_CURVE = new Curve(ED25519_FIELD,
            net.i2p.crypto.eddsa.Utils.hexToBytes("a3785913ca4deb75abd841414d0a700098e879777940c78c73fe6f2bee6c0352"),
            ED25519_FIELD.fromByteArray(net.i2p.crypto.eddsa.Utils.hexToBytes("b0a00e4a271beec478e42fad0618432fa7d7fb3d99004d2b0bdfc14f8024832b")));
    private static EdDSANamedCurveSpec ED25519_BLAKE2B_CURVES_PEC;

    static {
        Security.addProvider(new Blake2bProvider());
        ED25519_BLAKE2B_CURVES_PEC = new EdDSANamedCurveSpec(
                "VITE_" + EdDSANamedCurveTable.ED_25519,
                ED25519_CURVE,
                Blake2b.BLAKE2_B_512,
                new Ed25519ScalarOps(),
                ED25519_CURVE.createPoint(
                        net.i2p.crypto.eddsa.Utils.hexToBytes("5866666666666666666666666666666666666666666666666666666666666666"),
                        true));
    }

    private static Provider BLAKE2B_PROVIDER = new Blake2bProvider();

    public static byte[] getPublicKey(byte[] privateKey) {
        EdDSAPrivateKeySpec key = new EdDSAPrivateKeySpec(privateKey, ED25519_BLAKE2B_CURVES_PEC);
        return key.getA().toByteArray();
    }

    public static byte[] sign(byte[] message, byte[] privateKey) throws IllegalStateException {
        try {
            EdDSAEngine edDSAEngine = new EdDSAEngine(MessageDigest.getInstance(Blake2b.BLAKE2_B_512, BLAKE2B_PROVIDER));
            EdDSAPrivateKeySpec edDSAPrivateKeySpec = new EdDSAPrivateKeySpec(privateKey, ED25519_BLAKE2B_CURVES_PEC);
            EdDSAPrivateKey edDSAPrivateKey = new EdDSAPrivateKey(edDSAPrivateKeySpec);
            edDSAEngine.initSign(edDSAPrivateKey);
            edDSAEngine.setParameter(EdDSAEngine.ONE_SHOT_MODE);
            edDSAEngine.update(message);
            return edDSAEngine.sign();
        } catch (GeneralSecurityException e) {
            throw new IllegalStateException("sign failed, " + Arrays.toString(message), e);
        }
    }

    public static boolean verify(byte[] signature, byte[] hash, byte[] publicKey) throws IllegalStateException {
        try {
            EdDSAEngine edDSAEngine = new EdDSAEngine(MessageDigest.getInstance(Blake2b.BLAKE2_B_512, BLAKE2B_PROVIDER));
            EdDSAPublicKeySpec edDSAPublicKeySpec = new EdDSAPublicKeySpec(publicKey, ED25519_BLAKE2B_CURVES_PEC);
            EdDSAPublicKey edDSAPublicKey = new EdDSAPublicKey(edDSAPublicKeySpec);
            edDSAEngine.initVerify(edDSAPublicKey);
            edDSAEngine.setParameter(EdDSAEngine.ONE_SHOT_MODE);
            edDSAEngine.update(hash);
            return edDSAEngine.verify(signature);
        } catch (GeneralSecurityException e) {
            throw new IllegalStateException("verify failed, " + Arrays.toString(hash), e);
        }
    }

    public static byte[] digest(int digestSize, byte[]... byteArrays) {
        requireNonNull(byteArrays, "byteArrays can't be null");
        Blake2b blake2b = new Blake2b(null, digestSize, null, null);
        Stream.of(byteArrays).forEach(byteArray -> blake2b.update(byteArray, 0, byteArray.length));
        byte[] output = new byte[digestSize];
        blake2b.digest(output, 0);
        return output;
    }

    public static byte[] digest(byte[] data) {
        return digest(32, data);
    }
}
