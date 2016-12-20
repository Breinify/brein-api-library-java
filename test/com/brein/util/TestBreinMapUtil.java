package com.brein.util;

import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TestBreinMapUtil {


    @Test
    public void testTagWithFillMap() {

        final Map<String, Object> tags = new HashMap<>();

        Object oo = new String[]{"test4"};
        System.out.println(oo.getClass());

        tags.put("TAG_PRODUCTS_HANDLED", Collections.singletonList("productBought"));
        tags.put("Test", Collections.singletonList("hi"));
        tags.put("Test 2", "There");

        tags.put("TAG_TOTAL_PURCHASE_PRICE", 5 * 5 * 10.5);
        tags.put("TAG_PRODUCTS_HANDLED_ITEM_SKU_ARRAY",
                Collections.singletonList(String.valueOf(1000 % 5)));
        tags.put("TAG_PRODUCTS_HANDLED_UNIT_PRICE_ARRAY",
                new String[]{String.valueOf(45000 % 5 * 10.5)});
        tags.put("test 3", "test3");
        tags.put("test 4", new String[]{"test4"});
        tags.put("test 5", Collections.singletonList("test5"));
        tags.put("test 6", Collections.singleton("test6"));

        final Map<String, Object> json = new HashMap<>();


    }


}