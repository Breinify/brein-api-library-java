package com.brein.api;

import com.brein.domain.BreinConfig;
import com.brein.domain.BreinUser;
import com.brein.util.BreinUtil;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * Base Class for activity and lookup operations.
 */
public abstract class BreinBase<T extends BreinBase> implements ISecretStrategy {
    public final static String API_KEY_FIELD = "apiKey";
    public final static String UNIX_TIMESTAMP_FIELD = "unixTimestamp";
    public final static String SIGNATURE_FIELD = "signature";
    public final static String SIGNATURE_TYPE_FIELD = "signatureType";

    /**
     * Builder for json creation
     */
    public static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
            .create();

    /**
     * This list may not be complete it just contains some values. For a complete list it is recommended to look at the
     * API documentation.
     */
    public enum BaseField {
        IP_ADDRESS("ipAddress"),
        UNIX_TIMESTAMP("unixTimestamp");

        final String name;

        BaseField(final String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void set(final BreinBase base, final Object value) {
            base.set(getName(), value);
        }
    }

    /**
     * contains the User that will be used for the request
     */
    private BreinUser user;

    /**
     * contains a map for the base section
     */
    private Map<String, Object> baseMap;

    /**
     * retrieves the breinuser
     *
     * @return breinuser
     */
    public BreinUser getUser() {
        if (this.user == null) {
            this.user = new BreinUser();
        }

        return this.user;
    }

    /**
     * sets the brein user
     *
     * @param user user data
     *
     * @return self
     */
    public T setUser(final BreinUser user) {
        this.user = user;
        return getThis();
    }

    public T setUser(final String key, final Object value) {
        this.getUser().set(key, value);
        return getThis();
    }

    public T setAdditional(final String key, final Object value) {
        this.getUser().setAdditional(key, value);
        return getThis();
    }

    /**
     * retrieves the endpoint. this depends of the kind of BreinBase type.
     *
     * @param config the current configuration
     *
     * @return endpoint
     */
    public abstract String getEndPoint(final BreinConfig config);

    /**
     * retrieves the timestamp
     *
     * @return value from 1.1.1970
     */
    public long getUnixTimestamp() {
        final Object unixTimestamp = getBaseField(BaseField.UNIX_TIMESTAMP);
        if (unixTimestamp == null || !Long.class.isInstance(unixTimestamp) || unixTimestamp.equals(-1L)) {
            return -1L;
        } else {
            return Long.class.cast(unixTimestamp);
        }
    }

    /**
     * sets the timestamp
     *
     * @param unixTimestamp value from 1.1.1970
     *
     * @return self
     */
    public T setUnixTimestamp(final long unixTimestamp) {
        BaseField.UNIX_TIMESTAMP.set(this, unixTimestamp);
        return getThis();
    }

    /**
     * gets the ipAddress
     *
     * @return content of ipAddress
     */
    public String getIpAddress() {
        return getBaseField(BaseField.IP_ADDRESS);
    }

    /**
     * sets the ipaddress
     *
     * @param ipAddress contains the ipAddress
     *
     * @return object itself
     */
    public T setClientIpAddress(final String ipAddress) {
        BaseField.IP_ADDRESS.set(this, ipAddress);
        return getThis();
    }

    /**
     * return the GSON builder instance
     *
     * @return GSON instance
     */
    public Gson getGson() {
        return GSON;
    }

    /**
     * Sets a value
     *
     * @return self
     */
    public T set(final String key, final Object value) {
        if (BreinUser.USER_FIELD.equalsIgnoreCase(key)) {
            throw new BreinException("The field '" + BreinUser.USER_FIELD + "' cannot be set, " +
                    "use the setUser method to do so.");
        } else if (this.baseMap == null) {
            this.baseMap = new HashMap<>();
        }

        this.baseMap.put(key, value);
        return getThis();
    }

    /**
     * prepares the request for the base section with standard fields
     * plus possible fields if configured
     *
     * @param requestData contains the created json structure
     */
    public abstract void prepareRequestData(final BreinConfig config, final Map<String, Object> requestData);

    public String prepareRequestData(final BreinConfig config) {
        final Map<String, Object> requestData = new HashMap<>();

        requestData.put(API_KEY_FIELD, config.getApiKey());

        // add the base values
        if (this.baseMap != null) {
            this.baseMap.forEach((key, value) -> {
                if (BreinUtil.containsValue(value)) {
                    requestData.put(key, value);
                }
            });
        }

        // we set the unixTimestamp (may be twice, but anyways)
        long timestamp = getUnixTimestamp();
        if (timestamp == -1L) {
            timestamp = Instant.now().getEpochSecond();
        }
        requestData.put(UNIX_TIMESTAMP_FIELD, timestamp);

        // check if we have user and add it
        if (this.user != null) {
            this.user.prepareRequestData(config, requestData);
        }

        // add the sub-type specific values
        prepareRequestData(config, requestData);

        // check if we have to add a signature
        if (config.isSign()) {
            requestData.put(SIGNATURE_FIELD, createSignature(config, requestData));
            requestData.put(SIGNATURE_TYPE_FIELD, "HmacSHA256");
        }

        return getGson().toJson(requestData);
    }

    @SuppressWarnings("unchecked")
    protected T getThis() {
        return (T) this;
    }

    protected <F> F getBaseField(final BaseField field) {
        if (baseMap == null) {
            return null;
        }

        //noinspection unchecked
        return (F) baseMap.get(field.getName());
    }

    @Override
    public String toString() {
        final BreinConfig config = new BreinConfig(null);
        return prepareRequestData(config);
    }
}
