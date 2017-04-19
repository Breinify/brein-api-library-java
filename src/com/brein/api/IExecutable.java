package com.brein.api;

import com.brein.domain.BreinResult;

@FunctionalInterface
public interface IExecutable<R extends BreinResult> {

    /**
     * Method to execute the request synchronous.
     *
     * @return the returned value
     */
    R execute();
}
