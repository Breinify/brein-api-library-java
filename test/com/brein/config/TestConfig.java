package com.brein.config;

import com.brein.Breinify;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestConfig {

    /**
     * Test of Breinify class with empty configuration api-key
     */
    @Test
    public void testEmptyConfig() {

        final String emptyString = "";
        final Breinify breinify = new Breinify(emptyString);
        assertFalse(breinify.validConfig());
    }

    /**
     * Test of Breinify class with null configuration api-key
     */
    @Test
    public void testNullConfig() {

        final Breinify breinify = new Breinify(null);
        assertFalse(breinify.validConfig());
    }

    /**
     * Test of Breinify class with valid configuration api-key
     */
    @Test
    public void testValidConfig() {

        final String validApiKey = "9D9C-C9E9-BC93-4D1D-9A61-3A0F-9BD9-CF14";
        final Breinify breinify = new Breinify(validApiKey);
        assertTrue(breinify.validConfig());
    }

    /**
     * This should be the normal configuration methods
     */
    @Test
    public void testNormalConfigUsage() {
        Breinify breinify = new Breinify();
        final String validApiKey = "9D9C-C9E9-BC93-4D1D-9A61-3A0F-9BD9-CF14";
        breinify.setConfig(validApiKey);
        assertTrue(breinify.validConfig());
    }
}


