package com.brein.api;

import com.brein.domain.BreinConfig;
import com.brein.domain.BreinUser;
import com.brein.domain.BreinUser.UserField;
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

    public static final String API_KEY_FIELD = "apiKey";
    public static final String UNIX_TIMESTAMP_FIELD = "unixTimestamp";
    public static final String IP_ADDRESS = "ipAddress";
    public static final String SIGNATURE_FIELD = "signature";
    public static final String SIGNATURE_TYPE_FIELD = "signatureType";

    /**
     * Builder for JSON creation
     */
    public static final Gson GSON = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
            .create();
    /**
     * Contains user information for the request
     */
    private BreinUser user;
    /**
     * The base data for the request
     */
    private Map<String, Object> baseMap;

    /**
     * The time of the event
     */
    private long unixTimestamp = -1;

    /**
     * The IP address where this was sent from
     */
    private String ipAddress;

    /**
     * Retrieves the current {@code BreinUser} for the request. This method never returns {@code null}, instead it
     * creates an empty {@code BreinUser} instance if none is available so far.
     *
     * @return the current {@code BreinUser} for the request
     */
    public BreinUser getUser() {
        if (this.user == null) {
            this.user = new BreinUser();
        }

        return this.user;
    }

    /**
     * Sets the {@code BreinUser} instance for the request. It is recommended to get the user (using {@code getUser()})
     * and manipulate the retrieved instance directly or to use the {@code setUser(key, value)} method to set user
     * specific data.
     *
     * @param user the {@code BreinUser} to set
     *
     * @return {@code this}
     *
     * @see BreinUser
     */
    public T setUser(final BreinUser user) {
        this.user = user;
        return getThis();
    }

    /**
     * Sets a specific data point for the user data of the request, i.e.:
     * <p>
     * <pre>
     *     {
     *         user: {
     *             'key': 'value'
     *         }
     *     }
     * </pre>
     * <p>
     * Typically, that would be, e.g., {@code email},
     * {@code sessionId}, and/or {@code userId}
     *
     * @param key   the value to be set (e.g., {@code "email"} or {@code "sessionId"})
     * @param value the value to be set for the specified key
     *
     * @return {@code this}
     */
    public T setUser(final String key, final Object value) {
        this.getUser().set(key, value);
        return getThis();
    }

    /**
     * Sets a specific data point for the user data of the request, i.e.:
     * <p>
     * <pre>
     *     {
     *         user: {
     *             'key': 'value'
     *         }
     *     }
     * </pre>
     * <p>
     * Typically, that would be, e.g., {@code email},
     * {@code sessionId}, and/or {@code userId}
     *
     * @param key   the value to be set (e.g., {@code UserField.EMAIL})
     * @param value the value to be set for the specified key
     *
     * @return {@code this}
     *
     * @see UserField
     */
    public T setUser(final UserField key, final Object value) {
        this.getUser().set(key.getName(), value);
        return getThis();
    }

    /**
     * Sets a specific data point for the additional part of the request, i.e.:
     * <p>
     * <pre>
     *     {
     *         user: {
     *              additional: {
     *                  'key': 'value'
     *              }
     *         }
     *     }
     * </pre>
     *
     * @param key   the value to be set (e.g., {@code "localDateTime"}, {@code "userAgent"}, {@code "referrer"} or
     *              {@code "timezone"})
     * @param value the value to be set for the specified key
     *
     * @return {@code this}
     */
    public T setAdditional(final String key, final Object value) {
        this.getUser().setAdditional(key, value);
        return getThis();
    }

    /**
     * Gets the endpoint to be used to send the request to
     *
     * @param config the current configuration
     *
     * @return the endpoint to be used to send the request to
     *
     * @see BreinConfig
     */
    public abstract String getEndPoint(final BreinConfig config);

    /**
     * Retrieves the currently set {@code unixTimestamp}. If now should be used as timestamp, the method returns {@code
     * -1L}.
     *
     * @return unix timestamp
     */
    public long getUnixTimestamp() {
        if (unixTimestamp == -1) {
            return -1L;
        } else {
            return unixTimestamp;
        }
    }

    /**
     * Sets the timestamp.
     *
     * @param unixTimestamp unix timestamp to be used
     *
     * @return {@code this}
     */
    public T setUnixTimestamp(final long unixTimestamp) {
        this.unixTimestamp = unixTimestamp;
        return getThis();
    }

    /**
     * sets the ipaddress
     *
     * @param ipAddress contains the ipAddress
     *
     * @return {@code this}
     */
    public T setClientIpAddress(final String ipAddress) {
        this.ipAddress = ipAddress;
        return getThis();
    }

    public String getClientIpAddress(){
        return ipAddress;
    }

    /**
     * Gets the GSON builder instance used to build the requests body
     *
     * @return GSON instance
     */
    public Gson getGson() {
        return GSON;
    }

    /**
     * Sets a base value. The method cannot be used to set the user base-value, instead setUser has to be used.
     * <p>
     * <pre>
     *     {
     *          'key': 'value'
     *     }
     * </pre>
     *
     * @return {@code this}
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
     * This method adds the request specific information to the {@code requestData}. It is called by {@link
     * #prepareRequestData(BreinConfig)} after the request data of the base information is added.
     *
     * @param requestData the request data to be sent to the endpoint
     */
    public abstract void prepareRequestData(final BreinConfig config, final Map<String, Object> requestData);

    /**
     * Method to generate the body part of the request.
     *
     * @param config the configuration used to create the request body
     *
     * @return the created request body (JSON)
     */
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

        requestData.put(IP_ADDRESS, getClientIpAddress());


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

    @Override
    public String toString() {
        final BreinConfig config = new BreinConfig(null);
        return prepareRequestData(config);
    }
}
