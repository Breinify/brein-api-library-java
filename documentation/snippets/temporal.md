> ```java
> // Example with just IP address set
> final BreinUser breinUser = new BreinUser()
>          .setIpAddress("143.127.128.10");
> 
> // Invoke the temporal request 
> final BreinResult ipOnlyResult = Breinify.temporalData(breinUser, false);
> final Object timeValues = ipOnlyResult.get("time");
> final Object weatherValues = ipOnlyResult.get("weather");
> final Object locationValues = ipOnlyResult.get("location");
> final Object holidayValues = ipOnlyResult.get("holiday");
>
> // Example with IP address, timezone and localdata time
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
