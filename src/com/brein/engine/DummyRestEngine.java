package com.brein.engine;

import com.brein.api.BreinActivity;
import com.brein.api.BreinBase;
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
     * stops possible functionality (e.g. threads)
     */
    @Override
    public void terminate() {
    }

    @Override
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
