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
     * the possible engines...
     */
    public static final int UNIREST_ENGINE = 0;
    public static final int JERSEY_ENGINE  = 1;

    /**
     * Creates the requested Rest Engine.
     *
     * @param engine type of engine
     * @return created Rest-Engine
     */
    public static IRestClient getRestEngine(int engine) {
        switch (engine) {
            case UNIREST_ENGINE:
                return new UniRestEngine();

            case JERSEY_ENGINE:
                return new JerseyRestEngine();
            /**
             * I know that this is the same as UNIREST_ENGINE
             */
            default:
                return new UniRestEngine();

        }
    }

}
