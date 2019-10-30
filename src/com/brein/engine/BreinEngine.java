package com.brein.engine;

import com.brein.api.BreinBase;
import com.brein.domain.BreinConfig;
import com.brein.domain.BreinResult;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

/**
 * Creates the Rest Engine and provides the methods to invoke activity and lookup calls
 */
public class BreinEngine {

    private static final Map<BreinEngineType, IRestEngine> ENGINES = new ConcurrentHashMap<>();
    private static final Lock ENGINES_LOCK = new ReentrantLock();

    private static class SingletonHolder {
        static final BreinEngine INSTANCE = new BreinEngine();
    }

    /**
     * The {@code BreinEngine} class is a singleton, which can actually be accessed by this method. Creating a new
     * instance of a {@code BreinEngine} does not actually make any differences, since all attributes are constants,
     * i.e., {@code static} and {@code final}.
     *
     * @return the singleton instance of the {@code BreinEngine}
     */
    public static BreinEngine instance() {
        return SingletonHolder.INSTANCE;
    }

    public BreinResult invoke(final BreinConfig config, final BreinBase data) {
        return getEngine(config).invokeRequest(config, data);
    }

    public void invokeAsync(final BreinConfig config, final BreinBase data, final Consumer<BreinResult> callback) {
        getEngine(config).invokeAsyncRequest(config, data, callback);
    }

    protected IRestEngine getEngine(final BreinConfig config) {
        final BreinEngineType engineType = IRestEngine.getRestEngineType(config.getRestEngineType());
        IRestEngine engine = ENGINES.get(engineType);

        if (engine == null) {

            ENGINES_LOCK.lock();
            try {
                engine = ENGINES.get(engineType);

                if (engine == null) {
                    engine = IRestEngine.getRestEngine(engineType);
                    engine.configure(config);

                    ENGINES.put(engineType, engine);
                }
            } finally {
                ENGINES_LOCK.unlock();
            }
        }

        return engine;
    }

    public void terminate() {
        ENGINES_LOCK.lock();
        try {
            ENGINES.forEach((key, engine) -> engine.terminate());
            ENGINES.clear();
        } finally {
            ENGINES_LOCK.unlock();
        }
    }
}
