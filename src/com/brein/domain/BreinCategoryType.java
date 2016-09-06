package com.brein.domain;

/**
 * The categoryType of the platform/service/products, i.e., one of apparel,
 * home, education, family, food, health, job, services, or other.
 */
public class BreinCategoryType {

    /*
     * this pre-defined category types can be used
     */
    public static final String APPAREL = "apparel";
    public static final String HOME = "home";
    public static final String EDUCATION = "education";
    public static final String FAMILY = "family";
    public static final String FOOD = "food";
    public static final String HEALTH = "health";
    public static final String JOB = "job";
    public static final String SERVICES = "services";
    public static final String OTHER = "other";

    /**
     * the corresponding name of the enum
     */
    protected String name;

    /**
     * Indicates the category of the activity
     * @param name of the type
     */
    public BreinCategoryType(final String name) {
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