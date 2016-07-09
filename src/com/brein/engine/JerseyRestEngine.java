package com.brein.engine;

import com.brein.api.BreinActivity;
import com.brein.api.BreinException;
import com.brein.api.BreinLookup;
import com.brein.domain.BreinConfig;
import com.brein.domain.BreinResult;
import com.sun.jersey.api.client.*;

/**
 * could be the jersey rest engine implementation
 */
public class JerseyRestEngine implements IRestEngine {

    /**
     * create jersey client
     */
    final Client client = Client.create();

    /**
     * invokes the post request
     *
     * @param breinActivity data
     */
    @Override
    public void doRequest(final BreinActivity breinActivity) {

        /**
         * validation of activity and config
         */
        validateActivity(breinActivity);
        validateConfig(breinActivity);

        final WebResource webResource = client.resource(getFullyQualifiedUrl(breinActivity));

        try {
            final ClientResponse response = webResource.type("application/json")
                    .post(ClientResponse.class, getRequestBody(breinActivity));
        } catch (final UniformInterfaceException | ClientHandlerException e) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Exception", e);
                throw new BreinException("jersey rest call exception");
            }
        }
    }

    /**
     * performs a lookup and provides details
     *
     * @param breinLookup contains request data
     * @return response from Breinify
     */
    @Override
    public BreinResult doLookup(final BreinLookup breinLookup) {

        /**
         * validation of lookup and config
         */
        validateLookup(breinLookup);
        validateConfig(breinLookup);

        final WebResource webResource = client.resource(getFullyQualifiedUrl(breinLookup));

        try {
            final ClientResponse response = webResource.type("application/json")
                    .post(ClientResponse.class, getRequestBody(breinLookup));

            return new BreinResult(response.getEntity(String.class));
        } catch (final UniformInterfaceException | ClientHandlerException e) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Exception", e);
                throw new BreinException("jersey rest call exception");
            }
        }

        return null;
    }

    /**
     * stops possible functionality (e.g. threads)
     */
    @Override
    public void terminate() {
    }

    /**
     * configuration of the rest  client
     */
    @Override
    public void configure(final BreinConfig breinConfig) {
    }

}
