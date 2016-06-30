package com.brein.domain;

/**
 * Contains the Dimension to ask for
 *
 */
public class BreinDimension {

    /**
     * contains the array of dimensions
     */
    private String[] dimensionFields;

    /**
     * Ctor with array of requested dimensions
     *
     * @param dimensionFields array of dimensions
     */
    public BreinDimension(final String[] dimensionFields) {
        this.dimensionFields = dimensionFields;
    }

    /**
     * retrieve dimesion array
     * @return dimension array
     */
    public String[] getDimensionFields() {
        return dimensionFields;
    }

    /**
     * sets dimension array
     * @param dimensionFields data of dimensions
     */
    public void setDimensionFields(final String[] dimensionFields) {
        this.dimensionFields = dimensionFields;
    }

}
