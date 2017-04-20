package com.brein.engine;

import com.brein.domain.BreinResult;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.function.Consumer;

public class UniRestCallback implements Callback<JsonNode> {
    public static final String MSG_REQUEST_HAS_FAILED = "FAILED";
    public static final String MSG_REQUEST_HAS_BEEN_CANCELLED = "CANCELLED";
    public static final String MSG_REQUEST_WAS_SUCCESSFUL = "SUCCESS";

    private static final Logger LOG = LoggerFactory.getLogger(UniRestCallback.class);

    private final UniRestEngine engine;
    private final Consumer<BreinResult> callback;

    public UniRestCallback(final UniRestEngine engine,
                           final Consumer<BreinResult> callback) {
        this.engine = engine;
        this.callback = callback;
    }

    @Override
    public void completed(final HttpResponse<JsonNode> response) {
        final String strResponse = response.getBody().toString();
        final int status = response.getStatus();

        final BreinResult result;
        if (status == 200) {
            final Map<String, Object> mapResponse = this.engine.parseJson(strResponse);
            result = new BreinResult(mapResponse);
        } else {
            result = new BreinResult(strResponse, status);
        }

        if (this.callback != null) {
            this.callback.accept(result);
        }
    }

    @Override
    public void failed(final UnirestException e) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(MSG_REQUEST_HAS_FAILED, e);
        }

        if (this.callback != null) {
            this.callback.accept(new BreinResult(e, 500));
        }
    }

    @Override
    public void cancelled() {
        if (LOG.isDebugEnabled()) {
            LOG.debug(MSG_REQUEST_HAS_BEEN_CANCELLED);
        }

        if (this.callback != null) {
            this.callback.accept(new BreinResult(MSG_REQUEST_HAS_BEEN_CANCELLED, 400));
        }
    }
}