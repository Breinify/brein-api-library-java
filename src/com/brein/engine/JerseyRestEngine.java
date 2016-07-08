package com.brein.engine;

import com.brein.api.BreinActivity;
import com.brein.api.BreinLookup;
import com.brein.domain.BreinConfig;
import com.brein.domain.BreinResult;

/**
 * could be the jersey rest engine implementation
 */
public class JerseyRestEngine implements IRestEngine {

    /**
     * invokes the post request
     *
     * @param breinActivity data
     */
    @Override
    public void doRequest(final BreinActivity breinActivity) {
    }

    /**
     * performs a lookup and provides details
     *
     * @param breinLookup contains request data
     * @return response from Breinify
     */
    @Override
    public BreinResult doLookup(final BreinLookup breinLookup) {
        return null;
    }

    /**
     * stops possible functionality (e.g. threads)
     */
    @Override
    public void terminate() {
    }

    /**
     * configuration of the rest client
     */
    @Override
    public void configure(final BreinConfig breinConfig) {
    }

}
