package com.brein.api;

/**
 * Base class for the secret strategy
 */
public interface ISecretStrategy {

    /**
     * Creates the appropriate signature that is part of the request to
     * the Breinify server.
     *
     * @return creates signature
     */
    String createSignature();
}
