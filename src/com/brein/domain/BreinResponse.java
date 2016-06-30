package com.brein.domain;

/**
 * Contains the result of an Brein Request when invoking a
 * lookUp
 *
 */
public class BreinResponse {

    /**
     * contains the response
     */
    private String response;

    /**
     * creates a brein response object
     * @param response as json string
     */
    public BreinResponse(final String response) {
        setResponse(response);
    }

    /**
     * retrieves the response
     * @return as json string
     */
    public String getResponse() {
        return response;
    }

    /**
     * sets the response
     * @param response as json string
     */
    public void setResponse(final String response) {
        this.response = response;
    }
}
