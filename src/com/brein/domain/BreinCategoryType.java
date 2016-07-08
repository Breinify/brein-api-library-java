package com.brein.domain;

/**
 * The categoryType of the platform/service/products, i.e., one of apparel,
 * home, education, family, food, health, job, services, or other.
 */
public enum BreinCategoryType {

    /**
     * the only possible category types
     */

    APPAREL("apparel"),
    HOME("home"),
    EDUCATION("education"),
    FAMILY("family"),
    FOOD("food"),
    HEALTH("health"),
    JOB("job"),
    SERVICES("services"),
    OTHER("other");

    private final String name;

    BreinCategoryType(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}