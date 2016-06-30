package com.brein;

import com.brein.activity.TestActivity;
import com.brein.config.TestConfig;
import com.brein.domain.TestDomain;
import com.brein.engine.TestEngine;
import com.brein.lookup.TestLookup;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Test suite for all java api tests
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        TestLookup.class,
        TestActivity.class,
        TestConfig.class,
        TestDomain.class,
        TestEngine.class
})
public class TestSuite {
}
