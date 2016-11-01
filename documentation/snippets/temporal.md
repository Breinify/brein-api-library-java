
```Java
// provide
final BreinUser breinUser = new BreinUser()
         .setTimezone("America/Los_Angeles")
         .setLocalDateTime("America/Los_Angeles");
               
// invoke the temporal request 
final BreinResult result = Breinify.temporal(breinUser, false);
final Object timeValues = result.get("time");
final Object weatherValues = result.get("weather");
final Object locationValues = result.get("location");
final Object holidayValues = result.get("holiday");

```
