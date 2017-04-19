package com.brein.api;

import com.brein.domain.BreinConfig;
import com.brein.engine.BreinEngineType;
import org.junit.Before;

@SuppressWarnings("Duplicates")
public class TestConcurrencyWithJersey extends TestConcurrencyWithUniRest {

    @Before
    public void setUp() {
        Breinify.setConfig(new BreinConfig(VALID_API_KEY)
                .setRestEngineType(BreinEngineType.JERSEY_ENGINE));
    }
}
