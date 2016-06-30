package com.brein.engine;

import com.brein.api.BreinActivity;
import com.brein.api.BreinLookup;
import com.brein.domain.BreinResponse;

/**
 * Creates the Rest Engine (currently only unirest) and provides the methods to
 * invoke activity and lookUp calls
 */
public class BreinEngine {

    /**
     * creation of rest engine. I know that this implementation only allows UNIREST and nothing
     * else. Configuration of parameter needs to be done.
     * TODO
     */
    private final IRestClient restClient =
            RestClientFactory.getRestEngine(RestClientFactory.UNIREST_ENGINE);

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
     * performs a lookup. This will be delegated to the
     * configured restClient.
     *
     * @param breinLookup contains the appropriate data for the lookup
     *                 request
     * @return if succeeded a BreinResponse object or null
     */
    public BreinResponse performLookUp(final BreinLookup breinLookup) {

        if (breinLookup != null) {
            return restClient.doLookup(breinLookup);
        }

        return null;
    }
}
