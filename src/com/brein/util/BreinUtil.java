package com.brein.util;

import com.brein.api.BreinException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;

/**
 * Utility class
 */
public class BreinUtil {

    private static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static final Mac mac;

    private static final Random RANDOM = new Random();

    static {

        try {
            mac = Mac.getInstance("HmacSHA256");
        } catch (NoSuchAlgorithmException var1) {
            throw new IllegalStateException("Unable to find needed algorithm!", var1);
        }
    }

    /**
     * Verifies if the object contains a value
     * Return false in case of:
     *    - null
     *    - empty strings
     *
     * @param object to check
     *
     * @return true if object contains data
     */
    public static boolean containsValue(final Object object) {

        if (object == null) {
            return false;
        }

        if (object.getClass() == String.class) {
            final String strObj = (String)object;
            return strObj.length() > 0;
        }

        return true;
    }

    /**
     * Helper method to generate a random string
     *
     * @return string
     */
    public static String randomString() {
        final int len = 1 + RANDOM.nextInt(100);

        return randomString(len);
    }

    /**
     * Helper methods generates a random string by len
     *
     * @param len of the requested string
     * @return random string
     */
    public static String randomString(final int len) {
        final StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(AB.charAt(RANDOM.nextInt(AB.length())));
        }
        return sb.toString();
    }

    /**
     * Creates a secret by given len
     *
     * @param length of the secret
     * @return created secret
     */
    public static String generateSecret(final int length) {
        final SecureRandom random = new SecureRandom();

        final byte[] bytes = new byte[length / 8];
        random.nextBytes(bytes);

        return Base64.getEncoder().encodeToString(bytes).toLowerCase();
    }

    /**
     * generates the signature
     *
     * @param message
     * @param secret
     * @return
     */
    public static String generateSignature(final String message, final String secret) {

        if (message == null) {
            throw new BreinException("Illegal value for message in method generateSignature");
        }

        if (secret == null) {
            throw new BreinException("Illegal value for secret in method generateSignature");
        }

        try {
            byte[] e = secret.getBytes(StandardCharsets.UTF_8.name());
            SecretKeySpec secretKey = new SecretKeySpec(e, "HmacSHA256");
            mac.init(secretKey);
            byte[] hmacData = mac.doFinal(message.getBytes(StandardCharsets.UTF_8.name()));
            return Base64.getEncoder().encodeToString(hmacData);
        } catch (InvalidKeyException | UnsupportedEncodingException var5) {
            throw new IllegalStateException("Unable to create signature!", var5);
        }

    }
}
