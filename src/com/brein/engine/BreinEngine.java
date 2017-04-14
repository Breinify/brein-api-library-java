package com.brein.engine;

import com.brein.api.BreinBase;
import com.brein.domain.BreinConfig;
import com.brein.domain.BreinResult;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

/**
 * Creates the Rest Engine and provides the methods to invoke activity and lookup calls
 */
public class BreinEngine {

    private final Map<BreinEngineType, IRestEngine> engines = new HashMap<>();
    private final Lock enginesLock = new ReentrantLock();

    public BreinResult invoke(final BreinConfig config, final BreinBase data) {
        return getEngine(config).invokeRequest(config, data);
    }

    public void invokeAsync(final BreinConfig config, final BreinBase data, final Consumer<BreinResult> callback) {
        getEngine(config).invokeAsyncRequest(config, data, callback);
    }

    protected IRestEngine getEngine(final BreinConfig config) {
        final BreinEngineType engineType = config.getRestEngineType();

        IRestEngine engine = this.engines.get(engineType);
        if (engine == null) {

            enginesLock.lock();
            try {
                engine = this.engines.get(engineType);

                if (engine == null) {
                    engine = IRestEngine.getRestEngine(config.getRestEngineType());
                    engine.configure(config);

                    this.engines.put(engineType, engine);
                }
            } finally {
                enginesLock.unlock();
            }
        }

        return engine;
    }

    public void terminate() {
        enginesLock.lock();
        try {
            this.engines.forEach((key, engine) -> engine.terminate());
            this.engines.clear();
        } finally {
            enginesLock.unlock();
        }
    }
}
