package com.brein.domain;

import com.brein.api.BreinBase;
import com.brein.util.BreinMapUtil;
import com.brein.util.BreinUtil;
import com.google.gson.JsonObject;

import java.util.Map;

public class BreinBaseRequest {

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
}
