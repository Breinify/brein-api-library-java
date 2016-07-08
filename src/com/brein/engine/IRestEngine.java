package com.brein.engine;

import com.brein.api.BreinActivity;
import com.brein.api.BreinException;
import com.brein.api.BreinLookup;
import com.brein.domain.BreinConfig;
import com.brein.domain.BreinResult;

/**
 * Interface for all possible rest  engines
 */
public interface IRestEngine {

    String HEADER_ACCESS = "accept";
    String HEADER_APP_JSON = "application/json";

    /**
     * configures the rest engine
     * @param breinConfig configuration object
     */
    void configure(final BreinConfig breinConfig);

    /**
     * invokes the post request
     *
     * @param breinActivity data
     */
    void doRequest(final BreinActivity breinActivity) throws BreinException;

    /**
     * performs a lookup and provides details
     *
     * @param breinLookup contains request data
     * @return response from Breinify
     */
    BreinResult doLookup(final BreinLookup breinLookup) throws BreinException;

    /**
     * terminates the rest engine
     *
     */
    void terminate();

    /**
     * Creates the requested Rest Engine.
     *
     * @param engine type of engine
     * @return created Rest-Engine
     */
    static IRestEngine getRestEngine(final BreinEngineType engine) {
        switch (engine) {
            case UNIREST_ENGINE:
                return new UniRestEngine();

            case JERSEY_ENGINE:
                return new JerseyRestEngine();

            default:
                throw new BreinException("no rest engine specified!");
        }
    }
}
