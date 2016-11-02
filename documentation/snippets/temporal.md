> ```java
> // Example with just IP address set
> final BreinUser breinUser = new BreinUser()
>          .setIpAddress("143.127.128.10");
> 
> // Invoke the temporal request 
> final BreinResult result = Breinify.temporalData(breinUser, false);
> final Object timeValues = result.get("time");
> final Object weatherValues = result.get("weather");
> final Object locationValues = result.get("location");
> final Object holidayValues = result.get("holiday");

> // Example with IP address, timezone and localdata time
> final BreinUser breinUser = new BreinUser()
>          .setIpAddress("143.127.128.10")
>          .setTimezone("America/Los_Angeles")
>          .setLocalDateTime("Sun 25 Dec 2016 18:15:48 GMT-0800 (PST)");
>
> // Invoke the temporal request
> final BreinResult result = Breinify.temporalData(breinUser, false);
> final Object timeValues = result.get("time");
> final Object weatherValues = result.get("weather");
> final Object locationValues = result.get("location");
> final Object holidayValues = result.get("holiday");
> ```
