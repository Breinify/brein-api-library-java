package com.brein.util;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Utility class
 */
public class BreinUtil {

    private static final Mac mac;

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
     *
     * @param message
     * @param secret
     * @return
     */
    public static String generateSignature(String message, String secret) {
        // TODO: 10.07.16
        return "";

    }



}
