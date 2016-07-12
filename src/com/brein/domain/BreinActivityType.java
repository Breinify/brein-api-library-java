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
    OTHER("other");

    private final String name;

    BreinActivityType(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

