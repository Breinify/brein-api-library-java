package com.brein.domain;

import com.brein.api.BreinBase;
import com.brein.util.BreinMapUtil;
import com.brein.util.BreinUtil;
import com.google.gson.JsonObject;

import java.util.Map;

public class BreinBaseRequest {

    /**
     * contains an extra map for the base section
     */
    private Map<String, Object> extraBaseMap;

    /**
     * returns the extra map for the base section
     *
     * @return map <String, Object>
     */
    public Map<String, Object> getExtraBaseMap() {
        return extraBaseMap;
    }

    /**
     * sets an extra map for the base section
     *
     * @param extraBaseMap map of <String, Object></String,>
     */
    public void setExtraBaseMap(final Map<String, Object> extraBaseMap) {
        this.extraBaseMap = extraBaseMap;
    }

    /**
     * prepares the request for the base section with standard fields
     * plus possible extra fields if configured
     *
     * @param breinBase   contains the appropriate request object
     * @param requestData contains the created json structure
     * @param isSign      indicates if the request must be signed (secret option)
     */
    public void prepareBaseRequestData(final BreinBase breinBase,
                                       final JsonObject requestData,
                                       final boolean isSign) {

        if (BreinUtil.containsValue(breinBase.getConfig())) {
            if (BreinUtil.containsValue(breinBase.getConfig().getApiKey())) {
                requestData.addProperty("apiKey", breinBase.getConfig().getApiKey());
            }
        }

        final BreinUser breinUser = breinBase.getBreinUser();

        /*
        if (BreinUtil.containsValue(breinBase.getIpAddress())) {
            requestData.addProperty("ipAddress", breinBase.getIpAddress());
        } */

        if (BreinUtil.containsValue(breinUser.getIpAddress())) {
            requestData.addProperty("ipAddress", breinUser.getIpAddress());
        }

        requestData.addProperty("unixTimestamp", breinBase.getUnixTimestamp());

        // if sign is active
        if (isSign) {
            requestData.addProperty("signature", breinBase.createSignature());
            requestData.addProperty("signatureType", "HmacSHA256");
        }

        // check if there are further extra maps to add on base level
        if (extraBaseMap != null && extraBaseMap.size() > 0) {
            BreinMapUtil.fillMap(extraBaseMap, requestData);
        }
    }
}
