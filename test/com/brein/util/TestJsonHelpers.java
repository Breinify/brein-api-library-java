package com.brein.util;

import com.brein.domain.results.CommonResultConstants;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;

public class TestJsonHelpers {
    @Test
    public void testWithDefault() {
        Assert.assertEquals("test", JsonHelpers.getOr(Collections.singletonMap("key", "test"), "key", "miss"));
        Assert.assertEquals(2.5, JsonHelpers.getOr(Collections.singletonMap("key", "test"), "key2", 2.5), 0.001);

        Assert.assertEquals(1L, JsonHelpers.getOrLong(Collections.singletonMap("key", 1.0), "key"), 0.001);
        Assert.assertEquals(1L, JsonHelpers.getOrLong(Collections.singletonMap("key", 1.01), "key"), 0.001);
        Assert.assertEquals(CommonResultConstants.UNKNOWN_LONG,
                JsonHelpers.getOrLong(Collections.singletonMap("key", "test"), "key2"));
    }
}