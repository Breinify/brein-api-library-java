> ```java
> // Example with full user data 
> final BreinUser breinUser = new BreinUser()
>          .setIpAddress("143.127.128.10")
>          .setTimezone("America/New_York")
>          .setLocalDateTime("Wed Oct 26 2016 13:02:06 GMT-0700 (EDT)");
>
>
> // Invoke the temporal request
> final BreinResult result = Breinify.temporalData(breinUser);
>
> // Retrieve the results
> final Map<String, Object> timeValues = result.get("time");
> final Map<String, Object> weatherValues = result.get("weather");
> final Map<String, Object> locationValues = result.get("location");
> final Map<String, Object> holidayValues = result.get("holiday");
> ```
