package com.brein.engine;

import com.brein.api.BreinActivity;
import com.brein.api.BreinBase;
import com.brein.api.BreinException;
import com.brein.domain.BreinConfig;
import com.brein.domain.BreinResult;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;

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

            if (response.getStatus() != 200) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Response is: " + response.toString());
                }

                if (errorCallback != null) {
                    final String message = "Failure in rest call. Reason: " + response.toString();
                    errorCallback.apply(message);
                }
            }

        } catch (final UniformInterfaceException | ClientHandlerException e) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Exception", e);

                if (errorCallback != null) {
                    final String message = "Failure in rest call. Reason " + e.getLocalizedMessage();
                    errorCallback.apply(message);
                }
            }
        }
    }

    /**
     * stops possible functionality (e.g. threads)
     */
    @Override
    public void terminate() {
    }

    @Override
    public BreinResult invokeRequest(final BreinBase breinRequestObject) {

        final WebResource webResource = client.resource(getFullyQualifiedUrl(breinRequestObject));

        try {
            final ClientResponse response = webResource.type("application/json")
                    .post(ClientResponse.class, getRequestBody(breinRequestObject));

            if (response.getStatus() == 200) {
                return new BreinResult(response.getEntity(String.class), response.getStatus());
            } else {
                final String exceptionMsg = "Rest call exception with status code: " + response.getStatus();
                throw new BreinException(exceptionMsg);
            }
        } catch (final UniformInterfaceException | ClientHandlerException e) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Exception", e);
                throw new BreinException("jersey rest call exception");
            }
        }

        return null;
    }

    /**
     * configuration of the rest  client
     */
    @Override
    public void configure(final BreinConfig breinConfig) {
    }
}
