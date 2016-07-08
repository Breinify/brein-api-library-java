package com.brein.util;

/**
 * Utility class
 */
public class BreinUtil {

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
}
