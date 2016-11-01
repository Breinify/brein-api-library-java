
```Java
// provide
final BreinUser breinUser = new BreinUser()
         .setTimezone("America/Los_Angeles")
         .setLocalDateTime("Sun 25 Dec 2016 18:15:48 GMT-0800 (PST)");
               
// invoke the temporal request 
final BreinResult result = Breinify.temporalData(breinUser, false);
final Object timeValues = result.get("time");
final Object weatherValues = result.get("weather");
final Object locationValues = result.get("location");
final Object holidayValues = result.get("holiday");

```
