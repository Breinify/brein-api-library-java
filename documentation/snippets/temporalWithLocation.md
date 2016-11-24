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
>       .setAdditionalMap(locationAdditionalMap);
>
>        try {
>            // set configuration
>            Breinify.setConfig(breinConfig);
>
>            // invoke temporaldata
>            final BreinResult response = Breinify.temporalData(user);
>
>        } catch (final Exception e) {
>            LOG("REST exception is: " + e);
>        }
> ```
