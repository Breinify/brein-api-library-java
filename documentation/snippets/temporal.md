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
> ```
