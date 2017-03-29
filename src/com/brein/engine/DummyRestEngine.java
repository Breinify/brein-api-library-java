package com.brein.engine;

import com.brein.api.*;
import com.brein.domain.BreinConfig;
import com.brein.domain.BreinResult;

import java.util.function.Function;

/**
 * could be the jersey rest engine implementation
 */
public class DummyRestEngine implements IRestEngine {


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

        getFullyQualifiedUrl(breinActivity);

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
     * @param breinTemporalDataRequest contains the request data
     * @return result from request
     * @throws BreinException exception will be thrown
     */
    @Override
    public BreinResult doTemporalDataRequest(final BreinTemporalDataRequest breinTemporalDataRequest) throws BreinException {
        // validate the input objects
        validate(breinTemporalDataRequest);

        return invokeRequest(breinTemporalDataRequest);
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

        getFullyQualifiedUrl(breinRequestObject);

        return new BreinResult("");
    }

    /**
     * configuration of the rest  client
     */
    @Override
    public void configure(final BreinConfig breinConfig) {
    }

}
