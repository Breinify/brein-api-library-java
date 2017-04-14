package com.brein.api;

import com.brein.domain.BreinConfig;
import com.brein.engine.BreinEngineType;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;

/**
 * This test cases shows how to use the  activity
 */
public class TestTemporalDataApiWithUnirest extends ApiTestBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestTemporalDataApiWithUnirest.class);

    @Before
    public void setUp() {
        Breinify.setConfig(new BreinConfig(VALID_API_KEY)
                .setRestEngineType(BreinEngineType.UNIREST_ENGINE));
    }


}
