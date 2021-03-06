package com.brein.domain;

import com.brein.api.BreinInvalidConfigurationException;
import com.brein.engine.BreinEngineType;
import com.brein.util.BreinUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Provides the configuration of the library for the properties supplied.
 */
public class BreinConfig {

    /**
     * default endpoint for activity
     */
    public static final String DEFAULT_ACTIVITY_ENDPOINT = "/activity";

    /**
     * default endpoint for lookup
     */
    public static final String DEFAULT_LOOKUP_ENDPOINT = "/lookup";

    /**
     * default endpoint for temporalData
     */
    public static final String DEFAULT_TEMPORALDATA_ENDPOINT = "/temporaldata";

    /**
     * default endpoint for recommendation
     */
    public static final String DEFAULT_RECOMMENDATION_ENDPOINT = "/recommendation";

    /**
     * default connection timeout
     */
    public static final long DEFAULT_CONNECTION_TIMEOUT = 1000;

    /**
     * default socket timeout
     */
    public static final long DEFAULT_SOCKET_TIMEOUT = 6000;

    /**
     * default Breinify base url
     */
    public static final String DEFAULT_BASE_URL = "https://api.breinify.com";

    /**
     * default {@code BreinEngineType}
     */
    public static final BreinEngineType DEFAULT_ENGINE_TYPE = BreinEngineType.AUTO_DETECT;

    /**
     * default rest call threads
     */
    public static final int DEFAULT_REST_CALL_THREADS = 5;

    /**
     * Logger instance
     */
    private static final Logger LOG = LoggerFactory.getLogger(BreinConfig.class);

    /**
     * BASE URL
     */
    private String baseUrl = DEFAULT_BASE_URL;

    /**
     * contains the api key
     */
    private String apiKey;

    /**
     * Default REST client configuration
     */
    private BreinEngineType restEngineType = DEFAULT_ENGINE_TYPE;

    /**
     * contains the activity endpoint (default = ACTIVITY_ENDPOINT)
     */
    private String activityEndpoint = DEFAULT_ACTIVITY_ENDPOINT;

    /**
     * contains the lookup endpoint (default = LOOKUP_ENDPOINT)
     */
    private String lookupEndpoint = DEFAULT_LOOKUP_ENDPOINT;

    /**
     * contains the temporalData endpoint (default = DEFAULT_TEMPORALDATA_ENDPOINT)
     */
    private String temporalDataEndpoint = DEFAULT_TEMPORALDATA_ENDPOINT;

    /**
     * contains the recommendation endpoint (default = DEFAULT_RECOMMENDATION_ENDPOINT)
     */
    private String recommendationEndpoint = DEFAULT_RECOMMENDATION_ENDPOINT;

    /**
     * connection timeout
     */
    private long connectionTimeout = DEFAULT_CONNECTION_TIMEOUT;

    /**
     * socket timeout
     */
    private long socketTimeout = DEFAULT_SOCKET_TIMEOUT;

    /**
     * default category
     */
    private String defaultCategory = "";

    /**
     * contains the secret that will be used for the signature
     */
    private String secret;

    /**
     * contains the number of rest call threads
     */
    private int restCallThreads = DEFAULT_REST_CALL_THREADS;

    /**
     * The additional headers to be sent with the request
     */
    private final AtomicReference<Map<String, String>> headers = new AtomicReference<>();

    /**
     * @param apiKey contains the Breinify api key
     */
    public BreinConfig(final String apiKey) {
        setApiKey(apiKey);
    }

    /**
     * @param apiKey contains the Breinify api key
     * @param secret contains the secret
     */
    public BreinConfig(final String apiKey,
                       final String secret) {
        this(apiKey);
        setSecret(secret);
    }

    /**
     * retrieves the base url
     *
     * @return base url
     */
    public String getBaseUrl() {
        return this.baseUrl;
    }

    /**
     * set the base url of the breinify backend and will check
     * if the URL is valid.
     *
     * @param baseUrl contains the url
     *
     * @return the config object itself
     */
    public BreinConfig setBaseUrl(final String baseUrl) {
        this.baseUrl = baseUrl;
        checkBaseUrl(baseUrl);
        return this;
    }

    /**
     * checks if the url is valid. If not a BreinInvalidConfigurationException will
     * be thrown.
     *
     * @param baseUrl url to check
     */
    public void checkBaseUrl(final String baseUrl) throws BreinInvalidConfigurationException {

        if (!BreinUtil.isValidUrl(baseUrl)) {
            final String msg = "BreinConfig issue. Value for BaseUrl is not valid. Value is: "
                    + baseUrl;
            if (LOG.isDebugEnabled()) {
                LOG.debug(msg);
            }
            throw new BreinInvalidConfigurationException(msg);
        }
    }

    public BreinEngineType getRestEngineType() {
        return this.restEngineType;
    }

    public BreinConfig setRestEngineType(final BreinEngineType restEngineType) {
        this.restEngineType = restEngineType;
        return this;
    }

    public String getApiKey() {
        return this.apiKey;
    }

    public BreinConfig setApiKey(final String apiKey) {

        if (BreinUtil.containsValue(apiKey)) {
            this.apiKey = apiKey;
        }
        return this;
    }

    public String getUrl() {
        return this.baseUrl;
    }

    public long getConnectionTimeout() {
        return this.connectionTimeout;
    }

    public BreinConfig setConnectionTimeout(final long connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
        return this;
    }

    public long getSocketTimeout() {
        return this.socketTimeout;
    }

    public BreinConfig setSocketTimeout(final long socketTimeout) {
        this.socketTimeout = socketTimeout;
        return this;
    }

    public int getRestCallThreads() {
        return this.restCallThreads;
    }

    public void setRestCallThreads(final int restCallThreads) {
        this.restCallThreads = restCallThreads;
    }

    public String getActivityEndpoint() {
        return this.activityEndpoint;
    }

    public BreinConfig setActivityEndpoint(final String activityEndpoint) {
        this.activityEndpoint = activityEndpoint;
        return this;
    }

    public String getLookupEndpoint() {
        return this.lookupEndpoint;
    }

    public BreinConfig setLookupEndpoint(final String lookupEndpoint) {
        this.lookupEndpoint = lookupEndpoint;
        return this;
    }

    public String getTemporalDataEndpoint() {
        return this.temporalDataEndpoint;
    }

    public void setTemporalDataEndpoint(final String temporalDataEndpoint) {
        this.temporalDataEndpoint = temporalDataEndpoint;
    }

    public String getRecommendationEndpoint() {
        return this.recommendationEndpoint;
    }

    public BreinConfig setRecommendationEndpoint(final String recommendationEndpoint) {
        this.recommendationEndpoint = recommendationEndpoint;
        return this;
    }

    public String getSecret() {
        return this.secret;
    }

    public BreinConfig setSecret(final String secret) {
        this.secret = secret;
        return this;
    }

    public String getDefaultCategory() {
        return this.defaultCategory;
    }

    public BreinConfig setDefaultCategory(final String defaultCategory) {
        this.defaultCategory = defaultCategory;
        return this;
    }

    public boolean isSign() {
        return getSecret() != null && !getSecret().isEmpty();
    }

    public BreinConfig setHeader(final String key, final String value) {
        return setHeaders(Collections.singletonMap(key, value));
    }

    public BreinConfig setHeaders(final Map<String, String> headers) {
        if (this.headers.get() == null) {
            this.headers.compareAndSet(null, new HashMap<>());
        }

        this.headers.get().putAll(headers);
        return this;
    }

    public Map<String, String> getHeaders() {
        if (this.headers.get() == null) {
            return Collections.emptyMap();
        } else {
            return this.headers.get();
        }
    }
}
