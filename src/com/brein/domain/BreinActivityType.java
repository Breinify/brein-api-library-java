package com.brein.domain;

/**
 * The type of the activity collected, i.e., one of search, login, logout, addToCart, removeFromCart, checkOut,
 * selectProduct, or other.
 */
public enum BreinActivityType {
    SEARCH("search"),
    LOGIN("login"),
    LOGOUT("logout"),
    ADD_TO_CART("addToCart"),
    REMOVE_FROM_CART("removeFromCart"),
    SELECT_PRODUCT("selectProduct"),
    CHECKOUT("checkOut"),
    PAGEVISIT("pageVisit"),
    OTHER("other");

    /**
     * the corresponding name of the enum
     */
    private final String name;

    /**
     * Indicates the kind of the activity
     * @param name of the type
     */
    BreinActivityType(final String name) {
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

