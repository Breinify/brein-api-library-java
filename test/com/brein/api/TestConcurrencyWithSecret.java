package com.brein.api;

import com.brein.domain.BreinConfig;
import org.junit.Before;

@SuppressWarnings("Duplicates")
public class TestConcurrencyWithSecret extends TestConcurrencyWithUniRest {

    @Before
    public void setUp() {
        Breinify.setConfig(new BreinConfig(VALID_API_KEY_FOR_SECRET, VALID_SECRET));
    }
}
