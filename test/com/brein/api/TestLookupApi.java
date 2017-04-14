package com.brein.api;

import com.brein.domain.BreinDimension;
import com.brein.domain.BreinResult;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test cases for lookup functionality
 */
public class TestLookupApi extends ApiTestBase {

    /**
     * The Lookup itself
     */
    private final BreinLookup breinLookup = new BreinLookup();

    /**
     * Tests the lookup functionality
     */
    @Test
    public void testLookup() {

        final String[] dimensions = {
                "firstname", "gender", "age", "agegroup", "digitalfootprint", "images"
        };

        final BreinLookup data = new BreinLookup()
                .setUser(this.user)
                .setBreinDimension(new BreinDimension(dimensions));

        final BreinResult response = Breinify.lookUp(data);
        assertEquals(200, response.getStatus());
    }
}
