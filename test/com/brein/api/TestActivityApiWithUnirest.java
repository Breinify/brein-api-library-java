package com.brein.api;

import com.brein.domain.BreinConfig;
import com.brein.engine.BreinEngineType;
import com.brein.util.MapBuilder;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;

/**
 * This test cases shows how to use the  activity
 */
public class TestActivityApiWithUnirest extends ApiTestBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestActivityApiWithUnirest.class);

    @Before
    public void setUp() {
        Breinify.setConfig(new BreinConfig(VALID_API_KEY)
                .setRestEngineType(BreinEngineType.UNIREST_ENGINE));
    }

    /**
     * testcase how to use the activity api
     */
    @Test
    public void testLogin() {
        final String description = "Login-Description";

        this.user.setFirstName("Marco");
        this.user.setLastName("Recchioni");

        asyncTest(cb -> Breinify.activity(this.user, "testLogin", this.category, description, res -> {
            assertEquals(200, res.getStatus());
            cb.set(true);
        }), 2000);
    }

    /**
     * test case without category type
     */
    @Test
    public void testWithoutCategoryTypeSet() {
        final String description = "Login-Description";

        this.user.setFirstName("Marco");
        this.user.setLastName("Recchioni");

        asyncTest(cb -> Breinify.activity(this.user, "testWithoutCategoryTypeSet", null, description, res -> {
            assertEquals(200, res.getStatus());
            cb.set(true);
        }), 2000);
    }

    /**
     * test case how to invoke logout activity
     */
    @Test
    public void testLogout() {
        final String description = "Logout-Description";
        this.user.setDateOfBirth(12, 31, 2008);

        asyncTest(cb -> Breinify.activity(this.user, "testLogout", this.category, description, res -> {
            assertEquals(200, res.getStatus());
            cb.set(true);
        }), 2000);
    }

    /**
     * test case how to invoke search activity
     */
    @Test
    public void testSearch() {
        final String description = "Search-Description";

        asyncTest(cb -> Breinify.activity(this.user, "testSearch", this.category, description, res -> {
            assertEquals(200, res.getStatus());
            cb.set(true);
        }), 2000);
    }

    /**
     * test case how to invoke add-to-cart activity
     */
    @Test
    public void testAddToCart() {
        final String description = "AddToCart-Description";

        asyncTest(cb -> Breinify.activity(this.user, "testAddToCart", this.category, description, res -> {
            assertEquals(200, res.getStatus());
            cb.set(true);
        }), 2000);
    }

    /**
     * test case how to invoke remove-from-cart activity
     */
    @Test
    public void testRemoveFromCart() {
        final String description = "RemoveFromCart-Description";

        asyncTest(cb -> Breinify.activity(this.user, "testRemoveFromCart", this.category, description,
                res -> {
                    assertEquals(200, res.getStatus());
                    cb.set(true);
                }), 2000);
    }

    /**
     * test case how to invoke select product
     */
    @Test
    public void testSelectProduct() {
        final String description = "Select-Product-Description";

        asyncTest(cb -> Breinify.activity(this.user, "testSelectProduct", this.category, description,
                res -> {
                    assertEquals(200, res.getStatus());
                    cb.set(true);
                }), 2000);
    }

    /**
     * test case how to invoke other
     */
    @Test
    public void testOther() {
        final String description = "Other-Description";
        asyncTest(cb -> Breinify.activity(this.user, "testOther", this.category, description, res -> {
            assertEquals(200, res.getStatus());
            cb.set(true);
        }), 2000);
    }

    /**
     * Invoke a test call with 200 logins
     */
    @Test
    public void testMultiple() {
        final int maxLogin = 200;
        for (int index = 0; index < maxLogin; index++) {
            final String description = "testMultiple";

            this.user.setFirstName("Marco");
            this.user.setLastName("Recchioni");

            asyncTest(cb -> Breinify.activity(this.user, "testMultiple", this.category, description, res -> {
                assertEquals(200, res.getStatus());
                cb.set(true);
            }), 2000);
        }
    }

    @Test
    public void testUsageOfM() {
        asyncTest(cb -> Breinify.activity(new MapBuilder<String>().set("firstName", "Marco"), "login", res -> {
            assertEquals(200, res.getStatus());
            cb.set(true);
        }), 2000);
    }
}
