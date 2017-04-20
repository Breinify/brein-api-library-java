package com.brein;

import com.brein.domain.TestDomain;
import com.brein.domain.results.TestBreinTemporalDataResult;
import com.brein.domain.results.temporaldataparts.TestBreinEventResult;
import com.brein.domain.results.temporaldataparts.TestBreinHolidayResult;
import com.brein.domain.results.temporaldataparts.TestBreinLocationResult;
import com.brein.domain.results.temporaldataparts.TestBreinWeatherResult;
import com.brein.util.TestJsonHelper;
import com.brein.util.TestUtil;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Test suite for all java api  tests
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        TestDomain.class,
        TestUtil.class,
        TestJsonHelper.class,
        TestBreinTemporalDataResult.class,
        TestBreinWeatherResult.class,
        TestBreinLocationResult.class,
        TestBreinHolidayResult.class,
        TestBreinEventResult.class
})
public class TestSuite {
}
