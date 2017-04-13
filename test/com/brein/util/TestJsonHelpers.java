package com.brein.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;

public class TestJsonHelpers {
    @Test
    public void testWithDefault() {
        Assert.assertEquals("test", JsonHelpers.getOr(Collections.singletonMap("key", "test"), "key", "miss"));
        Assert.assertEquals(2.5, JsonHelpers.getOr(Collections.singletonMap("key", "test"), "key2", 2.5), 0.001);

        Assert.assertEquals(Long.valueOf(1L), JsonHelpers.getOrLong(Collections.singletonMap("key", 1.0), "key"));
        Assert.assertEquals(Long.valueOf(1L), JsonHelpers.getOrLong(Collections.singletonMap("key", 1.01), "key"));
        Assert.assertNull(JsonHelpers.getOrLong(Collections.singletonMap("key", "test"), "key2"));
    }
}