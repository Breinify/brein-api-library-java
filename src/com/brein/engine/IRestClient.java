package com.brein.engine;

import com.brein.api.BreinActivity;
import com.brein.domain.BreinResponse;

/**
 * Interface for all possible rest clients
 */
public interface IRestClient {

    /**
     * invokes the post request
     *
     * @param breinActivity data
     */
    void doRequest(final BreinActivity breinActivity);

    /**
     * performs a lookup and provides details
     *
     * @param breinActivity contains request data
     * @return response from Breinify
     */
    BreinResponse doLookup(final BreinActivity breinActivity);

    /**
     * stops possible functionality (e.g. threads)
     */
    void stop();
}
