package com.brein.engine;

import com.brein.api.BreinActivity;
import com.brein.api.BreinLookup;
import com.brein.domain.BreinResponse;

/**
 * could be the jersey client implementation
 */
public class JerseyRestEngine implements IRestClient {


    /**
     * invokes the post request
     *
     * @param breinActivity data
     */
    public void doRequest(final BreinActivity breinActivity) {
    }

    /**
     * performs a lookup and provides details
     *
     * @param breinLookup contains request data
     * @return response from Breinify
     */
    public BreinResponse doLookup(final BreinLookup breinLookup) {
        return null;
    }

    /**
     * stops possible functionality (e.g. threads)
     */
    public void stop() {
    }

    /**
     * configuration of the rest client
     */
    public void configure() {
    }

}
