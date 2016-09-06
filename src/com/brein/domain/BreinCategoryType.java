package com.brein.domain;

/**
 * The categoryType of the platform/service/products, i.e., one of apparel,
 * home, education, family, food, health, job, services, or other.
 */
public enum BreinCategoryType {

    /*
     * this pre-defined category types can be used
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

    /**
     * the corresponding name of the enum
     */
    private String name;

    /**
     * Indicates the category of the activity
     * @param name of the type
     */
    BreinCategoryType(final String name) {
        this.name = name;
    }

    /**
     * return the corresponding name
     * @return of the enum
     */
    public String getName() {
        return name;
    }

    /**
     * sets the name to any value for BreinCategoryType.
     *
     * @param name contains the string to set to
     */
    public void setName(final String name) {
        this.name = name;
    }

}