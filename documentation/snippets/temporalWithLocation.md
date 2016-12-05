> ```java
> // Example with location latitude / longitude data
> final Map<String, Object> location = new HashMap<>();
>
> location.put("latitude", 32.7157);
> location.put("longitude", -117.1611);
>
> final BreinUser user = new BreinUser()
>       .setAdditional("location", location);
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
