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
> ```
