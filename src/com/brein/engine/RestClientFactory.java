package com.brein.engine;

/**
 * Provides an Rest Client Implementation depending of the requested
 * engine type.
 *
 * Currently the following Engines are supported:
 *
 */
public class RestClientFactory {

    /**
     * Creates the requested Rest Engine.
     *
     * @param engine type of engine
     * @return created Rest-Engine
     */
    public static IRestClient getRestEngine(final BreinEngineType engine) {
        switch (engine) {
            case UNIREST_ENGINE:
                return new UniRestEngine();

            case JERSEY_ENGINE:
                return new JerseyRestEngine();
            /**
             * I know that this is the same as UNIREST_ENGINE it
             * is the default value
             */
            default:
                return new UniRestEngine();
        }
    }

    /**
     * Ensure that no object will be created
     */
    private RestClientFactory() {}

}
