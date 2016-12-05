package com.brein.api;

import com.brein.domain.BreinConfig;
import com.brein.domain.BreinUser;
import com.brein.engine.BreinEngine;
import com.brein.util.BreinMapUtil;
import com.brein.util.BreinUtil;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

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
     * contains the errorCallback
     */
    private Function<String, Void> errorCallback;

    /**
     * contains a map for the base section
     */
    private Map<String, Object> baseMap;

    /**
     * returns the map for the base section
     *
     * @return map <String, Object>
     */
    public Map<String, Object> getBaseMap() {
        return baseMap;
    }

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
     * sets an map for the base section
     *
     * @param baseMap map of <String, Object></String,>
     */
    public void setBaseMap(final Map<String, Object> baseMap) {
        this.baseMap = baseMap;
    }

    /**
     * prepares the request for the base section with standard fields
     * plus possible fields if configured
     *
     * @param breinBase   contains the appropriate request object
     * @param requestData contains the created json structure
     */
    public void prepareBaseRequestData(final BreinBase breinBase,
                                       final JsonObject requestData) {

        if (BreinUtil.containsValue(breinBase.getConfig())) {
            if (BreinUtil.containsValue(breinBase.getConfig().getApiKey())) {
                requestData.addProperty("apiKey", breinBase.getConfig().getApiKey());
            }
        }

        final BreinUser breinUser = breinBase.getBreinUser();

        if (BreinUtil.containsValue(breinUser.getIpAddress())) {
            requestData.addProperty("ipAddress", breinUser.getIpAddress());
        }

        requestData.addProperty("unixTimestamp", breinBase.getUnixTimestamp());

        // if sign is active
        if (breinBase.isSign()) {
            requestData.addProperty("signature", breinBase.createSignature());
            requestData.addProperty("signatureType", "HmacSHA256");
        }

        // check if there are further maps to add on base level
        if (baseMap != null && baseMap.size() > 0) {
            BreinMapUtil.fillMap(baseMap, requestData);
        }
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
     * Clones from base class
     *
     * @param source to clone from
     */
    public void cloneBase(final BreinBase source) {

        // set further data...
        this.setIpAddress(source.getIpAddress());
        this.setUnixTimestamp(source.getUnixTimestamp());

        // callback
        this.setErrorCallback(source.getErrorCallback());

        // configuration
        this.setConfig(source.getConfig());

        // clone user
        final BreinUser clonedUser = BreinUser.clone(source.getBreinUser());
        this.setBreinUser(clonedUser);

        // clone maps
        // a copy of all maps
        final Map<String, Object> baseMap = source.getBaseMap();
        final Map<String, Object> copyOfBaseMap = BreinMapUtil.copyMap(baseMap);
        this.setBaseMap(copyOfBaseMap);
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
