package com.brein.engine;

import com.brein.api.*;
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
     * performs a lookup and provides details
     *
     * @param breinLookup contains request data
     * @return response from Breinify
     */
    @Override
    public BreinResult doLookup(final BreinLookup breinLookup) {

        // validate the input objects
        validate(breinLookup);

        return invokeRequest(breinLookup);
    }

    /**
     * stops possible functionality (e.g. threads)
     */
    @Override
    public void terminate() {
    }

    /**
     * performs a temporalData request
     *
     * @param breinTemporalData contains the request data
     * @return result from request
     * @throws BreinException exception will be thrown
     */
    @Override
    public BreinResult doTemporalDataRequest(final BreinTemporalData breinTemporalData) throws BreinException {
        // validate the input objects
        validate(breinTemporalData);

        return invokeRequest(breinTemporalData);
    }

    /**
     * invokes a recommendation request
     *
     * @param breinRecommendation contains the request data
     * @return result from the request
     * @throws BreinException exception
     */
    @Override
    public BreinResult doRecommendation(final BreinRecommendation breinRecommendation) throws BreinException {

        validate(breinRecommendation);
        return invokeRequest(breinRecommendation);
    }

    /**
     * invokes the request
     *
     * @param breinRequestObject contains the request data
     * @return result from the Breinify engine
     */
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
