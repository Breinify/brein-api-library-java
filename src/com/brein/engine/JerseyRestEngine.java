package com.brein.engine;

import com.brein.api.BreinActivity;
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
     * @param breinActivity contains request data
     * @return response from Breinify
     */
    public BreinResponse doLookup(final BreinActivity breinActivity) {
        return null;
    }

    /**
     * stops possible functionality (e.g. threads)
     */
    public void stop() {

    }
}
