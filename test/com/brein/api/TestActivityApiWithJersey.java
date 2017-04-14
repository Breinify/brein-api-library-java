package com.brein.api;

import com.brein.domain.BreinConfig;
import com.brein.engine.BreinEngineType;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestActivityApiWithJersey extends TestActivityApiWithUnirest {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestActivityApiWithUnirest.class);

    @Before
    public void setUp() {
        Breinify.setConfig(new BreinConfig(VALID_API_KEY)
                .setRestEngineType(BreinEngineType.JERSEY_ENGINE));
    }
}
