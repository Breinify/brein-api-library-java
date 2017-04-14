package com.brein.api;

import com.brein.domain.BreinResult;

public interface IExecutable<R extends BreinResult> {

    R execute();
}
