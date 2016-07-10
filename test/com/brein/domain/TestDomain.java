package com.brein.domain;


import com.brein.api.BreinActivity;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Test classes for the domain objects
 */
public class TestDomain {

    /**
     * creates a brein request object that will be used within the body
     * of the request
     */
    @Test
    public void testBreinRequest() {
        BreinConfig breinConfig = new BreinConfig();
        final String validApiKey = "9D9C-C9E9-BC93-4D1D-9A61-3A0F-9BD9-CF14";
        breinConfig.setApiKey(validApiKey);

        BreinUser breinUser = new BreinUser("m.recchioni@me.com");
        breinUser.setFirstName("Marco");
        breinUser.setLastName("Recchioni");

        BreinActivity breinActivity = new BreinActivity();
        breinActivity.setBreinUser(breinUser);
        breinActivity.setBreinActivityType(BreinActivityType.LOGIN);
        breinActivity.setDescription("Super-Description");
        breinActivity.setBreinCategoryType(BreinCategoryType.HOME);

        final String jsonOutput = breinActivity.prepareJsonRequest();
        assertTrue(jsonOutput.length() > 0);
    }

    /**
     * creates a brein request object that will be used within the body
     * of the request but with less data
     */
    @Test
    public void testBreinRequsestWithLessData() {
        BreinConfig breinConfig = new BreinConfig();
        final String validApiKey = "9D9C-C9E9-BC93-4D1D-9A61-3A0F-9BD9-CF14";
        breinConfig.setApiKey(validApiKey);

        BreinUser breinUser = new BreinUser("m.recchioni@me.com");
        breinUser.setLastName("");

        BreinActivity breinActivity = new BreinActivity();
        breinActivity.setBreinUser(breinUser);
        breinActivity.setBreinActivityType(BreinActivityType.LOGIN);
        breinActivity.setDescription("Super-Description");
        breinActivity.setBreinCategoryType(BreinCategoryType.FOOD);

        final String jsonOutput = breinActivity.prepareJsonRequest();
        assertTrue(jsonOutput.length() > 0);
    }

}
