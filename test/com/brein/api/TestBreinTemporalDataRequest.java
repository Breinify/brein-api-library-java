package com.brein.api;

import com.brein.domain.BreinConfig;
import com.brein.domain.BreinUser;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;

public class TestBreinTemporalDataRequest {
    @Test
    public void testBasic() {
        final BreinTemporalDataRequest req = new BreinTemporalDataRequest();
        req.setUnixTimestamp(123L);
        Assert.assertEquals("{\"unixTimestamp\":123}", req2String(req));

        req.setBreinUser(new BreinUser().setUserAgent("myuseragent").setAdditional(Collections.singletonMap("a", "b")));
        req.setConfig(new BreinConfig().setApiKey("aaaaaa"));

        Assert.assertEquals("{\"unixTimestamp\":123,\"apiKey\":\"aaaaaa\"," +
                "\"user\":{\"additional\":{\"a\":\"b\",\"userAgent\":\"myuseragent\"}}}", req2String(req));
    }

    @Test
    public void testLocations() {
        final BreinTemporalDataRequest req = new BreinTemporalDataRequest();
        req.setUnixTimestamp(123L);

        Assert.assertEquals("{\"unixTimestamp\":123,\"user\":{\"additional\":{\"location\":{\"latitude\":-1.0," +
                "\"longitude\":20.0}}}}", req2String(req.setLocation(-1, 20)));
        Assert.assertEquals("{\"unixTimestamp\":123,\"user\":{\"additional\":{\"location\":{\"country\":\"co\"," +
                "\"city\":\"c\",\"state\":\"s\"}}}}", req2String(req.setLocation("c", "s", "co")));
        Assert.assertEquals("{\"unixTimestamp\":123,\"user\":{\"additional\":{\"location\":{\"text\":\"city\"}}}}",
                req2String(req.setLocation(-1, 20).setLocation("city")));
        Assert.assertEquals("{\"unixTimestamp\":123,\"user\":{\"additional\":{\"location\":{\"text\":\"nyc\"}}}}",
                req2String(req.setLocation("nyc")));
    }

    protected String req2String(final BreinTemporalDataRequest req) {
        return req.prepareJsonRequest().replaceAll("\n", "").replaceAll(" ", "");
    }
}