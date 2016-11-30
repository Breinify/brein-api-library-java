package com.brein.api;


import com.brein.domain.BreinConfig;
import com.brein.domain.BreinResult;
import com.brein.domain.BreinUser;
import com.brein.test.TestHelper;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;


public class TestConcurrency {

    private static final String VALID_API_KEY = "41B2-F48C-156A-409A-B465-317F-A0B4-E0E9";

    @Test
    public void testTemporalConcurrency() {

        Breinify.setConfig(new BreinConfig(VALID_API_KEY));

        TestHelper.threadTesting(100, () -> {
            for (int ct = 0; ct < 1000; ct++) {

                System.out.println("Invoking Thread: " + ct);

                final Map<String, Object> locationAdditionalMap = new HashMap<>();
                final Map<String, Object> locationValueMap = new HashMap<>();

                locationValueMap.put("latitude", Math.random() * 10 + 39 - 5);
                locationValueMap.put("longitude", Math.random() * 50 - 98 - 25);
                locationAdditionalMap.put("location", locationValueMap);

                final BreinUser localBreinUser = new BreinUser()
                        .setEmail("fred.firestone@email.com")
                        .setIpAddress("127.0.0.1")
                        .setAdditionalMap(locationAdditionalMap);

                // invoke temporalData
                final BreinResult response = Breinify.temporalData(localBreinUser);
                System.out.println(response);

                // Assert.assertNotNull(response);
            }
        });

    }

}
