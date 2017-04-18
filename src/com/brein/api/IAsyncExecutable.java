package com.brein.api;

import com.brein.domain.BreinResult;

import java.util.function.Consumer;

public interface IAsyncExecutable<R extends BreinResult> {

    default void execute() {
        execute(null);
    }

    void execute(final Consumer<R> callback);
}
