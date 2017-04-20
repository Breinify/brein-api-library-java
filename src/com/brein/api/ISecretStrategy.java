package com.brein.api;

import com.brein.domain.BreinConfig;

import java.util.Map;

/**
 * Base class for the secret strategy
 */
@FunctionalInterface
public interface ISecretStrategy {

    /**
     * Creates the appropriate signature that is part of the request to
     * the Breinify server.
     *
     * @param config      the configuration
     * @param requestData the data used for the request including all the data that will be sent, without the signature
     *
     * @return creates signature
     */
    String createSignature(final BreinConfig config, final Map<String, Object> requestData);
}
