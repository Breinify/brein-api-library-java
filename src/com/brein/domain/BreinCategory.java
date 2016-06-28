package com.brein.domain;

/**
 *  The category of the platform/service/products, i.e., one of apparel,
 *  home, education, family, food, health, job, services, or other.
 */
public class BreinCategory {

    /**
     * for now a simple string
     */
    private String category;

    /**
     * Ctor with value
     * @param category value
     */
    public BreinCategory(final String category) {
        setCategory(category);
    }

    /**
     *
     * @return category
     */
    public String getCategory() {
        return category;
    }

    /**
     * set's category
     * @param category value
     */
    public void setCategory(final String category) {
        this.category = category;
    }


}
