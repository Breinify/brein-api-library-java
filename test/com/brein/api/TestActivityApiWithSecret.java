package com.brein.api;

import com.brein.domain.BreinConfig;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This test cases shows how to use the  activity
 */
public class TestActivityApiWithSecret extends TestActivityApiWithUnirest {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestActivityApiWithSecret.class);

    @Before
    public void setUp() {
        Breinify.setConfig(new BreinConfig(VALID_API_KEY_FOR_SECRET, VALID_SECRET));
    }
}
