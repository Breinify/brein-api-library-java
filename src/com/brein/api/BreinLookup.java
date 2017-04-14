package com.brein.api;

import com.brein.domain.BreinConfig;
import com.brein.domain.BreinDimension;
import com.brein.domain.BreinResult;
import com.brein.util.BreinMapUtil;
import com.brein.util.BreinUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Provides the lookup functionality
 */
public class BreinLookup extends BreinBase<BreinLookup> implements IExecutable<BreinResult> {

    /**
     * used for lookup request
     */
    private BreinDimension breinDimension;

    /**
     * retrieves the Brein dimension object
     *
     * @return object
     */
    public BreinDimension getBreinDimension() {
        return breinDimension;
    }

    /**
     * sets the breindimension object - will be used for lookup
     *
     * @param breinDimension object to set
     *
     * @return self
     */
    public BreinLookup setBreinDimension(final BreinDimension breinDimension) {
        this.breinDimension = breinDimension;
        return this;
    }

    @Override
    public String getEndPoint(final BreinConfig config) {
        return config.getLookupEndpoint();
    }

    @Override
    public void prepareRequestData(final BreinConfig config, final Map<String, Object> requestData) {

        // this is the section that is only available within the lookup request
        if (BreinUtil.containsValue(getBreinDimension())) {

            final Map<String, Object> lookupData = new HashMap<>();
            if (getBreinDimension().getDimensionFields().length > 0) {
                final List<String> dimensions = new ArrayList<>();
                Collections.addAll(dimensions, getBreinDimension().getDimensionFields());

                lookupData.put("dimensions", dimensions);
                requestData.put("lookup", lookupData);
            }
        }
    }

    /**
     * Creates the signature for lookup
     *
     * @return signature
     */
    @Override
    public String createSignature(final BreinConfig config, final Map<String, Object> requestData) {

        final List<String> dimensions = BreinMapUtil.getNestedValue(requestData, "lookup", "dimensions");
        final String paraDimensions = dimensions == null ? "0" : dimensions.get(0);
        final int paraDimensionsLength = dimensions == null ? 0 : dimensions.size();

        final long unixTimestamp = BreinMapUtil.getNestedValue(requestData, UNIX_TIMESTAMP_FIELD);

        final String message = String.format("%s%d%d", paraDimensions, unixTimestamp, paraDimensionsLength);
        return BreinUtil.generateSignature(message, config.getSecret());
    }

    @Override
    public BreinResult execute() {
        return Breinify.lookUp(this);
    }
}
