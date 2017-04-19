package com.brein.api;

import com.brein.domain.BreinResult;

public interface IExecutable<R extends BreinResult> {

    /**
     * Method to execute the request synchronous.
     *
     * @return the returned value
     */
    R execute();
}
