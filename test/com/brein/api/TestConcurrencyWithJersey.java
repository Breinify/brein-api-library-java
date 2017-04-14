package com.brein.api;

import com.brein.domain.BreinCategoryType;
import com.brein.domain.BreinConfig;
import com.brein.domain.BreinResult;
import com.brein.domain.BreinUser;
import com.brein.engine.BreinEngineType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Supplier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SuppressWarnings("Duplicates")
public class TestConcurrencyWithJersey extends TestConcurrencyWithUniRest {

    @Before
    public void setUp() {
        Breinify.setConfig(new BreinConfig(VALID_API_KEY)
                .setRestEngineType(BreinEngineType.JERSEY_ENGINE));
    }
}
