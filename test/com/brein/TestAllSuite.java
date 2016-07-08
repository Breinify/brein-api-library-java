package com.brein;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Test suite for all java api  tests
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        TestSuite.class,
        TestIntegrationSuite.class
})
public class TestAllSuite {
}
