package com.brein.config;

import com.brein.api.BreinActivity;
import com.brein.api.BreinInvalidConfigurationException;
import com.brein.domain.BreinConfig;
import com.brein.engine.BreinEngineType;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Objects;
import java.util.Properties;

import static org.junit.Assert.*;

/**
 * Test of configuration
 */
public class TestConfig {
    private static final String testApiKey = "TEST-API-KEY";

    /**
     * Init part
     */
    @BeforeClass
    public static void setUp() {

        // set logging on
        final Properties props = System.getProperties();
        props.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "DEBUG");

    }

    /**
     * Test of Breinify class with empty configuration api-key
     */
    @Test
    public void testEmptyConfig() {

        final String emptyString = "";
        final BreinConfig breinConfig = new BreinConfig();
        breinConfig.setApiKey(emptyString);

        final BreinActivity breinActivity = new BreinActivity();
        breinActivity.setConfig(breinConfig);
        assertNull(breinActivity.getConfig().getApiKey());
    }

    /**
     * Test of Breinify class with null configuration api-key
     */
    @Test
    public void testNullConfig() {

        final BreinConfig breinConfig = new BreinConfig();
        breinConfig.setApiKey(null);
        final BreinActivity breinActivity = new BreinActivity();
        breinActivity.setConfig(breinConfig);
        assertNull(breinActivity.getConfig().getApiKey());
    }

    /**
     * This should be the normal configuration  methods
     */
    @Test
    public void testNormalConfigUsage() {

        final BreinConfig breinConfig = new BreinConfig();
        final String validApiKey = "9D9C-C9E9-BC93-4D1D-9A61-3A0F-9BD9-CF14";
        breinConfig.setApiKey(validApiKey);

        final BreinActivity breinActivity = new BreinActivity();
        breinActivity.setConfig(breinConfig);
        assertTrue(!Objects.equals(breinActivity.getConfig().getApiKey(), ""));
    }

    /**
     * Test a config with a wrong url
     */
    @Test(expected = BreinInvalidConfigurationException.class)
    public void testConfigWithWrongUrl() {

        final String wrongUrl = "https://breeeeeinify.com";

        final BreinConfig breinConfig = new BreinConfig(testApiKey,
                wrongUrl,
                BreinEngineType.UNIREST_ENGINE);

        final boolean isValid = breinConfig.isUrlValid(wrongUrl);
        assertFalse(isValid);
    }

    /**
     * Test a config with a correct url
     */
    @Test
    public void testConfigWithCorrectUrl() {

        final String correctUrl = "http://google.com";

        final BreinConfig breinConfig = new BreinConfig(testApiKey,
                correctUrl,
                BreinEngineType.UNIREST_ENGINE);

        final boolean isValid = breinConfig.isUrlValid(correctUrl);
        assertTrue(isValid);
    }

    /**
     * Tests if both Breinify URL's are reachable:
     *  https://api.breinify.com
     *  http://api.breinify.com
     */
    @Test
    public void testBreinifyUrls() {

        final String httpsUrl = "https://api.breinify.com";
        final String httpUrl = "http://api.breinify.com";

        // HTTPS
        final BreinConfig breinConfigHttps = new BreinConfig(testApiKey,
                httpsUrl,
                BreinEngineType.UNIREST_ENGINE);
        final boolean isHttpsValid = breinConfigHttps.isUrlValid(httpsUrl);
        assertTrue(isHttpsValid);

        // HTTP
        final BreinConfig breinConfigHttp = new BreinConfig(testApiKey,
                httpUrl,
                BreinEngineType.UNIREST_ENGINE);
        final boolean isHttpValid = breinConfigHttps.isUrlValid(httpUrl);
        assertTrue(isHttpValid);
    }

}


