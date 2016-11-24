package com.brein.engine;

/**
 * Specifies the possible Engine  Types
 */
public enum BreinEngineType {

    UNIREST_ENGINE("com.mashape.unirest.http.Unirest"),
    JERSEY_ENGINE("com.sun.jersey.api.client.Client"),
    AUTO_DETECT(null),
    NO_ENGINE(null);

    private final String clazzName;

    BreinEngineType(final String clazzName) {
        this.clazzName = clazzName;
    }

    public static boolean isSupported(final BreinEngineType breinEngineType) {
        return breinEngineType.hasClass();
    }

    /**
     * Checks if a class exist on the classpath
     *
     * @return {@code true} if the class exists, otherwise {@code false}
     */
    protected boolean hasClass() {
        try {
            final Class<?> clazz = Class.forName(clazzName, false, getClass().getClassLoader());
            return clazz != null;
        } catch (final ClassNotFoundException e) {
            return false;
        }
    }
}
