>
```java--native
final BreinTemporalDataResult result;
result = Breinify.temporalData(37.7609295, 
                               -122.4194155,
                               "STATE", "CITY");
```

>
```java--native
final BreinTemporalDataResult result;
result = new BreinTemporalData()
   .setLatitude(37.7609295)
   .setLongitude(-122.4194155)
   .addShapeTypes("CITY", "NEIGHBORHOOD")
   .execute();
```