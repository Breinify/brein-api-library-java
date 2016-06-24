package com.brein.engine;

import com.brein.api.BreinActivity;

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
     * stops possible functionality (e.g. threads)
     */
    void stop();
}
