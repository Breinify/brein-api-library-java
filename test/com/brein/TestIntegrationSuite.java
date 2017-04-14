package com.brein;

import com.brein.api.TestActivityApiWithSecret;
import com.brein.api.TestActivityApiWithUnirest;
import com.brein.api.TestActivityApiWithJersey;
import com.brein.api.TestConcurrencyWithJersey;
import com.brein.api.TestConcurrencyWithSecret;
import com.brein.api.TestConcurrencyWithUniRest;
import com.brein.api.TestLookupApi;
import com.brein.api.TestStressTests;
import com.brein.api.TestTemporalDataApiWithUnirest;
import com.brein.engine.TestEngine;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        TestActivityApiWithUnirest.class,
        TestActivityApiWithJersey.class,
        TestActivityApiWithSecret.class,
        //TestTemporalDataApiWithUnirest.class,
        TestLookupApi.class,
        TestEngine.class,
        TestStressTests.class,
        TestConcurrencyWithUniRest.class,
        TestConcurrencyWithJersey.class,
        TestConcurrencyWithSecret.class
})
public class TestIntegrationSuite {
}
