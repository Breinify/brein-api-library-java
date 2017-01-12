package com.brein.engine;

import com.brein.api.BreinActivity;
import com.brein.api.BreinLookup;
import com.brein.api.BreinRecommendation;
import com.brein.api.BreinTemporalData;
import com.brein.domain.BreinConfig;
import com.brein.domain.BreinResult;

/**
 * Creates the Rest Engine (currently only unirest) and provides the methods to
 * invoke activity and lookup calls
 */
public class BreinEngine {

    /**
     * creation of rest engine. I know that this implementation only allows UNIREST and nothing
     * else. Configuration of parameter needs to be done.
     */
    private final IRestEngine restEngine;

    /**
     * Creates the engine
     *
     * @param engineType (e.g. UNIREST...)
     */
    public BreinEngine(final BreinEngineType engineType) {
        restEngine = IRestEngine.getRestEngine(engineType);
    }

    /**
     * sends an activity to the breinify server
     *
     * @param activity data
     */
    public void sendActivity(final BreinActivity activity) {
        if (activity != null) {
            restEngine.doRequest(activity, activity.getErrorCallback());
        }
    }

    /**
     * performs a lookup. This will be delegated to the
     * configured restEngine.
     *
     * @param breinLookup contains the appropriate data for the lookup
     *                    request
     * @return if succeeded a BreinResponse object or  null
     */
    public BreinResult performLookUp(final BreinLookup breinLookup) {
        if (breinLookup != null) {
            return restEngine.doLookup(breinLookup);
        }

        return null;
    }

    /**
     * invokes the recommendation request
     *
     * @param breinRecommendation contains the breinRecommendation object
     * @return result of request or null
     */
    public BreinResult invokeRecommendation(final BreinRecommendation breinRecommendation) {

        if (breinRecommendation != null) {
            return restEngine.doRecommendation(breinRecommendation);
        }

        return null;
    }

    /**
     * returns the brein engine
     *
     * @return engine itself
     */
    public IRestEngine getRestEngine() {
        return restEngine;
    }

    /**
     * configuration of engine
     *
     * @param breinConfig configuration object
     */
    public void configure(final BreinConfig breinConfig) {
        restEngine.configure(breinConfig);
    }

    /**
     * performs a temporalData request
     *
     * @param breinTemporalData contains the appropriate data in order to perform the request
     * @return result from Breinify engine
     */
    public BreinResult performTemporalDataRequest(final BreinTemporalData breinTemporalData) {

        if (breinTemporalData != null) {
            return restEngine.doTemporalDataRequest(breinTemporalData);
        }

        return null;
    }
}
