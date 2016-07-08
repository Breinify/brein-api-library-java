package com.brein;

import com.brein.activity.TestActivity;
import com.brein.api.TestApi;
import com.brein.engine.TestEngine;
import com.brein.lookup.TestLookup;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Test suite for all java api  tests
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        TestApi.class,
        TestLookup.class,
        TestActivity.class,
        TestEngine.class
})
public class TestIntegrationSuite {
}
