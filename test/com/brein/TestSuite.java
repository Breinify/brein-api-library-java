package com.brein;

import com.brein.config.TestConfig;
import com.brein.domain.TestDomain;
import com.brein.domain.results.TestBreinTemporalDataResult;
import com.brein.domain.results.temporaldataparts.TestBreinWeatherResult;
import com.brein.util.TestBreinMapUtil;
import com.brein.util.TestJsonHelpers;
import com.brein.util.TestUtil;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Test suite for all java api  tests
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        TestConfig.class,
        TestDomain.class,
        TestUtil.class,
        TestJsonHelpers.class,
        TestBreinMapUtil.class,
        TestBreinTemporalDataResult.class,
        TestBreinWeatherResult.class
})
public class TestSuite {
}
