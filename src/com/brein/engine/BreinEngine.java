package com.brein.engine;

import com.brein.api.BreinActivity;

/**
 * Creates the Rest Engine (currently only unirest) and provides the methods to
 * invoke activity and lookUp calls
 */
public class BreinEngine {

    /**
     * creation of rest engine. I know that this implementation only allows UNIREST and nothing
     * else.
     */
    private final IRestClient restClient = RestClientFactory.getRestEngine(RestClientFactory.UNIREST_ENGINE);

    /**
     * sends an activity to the breinify server
     * @param activity data
     */
    public void sendActivity(final BreinActivity activity) {

        if (activity != null) {
            restClient.doRequest(activity);
        }

    }

    /**
     * lookup functionality
     */
    public void performLookUp() {

    }

}
