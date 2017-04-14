package com.brein.engine;

import com.brein.api.BreinBase;
import com.brein.domain.BreinConfig;
import com.brein.domain.BreinResult;

import java.util.Collections;
import java.util.function.Consumer;

/**
 * could be the jersey rest engine implementation
 */
public class DummyRestEngine implements IRestEngine {

    @Override
    public void invokeAsyncRequest(final BreinConfig config,
                                   final BreinBase data,
                                   final Consumer<BreinResult> callback) {
        callback.accept(invokeRequest(config, data));
    }

    /**
     * stops possible functionality (e.g. threads)
     */
    @Override
    public void terminate() {
    }

    @Override
    public BreinResult invokeRequest(final BreinConfig config,
                                     final BreinBase data) {
        validate(config, data);
        getFullyQualifiedUrl(config, data);

        return new BreinResult(Collections.emptyMap());
    }

    /**
     * configuration of the rest  client
     */
    @Override
    public void configure(final BreinConfig breinConfig) {
    }

}
