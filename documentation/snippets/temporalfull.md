> ```java
> // Example with full user data 
> final BreinUser breinUser = new BreinUser()
>          .setIpAddress("143.127.128.10")
>          .setTimezone("America/New_York")
>          .setLocalDateTime("Wed Oct 26 2016 13:02:06 GMT-0700 (EDT)");
>
> // Invoke the temporal request
> final BreinResult result = Breinify.temporalData(breinUser, false);
> final Object timeValues = result.get("time");
> final Object weatherValues = result.get("weather");
> final Object locationValues = result.get("location");
> final Object holidayValues = result.get("holiday");
> ```
