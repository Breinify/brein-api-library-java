package com.brein.api;

/**
 * BreinException
 */
public class BreinException extends RuntimeException {

    /*
     * Error Messages
     */
    public static final String URL_IS_NULL = "URL in request contains null";
    public static final String REQUEST_FAILED = "Failed request!";
    public static final String VALIDATE_ACTIVITY_OR_CONFIG_FAILED = "either activity or config not valid";
    public static final String ACTIVITY_VALIDATION_FAILED = "activity object is null";
    public static final String CONFIG_VALIDATION_FAILED = "activity object is null";
    public static final String REQUEST_BODY_FAILED = "request body is null or wrong";
    public static final String LOOKUP_VALIDATION_FAILED = "lookup validation failed";
    public static final String LOOKUP_EXCEPTION = "lookup exception has occurred";
    public static final String ENGINE_NOT_INITIALIZED = "rest engine not initialized";

    /*
     * Exception methods...
     *
     */
    public BreinException(final Throwable e) {
        super(e);
    }

    public BreinException(final String msg) {
        super(msg);
    }

    public BreinException(final String msg, final Exception cause) {
        super(msg, cause);
    }

}
