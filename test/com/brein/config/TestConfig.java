package com.brein.config;

import com.brein.api.BreinActivity;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test of configuration
 */
public class TestConfig {

    /**
     * Test of Breinify class with empty configuration api-key
     */
    @Test
    public void testEmptyConfig() {

        final String emptyString = "";
        BreinConfig breinConfig = new BreinConfig();
        breinConfig.setApiKey(emptyString);

        final BreinActivity breinActivity = new BreinActivity();
        breinActivity.setConfig(breinConfig);
        assertFalse(breinActivity.validApiKey());
    }

    /**
     * Test of Breinify class with null configuration api-key
     */
    @Test
    public void testNullConfig() {

        BreinConfig breinConfig = new BreinConfig();
        breinConfig.setApiKey(null);
        final BreinActivity breinActivity = new BreinActivity();
        breinActivity.setConfig(breinConfig);
        assertFalse(breinActivity.validApiKey());
    }

    /**
     * This should be the normal configuration methods
     */
    @Test
    public void testNormalConfigUsage() {

        BreinConfig breinConfig = new BreinConfig();
        final String validApiKey = "9D9C-C9E9-BC93-4D1D-9A61-3A0F-9BD9-CF14";
        breinConfig.setApiKey(validApiKey);

        final BreinActivity breinActivity = new BreinActivity();
        breinActivity.setConfig(breinConfig);
        assertTrue(breinActivity.validApiKey());
    }
}


