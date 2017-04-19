package com.brein.api;

import com.brein.domain.BreinResult;

import java.util.function.Consumer;

public interface IAsyncExecutable<R extends BreinResult> {

    /**
     * Method to execute the request asynchronous without any callback.
     */
    default void execute() {
        execute(null);
    }

    /**
     * Method to execute the request asynchronous with a callback.
     *
     * @param callback the callback containing the response of the request, can be {@code null}
     */
    void execute(final Consumer<R> callback);
}
