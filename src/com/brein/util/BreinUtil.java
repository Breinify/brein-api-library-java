package com.brein.util;

import com.brein.api.BreinException;
import com.brein.engine.IRestEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;

public class BreinUtil {
    private static final Logger LOG = LoggerFactory.getLogger(IRestEngine.class);
    private static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final Random RANDOM = new Random();

    private BreinUtil() {
        /*
         * Utility classes, which are a collection of static members,
         * are not meant to be instantiated.
         */
    }

    /**
     * Verifies if the object contains a value
     * Return false in case of:
     * - null
     * - empty strings
     *
     * @param object to check
     *
     * @return true if object contains data
     */
    public static boolean containsValue(final Object object) {

        if (object == null) {
            return false;
        } else if (String.class.isInstance(object)) {
            final String strObj = String.class.cast(object);
            return !strObj.isEmpty();
        } else {
            return true;
        }
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
     *
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
     *
     * @return created secret
     */
    public static String generateSecret(final int length) {
        final SecureRandom random = new SecureRandom();

        final byte[] bytes = new byte[length / 8];
        random.nextBytes(bytes);

        return Base64.getEncoder().encodeToString(bytes).toLowerCase();
    }

    /**
     * Generates the signature
     *
     * @param message the message to generate the signature for
     * @param secret  the secret retrieved from the api
     *
     * @return the generated signature
     */
    public static String generateSignature(final String message, final String secret) {
        if (message == null) {
            throw new BreinException("Illegal value for message in method generateSignature");
        } else if (secret == null) {
            throw new BreinException("Illegal value for secret in method generateSignature");
        }

        try {
            final byte[] e = secret.getBytes(StandardCharsets.UTF_8.name());
            final SecretKeySpec secretKey = new SecretKeySpec(e, "HmacSHA256");

            final Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(secretKey);

            final byte[] hmacData = mac.doFinal(message.getBytes(StandardCharsets.UTF_8.name()));
            return Base64.getEncoder().encodeToString(hmacData);
        } catch (InvalidKeyException | UnsupportedEncodingException var5) {
            throw new IllegalStateException("Unable to create signature!", var5);
        } catch (final NoSuchAlgorithmException e1) {
            throw new IllegalStateException("Unable to find needed algorithm!", e1);
        }
    }

    public static boolean isValidUrl(final String fullyQualifiedUrl) {
        try {
            final URL url = new URL(fullyQualifiedUrl);
            final URLConnection conn = url.openConnection();
            conn.connect();
        } catch (final IOException e) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("The url " + fullyQualifiedUrl + "' is invalid.", e);
            }
            return false;
        }

        return true;
    }
}
