package com.brein.domain;


import com.brein.api.BreinActivity;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

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
        breinActivity.setApiKey("5d8b-064c-f007-4f92-a8dc-d06b-56b4-fad8");
        breinActivity.setActivityType(ActivityType.LOGIN);
        breinActivity.setDescription("Super-Description");
        breinActivity.setCategory(new Category("Super-Category"));

        final long unixTimestamp = System.currentTimeMillis() / 1000L;
        final BreinRequest breinRequest = new BreinRequest(breinActivity, unixTimestamp);
        final String jsonOutput = breinRequest.toJson();
        // System.out.println(jsonOutput);
        assertTrue(jsonOutput.length() > 0);

    }

}
