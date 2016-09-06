package com.brein.domain;

/**
 * The type of the activity collected, i.e., one of search, login, logout, addToCart, removeFromCart, checkOut,
 * selectProduct, or other.
 */
public class BreinActivityType {

    // this pre-defined activity types can be used
    public static final String SEARCH = "search";
    public static final String LOGIN = "login";
    public static final String LOGOUT = "logout";
    public static final String ADD_TO_CART = "addToCart";
    public static final String REMOVE_FROM_CART = "removeFromCart";
    public static final String SELECT_PRODUCT = "selectProduct";
    public static final String CHECKOUT = "checkOut";
    public static final String PAGEVISIT = "pageVisit";
    public static final String OTHER = "other";

    /**
     * the corresponding name of the enum
     */
    private String name;

    /**
     * Indicates the kind of the activity
     * @param name of the type
     */
    public BreinActivityType(final String name) {
        this.name = name;
    }

    /**
     * sets the name to any value for BreinActivityType.
     *
     * @param name contains the string to set to
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * return the corresponding name
     * @return of the enum
     */
    public String getName() {
        return name;
    }
}

