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
        final BreinActivity breinActivity = new BreinActivity();
        breinActivity.setApiKey(emptyString);
        assertFalse(breinActivity.validApiKey());
    }

    /**
     * Test of Breinify class with null configuration api-key
     */
    @Test
    public void testNullConfig() {

        final BreinActivity breinActivity = new BreinActivity();
        breinActivity.setApiKey(null);
        assertFalse(breinActivity.validApiKey());
    }

    /**
     * This should be the normal configuration methods
     */
    @Test
    public void testNormalConfigUsage() {
        BreinActivity breinActivity = new BreinActivity();
        final String validApiKey = "9D9C-C9E9-BC93-4D1D-9A61-3A0F-9BD9-CF14";
        breinActivity.setApiKey(validApiKey);
        assertTrue(breinActivity.validApiKey());
    }
}


