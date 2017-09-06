package com.brein.api;

import com.brein.domain.BreinConfig;
import com.brein.domain.results.BreinTemporalDataResult;
import com.brein.engine.BreinEngineType;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;

import static com.brein.api.ApiTestBase.VALID_API_KEY;

public class TestTemporalIntegration {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestTemporalIntegration.class);

    @Test
    @Ignore
    public void testNetworkChangeBug() {
        //ignore this, the test doesn't fail as expected yet...
        Breinify.setConfig(new BreinConfig(VALID_API_KEY)
                .setRestEngineType(BreinEngineType.JERSEY_ENGINE));

        Assert.assertNotNull(new BreinTemporalData().setLocation("san francisco").execute());

        JOptionPane.showMessageDialog(null, "Please disconnect from the internet");

        try {
            new BreinTemporalData().setLocation("san francisco").execute();
            Assert.fail("You didn't disconnect...");
        } catch (final Exception ex) {
            LOGGER.info("Got expected exception", ex);
        }

        JOptionPane.showMessageDialog(null, "Please reconnect from the internet");

        Assert.assertNotNull(new BreinTemporalData().setLocation("san francisco").execute());
    }

    @Test
    public void testEmptyTemporalData() {
        Breinify.setConfig(new BreinConfig(VALID_API_KEY));

        Assert.assertNotNull(new BreinTemporalData().execute());
    }

    @Test
    public void testFreeflowingTimeString() {
        Breinify.setConfig(new BreinConfig(VALID_API_KEY));

        final BreinTemporalDataResult nycTime = new BreinTemporalData()
                .setLocation("nyc")
                .setLocalDateTime("2017-03-14 11:00:00")
                .execute();

        final BreinTemporalDataResult sfTime = new BreinTemporalData()
                .setLocation("San Francisco")
                .setLocalDateTime("2017-03-14 11:00:00")
                .execute();

        Assert.assertEquals(nycTime.getEpochDateTime().plusHours(3), sfTime.getEpochDateTime());
    }
}
