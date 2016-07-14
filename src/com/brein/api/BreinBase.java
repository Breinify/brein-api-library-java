package com.brein.api;

import com.brein.domain.BreinConfig;
import com.brein.domain.BreinUser;
import com.brein.engine.BreinEngine;

import java.time.Instant;

/**
 * Base Class for activity and lookup operations.
 *
 */
public class BreinBase {

    /**
     * contains the User that will be used for the request
     */
    private BreinUser breinUser;

    /**
     * Configuration
     */
    private BreinConfig breinConfig;

    /**
     * contains the timestamp when the request will be generated
     */
    private long unixTimestamp;

    /**
     * if set to yes then a secret has to bo sent
     */
    private boolean sign;

    /**
     * retrieves the configuration
     *
     * @return brein config
     */
    public BreinConfig getConfig() {
        return breinConfig;
    }

    /**
     * sets the brein config
     * @param breinConfig object
     */
    public void setConfig(final BreinConfig breinConfig) {
        this.breinConfig = breinConfig;
    }

    /**
     * retrieves the breinuser
     *
     * @return breinuser
     */
    public BreinUser getBreinUser() {
        return breinUser;
    }

    /**
     * sets the brein user
     *~
     * @param breinUser user data
     */
    public void setBreinUser(final BreinUser breinUser) {
        this.breinUser = breinUser;
    }

    /**
     * returns the configured brein engine
     * @return brein engine
     */
    public BreinEngine getBreinEngine() {
        return null == breinConfig ? null : getConfig().getBreinEngine();
    }

    /**
     * prepares the json request string
     * @return empty
     */
    public String prepareJsonRequest() {

        setUnixTimestamp(Instant.now().getEpochSecond());

        return "";
    }

    /**
     * retrieves the endpoint. this depends of the kind of BreinBase type.
     *
     * @return endpoint
     */
    public String getEndPoint() {
        return "";
    }

    /**
     *
     * @return
     */
    public long getUnixTimestamp() {
        return unixTimestamp;
    }

    /**
     * retrieves the timestamp
     *
     * @param unixTimestamp
     */
    public void setUnixTimestamp(final long unixTimestamp) {
        this.unixTimestamp = unixTimestamp;
    }

    /**
     * retrieves the sign flag
     *
     * @return flag (true / false)
     */
    public boolean isSign() {
        return sign;
    }

    /**
     * sets the sign flag
     *
     * @param sign value to set
     */
    public void setSign(final boolean sign) {
        this.sign = sign;
    }
}
