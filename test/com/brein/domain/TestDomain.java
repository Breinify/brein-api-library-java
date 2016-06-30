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

        BreinUser breinUser = new BreinUser("m.recchioni@me.com");
        breinUser.setFirstName("Marco");
        breinUser.setLastName("Recchioni");

        BreinActivity breinActivity = new BreinActivity();
        breinActivity.setBreinUser(breinUser);
        breinActivity.setApiKey("5d8b-064c-f007-4f92-a8dc-d06b-56b4-fad8");
        breinActivity.setBreinActivityType(BreinActivityType.LOGIN);
        breinActivity.setDescription("Super-Description");
        breinActivity.setBreinCategory(new BreinCategory("Super-Category"));

        final String jsonOutput = breinActivity.prepareJsonRequest();
        // System.out.println(jsonOutput);
        assertTrue(jsonOutput.length() > 0);

    }

    /**
     * creates a brein request object that will be used within the body
     * of the request but with less data
     */
    @Test
    public void testBreinRequsestWithLessData() {
        BreinUser breinUser = new BreinUser("m.recchioni@me.com");
        breinUser.setLastName("");

        BreinActivity breinActivity = new BreinActivity();
        breinActivity.setBreinUser(breinUser);
        breinActivity.setApiKey("5d8b-064c-f007-4f92-a8dc-d06b-56b4-fad8");
        breinActivity.setBreinActivityType(BreinActivityType.LOGIN);
        breinActivity.setDescription("Super-Description");
        breinActivity.setBreinCategory(new BreinCategory("Super-Category"));

        final String jsonOutput = breinActivity.prepareJsonRequest();
        // System.out.println(jsonOutput);
        assertTrue(jsonOutput.length() > 0);
    }

}
