package com.brein.api;

/**
 * Exception in case of wrong configuration
 */
public class BreinInvalidConfigurationException extends RuntimeException {

    /**
     * Exception methods...
     */
    public BreinInvalidConfigurationException(final Throwable e) {
        super(e);
    }

    public BreinInvalidConfigurationException(final String msg) {
        super(msg);
    }

    public BreinInvalidConfigurationException(final String msg, final Exception cause) {
        super(msg, cause);
    }


}
