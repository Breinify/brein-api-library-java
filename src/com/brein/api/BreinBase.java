package com.brein.api;

import com.brein.domain.BreinBaseRequest;
import com.brein.domain.BreinConfig;
import com.brein.domain.BreinUser;
import com.brein.engine.BreinEngine;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.Instant;
import java.util.Map;
import java.util.function.Function;

/**
 * Base Class for activity and lookup operations.
 */
public class BreinBase implements ISecretStrategy {

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
    private long unixTimestamp = 0;

    /**
     * IpAddress
     */
    private String ipAddress;

    /**
     * contains the data structures for the base part
     */
    private final BreinBaseRequest breinBaseRequest = new BreinBaseRequest();

    /**
     * contains the errorCallback
     */
    private Function<String, Void> errorCallback;

    /**
     * Builder for json creation
     */
    final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
            .create();

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
     *
     * @param breinConfig object
     */
    public BreinBase setConfig(final BreinConfig breinConfig) {
        this.breinConfig = breinConfig;
        return this;
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
     * ~
     *
     * @param breinUser user data
     */
    public BreinBase setBreinUser(final BreinUser breinUser) {
        this.breinUser = breinUser;
        return this;
    }

    /**
     * returns the configured brein engine
     *
     * @return brein engine
     */
    public BreinEngine getBreinEngine() {
        return null == breinConfig ? null : getConfig().getBreinEngine();
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
     * retrieves the timestamp
     *
     * @return value from 1.1.1970
     */
    public long getUnixTimestamp() {
        return unixTimestamp;
    }

    /**
     * sets the timestamp
     *
     * @param unixTimestamp value from 1.1.1970
     */
    public BreinBase setUnixTimestamp(final long unixTimestamp) {
        this.unixTimestamp = unixTimestamp;
        return this;
    }

    /**
     * retrieves the sign flag
     *
     * @return true or false depending on configured secret
     */
    public boolean isSign() {
        if (breinConfig == null) {
            return false;
        }

        if (breinConfig.getSecret() == null) {
            return false;
        }

        return breinConfig.getSecret().length() > 0;
    }

    /**
     * gets the ipAddress
     *
     * @return content of ipAddress
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * sets the ipaddress
     *
     * @param ipAddress contains the ipAddress
     * @return object itself
     */
    public BreinBase setIpAddress(final String ipAddress) {
        this.ipAddress = ipAddress;
        return this;
    }

    /**
     * Returns the callback function
     *
     * @return callback function
     */
    public Function<String, Void> getErrorCallback() {
        return errorCallback;
    }

    /**
     * sets the error callback function
     *
     * @param errorCallback function to callback
     */
    public BreinBase setErrorCallback(final Function<String, Void> errorCallback) {
        this.errorCallback = errorCallback;
        return this;
    }

    /**
     * return the gson builder instance
     *
     * @return gson instance
     */
    public Gson getGson() {
        return gson;
    }

    /**
     * return the instance of BreinBaseRequestData
     *
     * @return instance of BreinBaseRequestData
     */
    public BreinBaseRequest getBreinBaseRequest() {
        return breinBaseRequest;
    }

    /**
     * sets the base map
     *
     * @param dataMap contains the map
     * @return self
     */
    public BreinBase setBaseMap(final Map<String, Object> dataMap) {
        getBreinBaseRequest().setBaseMap(dataMap);
        return this;
    }

    /**
     * Initializes all values
     */
    public void init() {
        breinUser = null;
        breinConfig = null;
        unixTimestamp = 0;
    }

    /**
     * prepares the json request string
     *
     * @return empty
     */
    public String prepareJsonRequest() {

        if (this.getUnixTimestamp() == 0) {
            setUnixTimestamp(Instant.now().getEpochSecond());
        }
        return "";
    }

    /**
     * used to create the signature depending of the request type
     *
     * @return signature
     */
    @Override
    public String createSignature() {
        return null;
    }
}
