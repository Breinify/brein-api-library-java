>
```java--native
final BreinTemporalDataResult result;
result = Breinify.temporalData(37.7609295, -122.4194155,
                               "STATE", "CITY", "NEIGHBORHOOD");
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

<blockquote class="lang-specific java--native">
<p>The returned <code class="prettyprint">GeoJson</code> instances can be read and used via
<code class="prettyprint">result.getLocation().getGeoJson("CITY")</code>. If a shape is not
available, the <code class="prettyprint">getGeoJson(...)</code> method returns 
<code class="prettyprint">null</code>.</p>
</blockquote>