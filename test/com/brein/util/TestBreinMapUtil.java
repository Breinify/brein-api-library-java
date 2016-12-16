package com.brein.util;

import com.google.gson.JsonObject;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;

public class TestBreinMapUtil {
    /**
     * For a feature request: Let us send in tags that are a List of primitives
     */
    @Test
    public void testListValueToJson() {
        final JsonObject json = new JsonObject();

        BreinMapUtil.fillMap(Collections.singletonMap("sampleList", Collections.singletonList("sample element")), json);

        Assert.assertNotNull(json.get("sampleList"));
    }
}