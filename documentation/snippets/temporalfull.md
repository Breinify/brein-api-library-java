> ```java
> // Example with full user data 
> final BreinUser breinUser = new BreinUser()
>          .setIpAddress("143.127.128.10")
>          .setTimezone("America/New_York")
>          .setLocalDateTime("Wed Oct 26 2016 13:02:06 GMT-0700 (EDT)");
>
> // Invoke the temporal request
> final BreinResult result = Breinify.temporalData(breinUser, false);
>
> // Retrieve the results
> final Map<String, Object> timeValues = (Map<String, Object>) result.get("time");
> final Map<String, Object> weatherValues = (Map<String, Object>) result.get("weather");
> final Map<String, Object> locationValues = (Map<String, Object>) result.get("location");
> final Map<String, Object> holidayValues = (Map<String, Object>) result.get("holiday");
> ```
