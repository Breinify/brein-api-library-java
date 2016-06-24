package com.brein.domain;

/**
 *  The category of the platform/service/products, i.e., one of apparel,
 *  home, education, family, food, health, job, services, or other.
 */
public class Category {

    /**
     * for now a simple string
     */
    private String category;

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
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Ctor with value
     * @param category
     */
    public Category(final String category) {
        setCategory(category);
    }
}
