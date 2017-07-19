<p align="center">
  <img src="https://www.breinify.com/img/Breinify_logo.png" alt="Breinify: Leading Temporal AI Engine" width="250">
</p>

# Sample Application

The library contains an executable sample application [Sample.java](../src/com/brein/sample/Sample.java). This sample
application provides some examples on how to request data from the API through the library.

<p align="center">
  <img src="https://raw.githubusercontent.com/Breinify/brein-api-library-java/master/documentation/img/sample-response-output.png" alt="Output of the Sample Application" width="500"><br/>
  <sup>Output of the Sample Application utilizing some commonly used features.</sup>
</p>

As illustrated, the sample application resolves the IP address `204.28.127.66` and looks-up some sample geo-coordinates, i.e., 
`(40.7608, -111.8910)`, `(39.0256, -77.0299)`, and `(43.0025, -87.9192)`. The sample also shows how to resolve text to a location, 
as examples `Las Vegas`, `Grand Canyon`, and `The Big Apple`. At last, the example shows a text based on the response of the
`/temporaldata` API using some time, location, event, and weather information. The sample application does not
 illustrate the retrieval of GeoJson instances. GeoJson instances are mainly used for visualizations, which are out
 of the scope of this simple example (not because of the API or the library, mostly because of the complexity within Java). 
 Nevertheless, to retrieve GeoJsons is as simple as it is for other libraries (see their examples for a visualization, e.g.,
 [JavaScript](https://github.com/Breinify/brein-api-library-javascript-browser#reverse-geocoding-retrieve-geojsons-for-eg-cities-neighborhoods-or-zip-codes) or 
 [Node.js](https://github.com/Breinify/brein-api-library-node#reverse-geocoding-retrieve-information-from-coordinates)):
 
 ```java
final BreinTemporalDataResult result = new BreinTemporalData()
    .setLatitude(37.7609295)
    .setLongitude(-122.4194155)
    .addShapeTypes("CITY", "NEIGHBORHOOD")
    .execute();

// access the geoJson instances for the CITY and the NEIGHBORHOOD
System.out.println(result.getLocation().getGeoJson("CITY"));
System.out.println(result.getLocation().getGeoJson("NEIGHBORHOOD"));
 ```