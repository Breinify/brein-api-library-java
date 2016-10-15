package com.brein.engine;

import com.brein.api.BreinActivity;
import com.brein.api.BreinException;
import com.brein.api.BreinLookup;
import com.brein.domain.BreinConfig;
import com.brein.domain.BreinResult;
import com.sun.jersey.api.client.*;

import java.util.function.Function;

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
     * @param errorCallback will be invoked in case of an error
     */
    @Override
    public void doRequest(final BreinActivity breinActivity,
                          final Function<String, Void> errorCallback) {

        // validate the input objects
        validate(breinActivity);

        final WebResource webResource = client.resource(getFullyQualifiedUrl(breinActivity));

        try {
            final ClientResponse response = webResource.type("application/json")
                    .post(ClientResponse.class, getRequestBody(breinActivity));
        } catch (final UniformInterfaceException | ClientHandlerException e) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Exception", e);

                if (errorCallback != null) {
                    errorCallback.apply("jersey rest call not successful!");
                }
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

        // validate the input objects
        validate(breinLookup);

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
