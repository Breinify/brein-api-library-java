package com.brein.engine;

import com.brein.api.ApiTestBase;
import com.brein.api.BreinActivity;
import com.brein.api.Breinify;
import com.brein.domain.BreinActivityType;
import com.brein.domain.BreinCategoryType;
import com.brein.domain.BreinUser;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Run some tests for the UNIREST API
 */
public class TestEngine extends ApiTestBase {

    @Test
    public void testBreinAsyncCallback() {

        final BreinUser user = new BreinUser()
                .setFirstName("User")
                .setLastName("Name")
                .setEmail("user.name@me.com");

        final BreinActivity activity = new BreinActivity()
                .setUser(user)
                .setActivityType(BreinActivityType.LOGIN)
                .setDescription("Super-Description")
                .setCategory(BreinCategoryType.EDUCATION);

        asyncTest(cb -> Breinify.activity(activity, res -> {
            assertEquals(200, res.getStatus());
            cb.set(true);
        }), 2000);
    }
}
