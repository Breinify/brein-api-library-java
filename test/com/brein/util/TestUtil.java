package com.brein.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestUtil {

    @Test
    public void testSignature() {

        assertEquals("h5HRhGRwWlRs9pscyHhQWNc7pxnDOwDZBIAnnhEQbrU=",
                BreinUtil.generateSignature("apiKey", "secretkey"));

        final String secret = BreinUtil.generateSecret(128);
        for (int i = 0; i < 100; i++) {

            // this is our test case, for the message 'apiKey' and secret 'secretKey'
            // it must return the used signature, the message should vary on each
            // request
            BreinUtil.generateSignature(BreinUtil.randomString(), secret);
        }
    }

    @Test
    public void testIOSSignature() {

        final String iOSMessage = "1486992560-2017-02-13 14:30:37 GMT+01:00 (MEZ)-Europe/Berlin";
        final String iOSSecret = "lmcoj4k27hbbszzyiqamhg==";
        final String iOSSignature = "oZxTFc1ZPpelBCoGVhRk0/3IMm9tEtwJd9LNDFrgtM0=";

        final String javaSignature = BreinUtil.generateSignature(iOSMessage, iOSSecret);
        System.out.println(javaSignature);
        assertEquals(iOSSignature, javaSignature);
    }
}
