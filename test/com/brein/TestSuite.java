package com.brein;

import com.brein.config.TestConfig;
import com.brein.domain.TestDomain;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Test suite for all java api  tests
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        TestConfig.class,
        TestDomain.class
})
public class TestSuite {
}
