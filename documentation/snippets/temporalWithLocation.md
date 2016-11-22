> ```java
> // Example with location latitude / longitude data
> final Map<String, Object> locationAdditionalMap = new HashMap<>();
> final Map<String, Object> locationValueMap = new HashMap<>();
>
> locationValueMap.put("latitude", 32.7157);
> locationValueMap.put("longitude", -117.1611);
> locationAdditionalMap.put("location", locationValueMap);
>
> final BreinUser user = new BreinUser()
>       .setExtraAdditionalMap(locationAdditionalMap);
>
>        try {
>            // set configuration
>            Breinify.setConfig(breinConfig);
>
>            // invoke temporaldata
>            final BreinResult response = Breinify.temporalData(user, false);
>
>        } catch (final Exception e) {
>            fail("REST exception is: " + e);
>        }
> ```
